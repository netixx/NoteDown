package fr.enst.infsi351.notedown;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by francois on 14/04/15.
 */
public class PdfMarker extends FrameLayout {


    private final View innerMarker;
    public PdfMarker(Context context, int x, int y) {
        super(context);
        innerMarker = new FrameLayout(context);
        int width = getResources().getDimensionPixelSize(R.dimen.pdf_marker_width);
        int height = getResources().getDimensionPixelSize(R.dimen.pdf_marker_height);
        LayoutParams params = new FrameLayout.LayoutParams(width, height);
        int padding = getResources().getDimensionPixelSize(R.dimen.pdf_marker_padding);
        this.setPadding(padding, padding, padding, padding);
        params.setMargins(x - padding/2 - width/2, y - padding/2 - width/2, 0, 0);
        this.setLayoutParams(params);
        this.addView(innerMarker);
    }

    public void setColor(int color) {
        innerMarker.setBackgroundColor(color);
    }

}
