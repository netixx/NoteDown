package fr.enst.infsi351.notedown;

import android.app.Activity;
import android.os.Bundle;

import java.io.File;

import fr.enst.infsi351.notedown.util.TakeNoteSession;


public class SideBySideActivity extends Activity {

//    public Bundle session;

    File targetPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_by_side);
        String selectedPdf = this.getIntent().getStringExtra(TakeNoteSession.TARGET_PDF);
        targetPdf = new File(selectedPdf);
    }

}
