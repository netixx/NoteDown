package fr.enst.infsi351.notedown.util;

import android.graphics.Point;
import android.view.View;
import android.view.View.DragShadowBuilder;

import fr.enst.infsi351.notedown.view.NoteView;

/**
 * Created by francois on 14/04/15.
 */
public class NoteShadowBuilder extends DragShadowBuilder {
    private final View v;
    private final Point touchPoint;

    public NoteShadowBuilder(NoteView v, Point touchPoint) {
        super(v);
        this.v = v;
        this.touchPoint = touchPoint;
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
        shadowSize.set(v.getWidth(), v.getHeight());
        shadowTouchPoint.set(touchPoint.x, touchPoint.y);
    }
}
