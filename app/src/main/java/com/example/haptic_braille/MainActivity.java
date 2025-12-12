package com.example.haptic_braille;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText textInput;
    private LinearLayout brailleOutputLayout;
    private Map<Character, boolean[]> brailleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBrailleMap();

        textInput = findViewById(R.id.text_input);
        brailleOutputLayout = findViewById(R.id.braille_output_layout);

        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateBrailleOutput(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    private void updateBrailleOutput(String text) {
        brailleOutputLayout.removeAllViews();
        String lowerCaseText = text.toLowerCase();

        for (char c : lowerCaseText.toCharArray()) {
            boolean[] pattern = brailleMap.get(c);
            if (pattern != null) {
                BrailleCellView brailleCell = new BrailleCellView(this, null);
                brailleCell.setBraillePattern(pattern);
                // Set layout params to control the size of the dynamically created views
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 300);
                brailleCell.setLayoutParams(params);
                brailleOutputLayout.addView(brailleCell);
            }
        }
    }

    private void initializeBrailleMap() {
        brailleMap = new HashMap<>();
        // Alphabet
        brailleMap.put('a', new boolean[]{true, false, false, false, false, false});
        brailleMap.put('b', new boolean[]{true, true, false, false, false, false});
        brailleMap.put('c', new boolean[]{true, false, false, true, false, false});
        brailleMap.put('d', new boolean[]{true, false, false, true, true, false});
        brailleMap.put('e', new boolean[]{true, false, false, false, true, false});
        brailleMap.put('f', new boolean[]{true, true, false, true, false, false});
        brailleMap.put('g', new boolean[]{true, true, false, true, true, false});
        brailleMap.put('h', new boolean[]{true, true, false, false, true, false});
        brailleMap.put('i', new boolean[]{false, true, false, true, false, false});
        brailleMap.put('j', new boolean[]{false, true, false, true, true, false});
        brailleMap.put('k', new boolean[]{true, false, true, false, false, false});
        brailleMap.put('l', new boolean[]{true, true, true, false, false, false});
        brailleMap.put('m', new boolean[]{true, false, true, true, false, false});
        brailleMap.put('n', new boolean[]{true, false, true, true, true, false});
        brailleMap.put('o', new boolean[]{true, false, true, false, true, false});
        brailleMap.put('p', new boolean[]{true, true, true, true, false, false});
        brailleMap.put('q', new boolean[]{true, true, true, true, true, false});
        brailleMap.put('r', new boolean[]{true, true, true, false, true, false});
        brailleMap.put('s', new boolean[]{false, true, true, true, false, false});
        brailleMap.put('t', new boolean[]{false, true, true, true, true, false});
        brailleMap.put('u', new boolean[]{true, false, true, false, false, true});
        brailleMap.put('v', new boolean[]{true, true, true, false, false, true});
        brailleMap.put('w', new boolean[]{false, true, false, true, true, true});
        brailleMap.put('x', new boolean[]{true, false, true, true, false, true});
        brailleMap.put('y', new boolean[]{true, false, true, true, true, true});
        brailleMap.put('z', new boolean[]{true, false, true, false, true, true});

        // Numbers (preceded by a number sign) - for simplicity, I'll map them directly for now
        brailleMap.put('1', new boolean[]{true, false, false, false, false, false}); // Same as 'a'
        brailleMap.put('2', new boolean[]{true, true, false, false, false, false}); // Same as 'b'
        brailleMap.put('3', new boolean[]{true, false, false, true, false, false}); // Same as 'c'
        brailleMap.put('4', new boolean[]{true, false, false, true, true, false}); // Same as 'd'
        brailleMap.put('5', new boolean[]{true, false, false, false, true, false}); // Same as 'e'
        brailleMap.put('6', new boolean[]{true, true, false, true, false, false}); // Same as 'f'
        brailleMap.put('7', new boolean[]{true, true, false, true, true, false}); // Same as 'g'
        brailleMap.put('8', new boolean[]{true, true, false, false, true, false}); // Same as 'h'
        brailleMap.put('9', new boolean[]{false, true, false, true, false, false}); // Same as 'i'
        brailleMap.put('0', new boolean[]{false, true, false, true, true, false}); // Same as 'j'

        // Space
        brailleMap.put(' ', new boolean[]{false, false, false, false, false, false});
    }
}
