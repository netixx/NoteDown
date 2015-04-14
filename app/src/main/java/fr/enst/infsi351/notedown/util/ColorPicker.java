package fr.enst.infsi351.notedown.util;

import android.graphics.Color;

public class ColorPicker {
    private static final int increment = 10;

    private static int current = 0;



    public static int nextColor() {
        float[] hsv = new float[3];
        current += increment;
        hsv[0] = current % 360;
        hsv[1] = 1;
        hsv[2] =1;

        return Color.HSVToColor(hsv);
    }
}
