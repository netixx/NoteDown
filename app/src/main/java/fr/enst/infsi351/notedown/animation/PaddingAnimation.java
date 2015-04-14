package fr.enst.infsi351.notedown.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by francois on 14/04/15.
 */
public class PaddingAnimation extends Animation {

    private final int[] paddings;
    private int[] startPaddings;
    private View mView;

    public PaddingAnimation(View view, int[] paddings)
    {
        //left, top, right, bottom
        mView = view;
        this.paddings = paddings;
        startPaddings = new int[] {view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom()};
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        int[] newPaddings = new int[4];
        for (int i = 0; i<newPaddings.length; i++) {
            newPaddings[i] = startPaddings[i] + (int) ((paddings[i] - startPaddings[i])*interpolatedTime);
        }
        mView.setPadding(newPaddings[0], newPaddings[1], newPaddings[2], newPaddings[3]);
        mView.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight)
    {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds()
    {
        return true;
    }
}
