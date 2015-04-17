package fr.enst.infsi351.notedown.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import java.util.ArrayList;

import fr.enst.infsi351.notedown.view.NoteView;
import fr.enst.infsi351.notedown.R;
import fr.enst.infsi351.notedown.animation.WidthAnimation;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link NotesAreaFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link NotesAreaFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class NotesAreaFragment extends Fragment implements OnDragListener {
    public static final int TRASH_OPEN_DURATION_MILLIS = 200;
    private int layout;
    private OnNoteDeleteListener onNoteDeleteListener;

    public interface OnNoteDeleteListener {
        void noteDeleted(NoteView note);
    }

//    private int INTER_NOTES_MARGIN = 0;

    public int getCurrentPage() {
        return current_page;
    }

    private int current_page = 0;
    private ViewGroup area;
    private ViewGroup trash;

    private ArrayList<ArrayList<NoteView>> pages = new ArrayList<>();


    public NotesAreaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        createPage();
        current_page = 0;
//        INTER_NOTES_MARGIN = getResources().getDimensionPixelSize(R.dimen.notes_inter_margin);

        // Inflate the layout for this fragment
        layout = R.layout.fragment_notes_area;
        View v = inflater.inflate(layout, container, false);
        area = (ViewGroup) v.findViewById(R.id.notes_area);
        trash = (ViewGroup) v.findViewById(R.id.notes_trash);
        v.setOnTouchListener(
                new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, final MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
//                                AsyncTask<Void, Void, Void> t = new AsyncTask<Void, Void, Void>() {
//                                    @Override
//                                    protected Void doInBackground(Void... params) {
//                                        return null;
//                                    }
//
//                                    protected void onPreExecute() {
//                                        addNewNote(pages.get(current_page), event.getX(), event.getY());
//                                    }
//                                    protected void onPostExecute(Void result) {
//                                        super.onPostExecute(result);
//                                        for (View note : pages.get(current_page)) {
//                                            System.out.println("note.getWidth() = " + note.getWidth());
//                                            System.out.println("note.getHeight() = " + note.getHeight());}
//                                    }
//                                };
//                                t.execute();
                                addNewNote(event.getX(), event.getY());
                            default:

                        }
                        return true;
                    }
                }
        );
        area.setOnDragListener(this);

        trash.setOnDragListener(new TrashDrag());

        return v;
    }

    public void previousPage() {
        if (current_page >= 1) {
            displayPage(--current_page, area);
        }
    }

    public void nextPage() {
        displayPage(++current_page, area);
    }

    private void displayPage(int n, ViewGroup parent) {
        if (n >= pages.size()) {
            createPage();
        }
        //remove all notes
        parent.removeAllViews();
        //add notes from current page
        for (View v : pages.get(n)) {
            parent.addView(v, v.getLayoutParams());
        }
    }

    public void createPage() {
        pages.add(new ArrayList<NoteView>());
    }

    public NoteView addNewNoteToPage(final ArrayList<NoteView> page, final float x, final float y) {
        final NoteView note = new NoteView(this.getActivity(), this);
        //hide and add view to get height and width
        FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.setMargins((int) x , (int) y, 0, 0);
        area.addView(note, params);
        page.add(note);
        return note;
    }

    public NoteView addNewNote(final float x, final float y) {
        return addNewNoteToPage(pages.get(current_page), x, y);
    }

//    public void addNewNote(final ArrayList<NoteView> page, final float x, final float y) {
//        final NoteView note = new NoteView(this.getActivity(), this);
//        //hide and add view to get height and width
//        FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//        params.setMargins((int)x , (int) y, 0, 0);
//        area.addView(note, params);
//        page.add(note);
//        note.setVisibility(View.INVISIBLE);
//
//        // when view in draw, we calculate it's size and adjust it's position
//        final ViewTreeObserver vto = area.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//                Point insertPoint = getInsertionPoint(page, (int) x, (int) y, note.getWidth(), note.getHeight());
//                params.setMargins(insertPoint.x, insertPoint.y, 0, 0);
//                note.setLayoutParams(params);
//                note.setVisibility(View.VISIBLE);
//                vto.removeOnGlobalLayoutListener(this);
//            }
//        });
//
//    }
//
//
//    public Point getInsertionPoint(ArrayList<NoteView> page, int x, int y, int width, int height) {
//        Point p = new Point(x, y);
//        //prevent overlapping of notes on insertion
//        for (View note : page) {
//            FrameLayout.LayoutParams par = (LayoutParams) note.getLayoutParams();
//            Rect rect = new Rect(par.leftMargin, par.topMargin, par.leftMargin+note.getWidth(), par.topMargin+note.getHeight());
////            int[] l = new int[2];
////            note.getLocationOnScreen(l);
////            Rect rect = new Rect(l[0], l[1], l[0] + note.getWidth(), l[1] + note.getHeight());
//            if (rect.intersects(p.x, p.y, p.x + width, p.y + height)) {
//                //bottom line
//                if (rect.intersects(p.x, p.y+height, p.x+width,p.y+height)) {
//                    //move new Note up
//                    p.y = Math.min(p.y, rect.top - height - INTER_NOTES_MARGIN);
//                }
//                //right line
//                if (rect.intersects(p.x+width, p.y, p.x+width,p.y+height)) {
//                    //move note left
//                    p.x = Math.min(p.x, rect.left - width - INTER_NOTES_MARGIN);
//                }
//
//                //top line
//                if (rect.intersects(p.x, p.y, p.x+width,p.y)) {
//                    //move new Note up
//                    p.y = Math.min(p.y, rect.top - height - INTER_NOTES_MARGIN);
//                }
//                //left line
//                if (rect.intersects(p.x, p.y, p.x,p.y+height)) {
//                    //move note left
//                    p.x = Math.max(p.x, rect.right + INTER_NOTES_MARGIN);
//                }
//
//            }
//        }
//        return p;
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    public void removeDisplayedNote(NoteView v) {
        pages.get(current_page).remove(v);
        area.removeView(v);
        if (onNoteDeleteListener != null) {
            onNoteDeleteListener.noteDeleted(v);
        }
    }

    public void invalidate() {
        area.invalidate();
    }

    private class TrashDrag implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //open trash panel
                    WidthAnimation wao = new WidthAnimation(trash, getResources().getDimensionPixelSize(R.dimen.trash_full_width));
                    wao.setDuration(TRASH_OPEN_DURATION_MILLIS);
                    trash.startAnimation(wao);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(getResources().getColor(R.color.trash_hover_color));
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(getResources().getColor(R.color.trash_normal_color));
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign remove Note
                    NoteView tv = (NoteView) event.getLocalState();
                    NotesAreaFragment.this.removeDisplayedNote(tv);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(getResources().getColor(R.color.trash_normal_color));
                    //open trash panel
                    WidthAnimation wac = new WidthAnimation(trash, getResources().getDimensionPixelSize(R.dimen.trash_folded_width));
                    wac.setDuration(TRASH_OPEN_DURATION_MILLIS);
                    trash.startAnimation(wac);
                default:
                    break;
            }
            return true;
        }
    }
    private float _offsetx = 0;
    private float _offsety = 0;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
//                NoteView tv = (NoteView) event.getLocalState();
//                FrameLayout.LayoutParams o2LayoutParams = (FrameLayout.LayoutParams) tv.getLayoutParams();
//                _offsetx = o2LayoutParams.leftMargin - event.getX();
//                _offsety = o2LayoutParams.topMargin - event.getY();
//                System.out.println("drag started");
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackground(enterShape);
                System.out.println("drag entered");
                NoteView otv = (NoteView) event.getLocalState();
                FrameLayout.LayoutParams oLayoutParams = (FrameLayout.LayoutParams) otv.getLayoutParams();
                _offsetx = oLayoutParams.leftMargin - event.getX();
                _offsety = oLayoutParams.topMargin - event.getY();
                otv.setOnDragListener(null);
                otv.setEnabled(false);
                otv.setVisibility(View.GONE);
//                otv.setVisibility(View.INVISIBLE);
                otv.invalidate();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackground(normalShape);
//                System.out.println("drag exited");
                break;
            case DragEvent.ACTION_DROP:
                this.dropDrag(event, event.getX(), event.getY());
                break;
            case DragEvent.ACTION_DRAG_ENDED:
//                System.out.println("drag ended");
//                    v.setBackground(normalShape);
//                this.dropDrag(event, event.getX(), event.getY());
//                NoteView tv = (NoteView) event.getLocalState();
//                tv.setVisibility(View.VISIBLE);
//                tv.setEnabled(true);
//                tv.invalidate();
//                tv.setOnDragListener(tv);
            default:
                break;
        }
        return true;
    }


    public void dropDrag(DragEvent event, float x, float y) {
        System.out.println("_offsetx = " + _offsetx);
        NoteView tv = (NoteView) event.getLocalState();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) tv.getLayoutParams();
        layoutParams.setMargins((int) (x+_offsetx),(int) (y+_offsety), 0, 0);
        tv.setLayoutParams(layoutParams);
        tv.setVisibility(View.VISIBLE);
        tv.setEnabled(true);
        tv.invalidate();
    }

    public void setOnNoteDeleteListener(OnNoteDeleteListener l) {
        this.onNoteDeleteListener = l;
    }
}
