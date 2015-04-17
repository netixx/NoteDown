package fr.enst.infsi351.notedown.view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.FrameLayout;

import fr.enst.infsi351.notedown.R;
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
public class NoteView extends FrameLayout implements OnTouchListener, View.OnClickListener, OnFocusChangeListener, OnLongClickListener, OnDragListener {

    public static final int CONTROLS_ANIMATE_DURATION_MILLIS = 100;

    public int minWidth;
    public int minHeight;

    private NotesAreaFragment parent;

    private View controls;
    private View text;
    private EditText title;
    private EditText content;

    public NoteView(Context context, NotesAreaFragment parent) {
        super(context);
        init(parent);
    }

    private void init(NotesAreaFragment parent) {
        minWidth = (int) getResources().getDimension(R.dimen.note_min_width);
        minHeight = (int) getResources().getDimension(R.dimen.note_min_height);
        this.parent = parent;
        inflate(getContext(), R.layout.view_note, this);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
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
        title.setOnDragListener(this);
        content.setOnDragListener(this);
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
            expandControls();
            expandView();
            if (!title.hasFocus()) {
                content.requestFocus();
            }
        } else {
            collapseControls();
            collapseView();
        }
    }

    public void collapseView() {
        //collapse content to one line
//        MaxHeightAnimation ha = new MaxHeightAnimation(content, (int) content.getTextSize());
//        ha.setDuration(CONTENT_ANIMATE_DURATION_MILLIS);
//        content.startAnimation(ha);
        content.setMaxHeight(getResources().getDimensionPixelSize(R.dimen.note_content_min_height));
    }

    public void expandView() {
//        int targetHeight = (int) (content.getLineCount() * content.getTextSize());
//        MaxHeightAnimation ha = new MaxHeightAnimation(content, targetHeight);
//        ha.setDuration(CONTENT_ANIMATE_DURATION_MILLIS);
//        content.startAnimation(ha);
        content.setMaxHeight(Integer.MAX_VALUE);
    }

    private void collapseControls() {
        PaddingAnimation pa = new PaddingAnimation(controls, new int[] {getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_padding_folded),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_suggestion_width),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding)});
        pa.setDuration(CONTROLS_ANIMATE_DURATION_MILLIS);
        controls.startAnimation(pa);
//            controls.setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_padding_folded),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_suggestion_width),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding));
    }

    private void expandControls() {
        PaddingAnimation pa = new PaddingAnimation(controls, new int[] {getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_padding_full),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_suggestion_width),
                getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding)});
        pa.setDuration(CONTROLS_ANIMATE_DURATION_MILLIS);
        controls.startAnimation(pa);
//            controls.setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_padding_full),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_suggestion_width),
//                    getContext().getResources().getDimensionPixelSize(R.dimen.note_side_menu_top_bottom_padding));

    }

    public void setIdentifyingColor(int color) {
        Drawable d = getResources().getDrawable(R.drawable.note_bg);
        d.setColorFilter(color, Mode.SRC_OVER);
        text.setBackground(d);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        System.out.println("NoteView.onDrag");
        System.out.println("event.getAction() = " + event.getAction());
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                System.out.println("drag dropped note");

                NoteView dv = (NoteView) event.getLocalState();
                if (this.equals(dv)) {
                    FrameLayout.LayoutParams lp = (LayoutParams) this.getLayoutParams();
                    float x = lp.leftMargin + event.getX();
                    float y = lp.topMargin + event.getY();
                    parent.dropDrag(event, x, y);
                } else {
                    this.mergeNote(dv);
                    parent.removeDisplayedNote(dv);
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                System.out.println("drag ended note");
//                    v.setBackground(normalShape);
//                View dv = (View) event.getLocalState();
//                dv.setVisibility(VISIBLE);
//                dv.invalidate();

            default:
                break;
        }
        return true;
    }

    public void mergeNote(NoteView note) {
        if (note.content.getText().length() > 0) {
            String link = "";
            if (this.content.getText().length() > 0)
                link = "\n";
            this.content.setText(this.content.getText() + link + note.content.getText());
        }
        if (note.title.getText().length() > 0) {
            String link = "";
            if (this.title.getText().length() > 0)
                link = " & ";
                this.title.setText(this.title.getText() + link + note.title.getText());
        }
    }
}
