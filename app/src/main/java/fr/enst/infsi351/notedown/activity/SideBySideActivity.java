package fr.enst.infsi351.notedown.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.io.File;
import java.util.HashMap;

import fr.enst.infsi351.notedown.NoteView;
import fr.enst.infsi351.notedown.PdfMarker;
import fr.enst.infsi351.notedown.R;
import fr.enst.infsi351.notedown.fragment.ControlsFragment;
import fr.enst.infsi351.notedown.fragment.ControlsFragment.OnNextClick;
import fr.enst.infsi351.notedown.fragment.ControlsFragment.OnPreviousClick;
import fr.enst.infsi351.notedown.fragment.NotesAreaFragment;
import fr.enst.infsi351.notedown.fragment.NotesAreaFragment.OnNoteDeleteListener;
import fr.enst.infsi351.notedown.fragment.PdfRendererFragment;
import fr.enst.infsi351.notedown.util.TakeNoteSession;


public class SideBySideActivity extends Activity {

    private HashMap<NoteView, PdfMarker> noteMarkerMap = new HashMap<>();
    private HashMap<PdfMarker, NoteView> markerNoteMap = new HashMap<>();


//    public Bundle session;
    PdfRendererFragment pdf;
    NotesAreaFragment notes;
    ControlsFragment controls;

    File targetPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_by_side);
        String selectedPdf = this.getIntent().getStringExtra(TakeNoteSession.TARGET_PDF);
        targetPdf = new File(selectedPdf);


        pdf = (PdfRendererFragment) getFragmentManager().findFragmentById(R.id.pdf);
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
                if (noteMarkerMap.containsKey(note)) {
                    pdf.removeMarkerFromCurrentPage(noteMarkerMap.get(note));
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
                note.setBackgroundColor(color);
                marker.setColor(color);
                pdf.addMarkerToCurrentPage(marker);
                noteMarkerMap.put(note, marker);
                markerNoteMap.put(marker, note);
                marker.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        markerNoteMap.get(v).requestFocus();
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
