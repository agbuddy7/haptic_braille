package com.example.haptic_braille;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class BrailleCellView extends View {

    private Paint dotPaint;
    private PointF[] dotPositions = new PointF[6];
    private float dotRadius = 40f; // The size of the dot
    private boolean[] activeDots = {true, true, true, true, true, true}; // Which dots are "raised"
    private Vibrator vibrator;
    private int lastVibratedDot = -1; // To prevent constant re-vibration on the same dot

    private SoundPool soundPool;
    private int popSoundId;
    private boolean soundLoaded = false;

    public BrailleCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setBraillePattern(boolean[] pattern) {
        if (pattern != null && pattern.length == 6) {
            this.activeDots = pattern;
            invalidate(); // This forces the view to redraw itself
        }
    }

    private void init() {
        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(Color.DKGRAY);
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if (status == 0) {
                soundLoaded = true;
            }
        });
        // NOTE: You need to add a sound file (e.g., "pop.mp3") to your res/raw folder.
        popSoundId = soundPool.load(getContext(), R.raw.pop, 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float cellWidth = w;
        float cellHeight = h;

        float dotSpacingX = cellWidth / 2;
        float dotSpacingY = cellHeight / 3;

        dotPositions[0] = new PointF(dotSpacingX / 2, dotSpacingY / 2);
        dotPositions[1] = new PointF(dotSpacingX / 2, dotSpacingY / 2 + dotSpacingY);
        dotPositions[2] = new PointF(dotSpacingX / 2, dotSpacingY / 2 + (2 * dotSpacingY));
        dotPositions[3] = new PointF(dotSpacingX / 2 + dotSpacingX, dotSpacingY / 2);
        dotPositions[4] = new PointF(dotSpacingX / 2 + dotSpacingX, dotSpacingY / 2 + dotSpacingY);
        dotPositions[5] = new PointF(dotSpacingX / 2 + dotSpacingX, dotSpacingY / 2 + (2 * dotSpacingY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < dotPositions.length; i++) {
            if (activeDots[i] && dotPositions[i] != null) {
                canvas.drawCircle(dotPositions[i].x, dotPositions[i].y, dotRadius, dotPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int currentDot = -1;

        for (int i = 0; i < dotPositions.length; i++) {
            if (activeDots[i] && dotPositions[i] != null) {
                float dx = touchX - dotPositions[i].x;
                float dy = touchY - dotPositions[i].y;
                if (dx * dx + dy * dy < dotRadius * dotRadius) {
                    currentDot = i;
                    break;
                }
            }
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (currentDot != -1 && currentDot != lastVibratedDot) {
                    triggerFeedback();
                    lastVibratedDot = currentDot;
                } else if (currentDot == -1) {
                    lastVibratedDot = -1; // Reset when in empty space
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastVibratedDot = -1;
                break;
        }
        return true;
    }

    private void triggerFeedback() {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // The amplitude can be set to a value between 1 and 255.
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(100);
            }
        }
        if (soundLoaded) {
            soundPool.play(popSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}