package fr.enst.infsi351.notedown;

import android.content.Context;
import android.util.AttributeSet;
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
    }
}
