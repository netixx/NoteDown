package fr.enst.infsi351.notedown;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link NoteFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link NoteFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class NoteView extends RelativeLayout implements OnTouchListener, View.OnClickListener {

    public int minWidth;
    public int minHeight;

    private View parent;
    public NoteView(Context context, View parent) {
        super(context);
        init(parent);
    }

    private void init(View parent) {
        minWidth = (int) getResources().getDimension(R.dimen.note_min_width);
        minHeight = (int) getResources().getDimension(R.dimen.note_min_height);
        this.parent = parent;
        inflate(getContext(), R.layout.view_note, this);
        final View title = findViewById(R.id.title);
        final View content = findViewById(R.id.content);
//        this.setNextFocusDownId(R.id.title);

        title.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_TAB)) {
                    // Perform action on Enter key press
                    title.clearFocus();
                    content.requestFocus();
                    return true;
                }
                return false;
            }
        });
        this.setOnTouchListener(this);
        title.setOnTouchListener(this);
        content.setOnTouchListener(this);

        Button b = (Button) this.findViewById(R.id.button_close);
        b.setOnClickListener(this);

    }

    private int _xDelta;
    private int _yDelta;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) this.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
//                        layoutParams.rightMargin = -250;
//                        layoutParams.bottomMargin = -250;
                this.setLayoutParams(layoutParams);
                break;
        }
        parent.invalidate();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_close:
                this.removeAllViews();
                break;
        }
    }
}
