package fr.enst.infsi351.notedown.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.io.File;

import fr.enst.infsi351.notedown.NoteView;
import fr.enst.infsi351.notedown.PdfMarker;
import fr.enst.infsi351.notedown.R;
import fr.enst.infsi351.notedown.fragment.ControlsFragment;
import fr.enst.infsi351.notedown.fragment.ControlsFragment.OnNextClick;
import fr.enst.infsi351.notedown.fragment.ControlsFragment.OnPreviousClick;
import fr.enst.infsi351.notedown.fragment.NotesAreaFragment;
import fr.enst.infsi351.notedown.fragment.NotesAreaFragment.OnNoteDeleteListener;
import fr.enst.infsi351.notedown.fragment.SideBySidePdfRendererFragment;
import fr.enst.infsi351.notedown.util.BiMap;
import fr.enst.infsi351.notedown.util.TakeNoteSession;


public class SideBySideActivity extends Activity {

    private BiMap<NoteView, PdfMarker> noteMarkerBiMap = new BiMap<>();

//    private HashMap<NoteView, PdfMarker> noteMarkerMap = new HashMap<>();
//    private HashMap<PdfMarker, NoteView> markerNoteMap = new HashMap<>();


//    public Bundle session;
    SideBySidePdfRendererFragment pdf;
    NotesAreaFragment notes;
    ControlsFragment controls;

    File targetPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_by_side);
        String selectedPdf = this.getIntent().getStringExtra(TakeNoteSession.TARGET_PDF);
        targetPdf = new File(selectedPdf);

        pdf = (SideBySidePdfRendererFragment) getFragmentManager().findFragmentById(R.id.pdf);
        controls = (ControlsFragment) getFragmentManager().findFragmentById(R.id.controls);
        notes = (NotesAreaFragment) getFragmentManager().findFragmentById(R.id.notes);

        controls.setOnNextListener(new OnNextClick() {
            @Override
            public void onClick() {
                pdf.showNextPage();
                notes.nextPage();
                checkUi();
            }
        });
        controls.setOnPreviousListener(new OnPreviousClick() {
            @Override
            public void onClick() {
                pdf.showPreviousPage();
                notes.previousPage();
                checkUi();
            }
        });
        checkUi();

        notes.setOnNoteDeleteListener(new OnNoteDeleteListener() {
            @Override
            public void noteDeleted(NoteView note) {
                if (noteMarkerBiMap.containsKey(note)) {
                    pdf.removeMarkerFromCurrentPage(noteMarkerBiMap.get(note));
                    //remove from maps
                    noteMarkerBiMap.remove(note);
                }
            }
        });

        View pdfArea = findViewById(R.id.pdf_frame);
        pdfArea.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int color = getResources().getColor(R.color.trash_hover_color);
                NoteView note = notes.addNewNote(event.getX(), event.getY());
                PdfMarker marker = new PdfMarker(v.getContext(), (int) event.getX(), (int) event.getY());
                note.setIdentifyingColor(color);
                marker.setIdentifyingColor(color);
                pdf.addMarkerToCurrentPage(marker);
                noteMarkerBiMap.put(note, marker);
                marker.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        noteMarkerBiMap.reverseGet((PdfMarker) v).requestFocus();
                        return true;
                    }
                });
                return false;
            }
        });
    }

    public void checkUi() {
        int index = pdf.getCurrentPageIndex();
        controls.setPreviousEnabled(0 != index);
        controls.setNextEnabled(index + 1 < pdf.getPageCount());
    }

}
