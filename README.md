#  Cyber Braille (Haptic Braille)

> **Transforming flat glass screens into tactile, multi-sensory experiences for visually impaired learners.**

Cyber Braille is an **edge-AI powered Android accessibility application** that translates visual digital interfaces—such as STEM diagrams, unoptimized web portals, and text—into real-time **haptic (vibration) feedback** and spatial audio. 

Currently, visually impaired users rely entirely on 1-dimensional audio (screen readers). Cyber Braille introduces a 2-dimensional tactile layer, allowing users to physically "feel" shapes, graphs, and Braille dots using the smartphone they already own—eliminating the need for expensive ($1,000+) physical Braille displays.

---

##  Key Features

- **Haptic Braille Emulator:** Translates digital text into standardized braille layouts on-screen. Users receive distinct, sharp haptic bursts when touching virtual braille dots.
- **Dynamic Visual-to-Haptic Mapping:** Converts visual boundaries (lines, UI boxes, graphs) into variable vibration patterns, allowing users to "trace" shapes with their fingers.
- **Multi-Sensory Sync:** Synchronizes spatial audio cues with device vibrations to build a faster, more intuitive mental map of the screen.
- **Zero-Latency Edge AI:** Uses local computer vision to detect UI elements and structural boundaries without requiring cloud processing.
- **100% Offline & Private:** Because it runs on-device, personal data and screen captures never leave the phone.

---

##  Technology Stack

**Frontend & Application Layer**
- Android SDK (Java/Kotlin)
- Android Accessibility Services API (For universal screen overlay)

**Hardware Integration & Output**
- `VibrationEffect` API (Dynamic amplitude/frequency modulation)
- Mobile LRA (Linear Resonant Actuators)
- `SoundPool` API (Low-latency spatial audio)

**Edge AI & Machine Learning (Conceptual/Integration)**
- ONNX Runtime / TensorFlow Lite
- Lightweight YOLO/CNNs (Object & UI Detection)
- Optimized for **AMD Ryzen™ AI (NPUs)** and edge compute for sub-1W power inference.

---

##  How It Works (Process Flow)

1. **Input Capture:** The Android Accessibility Service constantly monitors the active screen layout and tracks the exact `(X, Y)` coordinates of the user's finger.
2. **Edge Processing:** A local lightweight ML model detects the spatial bounding boxes of critical elements (text, buttons, graph lines).
3. **Intersection Logic:** The app calculates in real-time if the user's finger intersects with an identified UI element or virtual braille dot.
4. **Hardware Trigger:** The app sends a command to the device's native vibration motor (LRA), triggering a specific haptic waveform (e.g., a sharp 10ms pulse for a dot, a continuous low hum for a line).

---

##  Quick Start (Local Setup)

### Prerequisites
- Android Studio (Latest Version)
- An Android Physical Device running Android 8.0 (API Level 26) or higher.
  > *Note: Emulators do not support physical vibration. A real device is required to test haptic feedback.*

### Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com/agbuddy7/haptic_braille.git
