package fr.enst.infsi351.notedown;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link NoteFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link NoteFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class NoteView extends RelativeLayout {

    public NoteView(Context context) {
        super(context);
        init();
    }

    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_note, this);
        final View title = findViewById(R.id.title);
        final View content = findViewById(R.id.content);
        final View note = findViewById(R.id.note);

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
        note.setOnTouchListener(new OnTouchListener() {
            private int deltaX;
            private int deltaY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int X = (int) event.getRawX();
                int Y = (int) event.getRawY();
                int action = event.getAction()& MotionEvent.ACTION_MASK;
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        deltaX = X-params.leftMargin;
                        deltaY = Y-params.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)v.getLayoutParams();
                        params2.setMargins(X-deltaX,Y-deltaY,0,0);
                        v.setLayoutParams(params2);

                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


}
