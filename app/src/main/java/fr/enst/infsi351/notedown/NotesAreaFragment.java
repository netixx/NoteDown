package fr.enst.infsi351.notedown;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import java.util.ArrayList;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link NotesAreaFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link NotesAreaFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class NotesAreaFragment extends Fragment {
    private int layout;
    private int INTER_NOTES_MARGIN = 0;
    private int current_page = 0;
    private ViewGroup area;

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
        INTER_NOTES_MARGIN = getResources().getDimensionPixelSize(R.dimen.notes_inter_margin);

        // Inflate the layout for this fragment
        layout = R.layout.fragment_notes_area;
        View v = inflater.inflate(layout, container, false);
        area = (ViewGroup) v.findViewById(R.id.notes_area);
        v.setOnTouchListener(
                new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                addNewNote(area, pages.get(current_page), event.getX(), event.getY());
                            default:

                        }
                        return true;
                    }
                }
        );


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
        if (n > pages.size()) {
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

    public void addNewNote(final ViewGroup parent, ArrayList<NoteView> page, float x, float y) {
        NoteView note = new NoteView(this.getActivity(), parent);
        FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        Point insertPoint = getInsertionPoint(page, (int) x, (int) y, note.minWidth, note.minHeight);
        params.setMargins(insertPoint.x, insertPoint.y, 0, 0);
        page.add(note);
        parent.addView(note, params);
    }


    public Point getInsertionPoint(ArrayList<NoteView> page, int x, int y, int width, int height) {
        Point p = new Point(x, y);
        //prevent overlapping of notes on insertion
        for (View note : page) {
            FrameLayout.LayoutParams par = (LayoutParams) note.getLayoutParams();
            Rect rect = new Rect(par.leftMargin, par.topMargin, par.leftMargin+note.getWidth(), par.topMargin+note.getHeight());
//            int[] l = new int[2];
//            note.getLocationOnScreen(l);
//            Rect rect = new Rect(l[0], l[1], l[0] + note.getWidth(), l[1] + note.getHeight());
            if (rect.intersects(p.x, p.y, p.x + width, p.y + height)) {
                //bottom line
                if (rect.intersects(p.x, p.y+height, p.x+width,p.y+height)) {
                    //move new Note up
                    p.y = Math.min(p.y, rect.top - height - INTER_NOTES_MARGIN);
                }
                //right line
                if (rect.intersects(p.x+width, p.y, p.x+width,p.y+height)) {
                    //move note left
                    p.x = Math.min(p.x, rect.left - width - INTER_NOTES_MARGIN);
                }

            }
        }
        return p;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
