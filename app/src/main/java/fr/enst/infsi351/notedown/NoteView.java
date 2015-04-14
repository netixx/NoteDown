package fr.enst.infsi351.notedown;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

import fr.enst.infsi351.notedown.animation.PaddingAnimation;
import fr.enst.infsi351.notedown.fragment.NotesAreaFragment;
import fr.enst.infsi351.notedown.util.NoteShadowBuilder;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link NoteFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link NoteFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class NoteView extends FrameLayout implements OnTouchListener, View.OnClickListener, OnFocusChangeListener, OnLongClickListener {

    public static final int CONTROLS_ANIMATE_DURATION_MILLIS = 100;
    public int minWidth;
    public int minHeight;

    private NotesAreaFragment parent;

    private View controls;
    private View text;
    public NoteView(Context context, NotesAreaFragment parent) {
        super(context);
        init(parent);
    }

    private void init(NotesAreaFragment parent) {
        minWidth = (int) getResources().getDimension(R.dimen.note_min_width);
        minHeight = (int) getResources().getDimension(R.dimen.note_min_height);
        this.parent = parent;
        inflate(getContext(), R.layout.view_note, this);
        final View title = findViewById(R.id.title);
        final View content = findViewById(R.id.content);
        controls = findViewById(R.id.note_controls);
        text = findViewById(R.id.note_textedit);
//        this.setNextFocusDownId(R.id.title);

        title.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_TAB)) {
                    // Perform action on Enter or TAB key press
                    title.clearFocus();
                    content.requestFocus();
                    return true;
                }
                return false;
            }
        });

//        this.setOnLongClickListener(this);
//        title.setOnLongClickListener(this);
//        content.setOnLongClickListener(this);

        this.setOnTouchListener(this);
        title.setOnTouchListener(this);
        content.setOnTouchListener(this);

//        //controls related callbacks
//        Button b = (Button) this.findViewById(R.id.button_close);
//        b.setOnClickListener(this);

        title.setOnFocusChangeListener(this);
        content.setOnFocusChangeListener(this);
        expandControls();
    }


    @Override
    public boolean onLongClick(View v) {
        View draggedView = this;
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(draggedView);
        draggedView.startDrag(data, shadowBuilder, draggedView, 0);
        draggedView.setVisibility(INVISIBLE);
        return true;
    }

    public void onTouchSub(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                ClipData data = ClipData.newPlainText("", "");
                int[] viewCoords = new int[2];
                this.getLocationOnScreen(viewCoords);
                //get touchPoint relative to note
                Point touchPoint = new Point((int) event.getRawX() - viewCoords[0], (int) event.getRawY()- viewCoords[1]);
                DragShadowBuilder shadowBuilder = new NoteShadowBuilder(this, touchPoint);
                this.startDrag(data, shadowBuilder, this, 0);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.onTouchSub(v, event);
        return false;
    }



//    private int _xDelta;
//    private int _yDelta;
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        final int X = (int) event.getRawX();
//        final int Y = (int) event.getRawY();
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) this.getLayoutParams();
//                _xDelta = X - lParams.leftMargin;
//                _yDelta = Y - lParams.topMargin;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();
//                layoutParams.leftMargin = X - _xDelta;
//                layoutParams.topMargin = Y - _yDelta;
//                this.setLayoutParams(layoutParams);
//                break;
//        }
//        parent.invalidate();
//        return false;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_close:
                parent.removeDisplayedNote(this);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            //increase left padding here
//            controls
            expandControls();
//            controls.setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_padding_full),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_suggestion_width),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding));
        } else {
            collapseControls();
            //decrease left padding here
//            controls
//            controls.setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_padding_folded),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_suggestion_width),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding));
        }
    }

    private void collapseControls() {
        PaddingAnimation pa = new PaddingAnimation(controls, new int[] {getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_padding_folded),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_suggestion_width),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding)});
        pa.setDuration(CONTROLS_ANIMATE_DURATION_MILLIS);
        controls.startAnimation(pa);
    }

    private void expandControls() {
        PaddingAnimation pa = new PaddingAnimation(controls, new int[] {getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_padding_full),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_suggestion_width),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding)});
        pa.setDuration(CONTROLS_ANIMATE_DURATION_MILLIS);
        controls.startAnimation(pa);
    }

    public void setIdentifyingColor(int color) {
        Drawable d = getResources().getDrawable(R.drawable.note_bg);
        d.setColorFilter(color, Mode.SRC_OVER);
        text.setBackground(d);
    }

}
