package fr.enst.infsi351.notedown.activity;

import android.app.Activity;
import android.os.Bundle;

import java.io.File;

import fr.enst.infsi351.notedown.R;
import fr.enst.infsi351.notedown.fragment.ControlsFragment;
import fr.enst.infsi351.notedown.fragment.ControlsFragment.OnNextClick;
import fr.enst.infsi351.notedown.fragment.ControlsFragment.OnPreviousClick;
import fr.enst.infsi351.notedown.fragment.NotesAreaFragment;
import fr.enst.infsi351.notedown.fragment.PdfRendererFragment;
import fr.enst.infsi351.notedown.util.TakeNoteSession;


public class SideBySideActivity extends Activity {

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
    }

    public void checkUi() {
        int index = pdf.getCurrentPageIndex();
        controls.setPreviousEnabled(0 != index);
        controls.setNextEnabled(index + 1 < pdf.getPageCount());
    }

}
