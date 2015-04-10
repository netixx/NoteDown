package fr.enst.infsi351.notedown;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import fr.enst.infsi351.notedown.ControlsFragment.OnNextClick;
import fr.enst.infsi351.notedown.ControlsFragment.OnPreviousClick;


public class CutActivity extends ActionBarActivity {

    PdfRendererFragment pdf;
    ControlsFragment controls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut);
        pdf = (PdfRendererFragment) getFragmentManager().findFragmentById(R.id.pdf);
        controls = (ControlsFragment) getFragmentManager().findFragmentById(R.id.controls);
        controls.setOnNextListener(new OnNextClick() {
            @Override
            public void onClick() {
                pdf.showNextPage();
                checkUi();
            }
        });
        controls.setOnPreviousListener(new OnPreviousClick() {
            @Override
            public void onClick() {
                pdf.showPreviousPage();
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
