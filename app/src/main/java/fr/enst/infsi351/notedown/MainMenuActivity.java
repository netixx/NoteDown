package fr.enst.infsi351.notedown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import fr.enst.infsi351.notedown.FileChooserDialog.FileSelectedListener;
import fr.enst.infsi351.notedown.util.SystemUiHider;
import fr.enst.infsi351.notedown.util.TakeNoteSession;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    public void setSideBySideActivity(View view) {
        FileChooserDialog.chooseFile(this, ".pdf", new FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                startActivityWithPdf(SideBySideActivity.class, file);
            }
        });
    }

    public void setCutActivity(View view) {
        FileChooserDialog.chooseFile(this, ".pdf", new FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                startActivityWithPdf(CutActivity.class, file);
            }
        });
    }

    public void setNotesOnlyActivity(View view) {
        startActivity(NotesOnlyActivity.class);
    }

    public void startActivityWithPdf(Class<?> activityClass, File pdf) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(TakeNoteSession.TARGET_PDF, pdf.toString());
        startActivity(intent);
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
