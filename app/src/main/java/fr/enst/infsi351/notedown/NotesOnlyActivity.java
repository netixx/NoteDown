package fr.enst.infsi351.notedown;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fr.enst.infsi351.notedown.ControlsFragment.OnNextClick;
import fr.enst.infsi351.notedown.ControlsFragment.OnPreviousClick;


public class NotesOnlyActivity extends Activity{
    NotesAreaFragment notes;
    ControlsFragment controls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_only);
        notes = (NotesAreaFragment) getFragmentManager().findFragmentById(R.id.notes);
        controls = (ControlsFragment) getFragmentManager().findFragmentById(R.id.controls);
        controls.setOnNextListener(new OnNextClick() {
            @Override
            public void onClick() {
                notes.nextPage();
                checkUi();
            }
        });
        controls.setOnPreviousListener(new OnPreviousClick() {
            @Override
            public void onClick() {
                notes.previousPage();
                checkUi();
            }
        });
        checkUi();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes_only, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        checkUi();
        return super.onOptionsItemSelected(item);
    }

    private void checkUi() {
        if (notes.getCurrentPage() == 0) {
            controls.setPreviousEnabled(false);
        } else {
            controls.setPreviousEnabled(true);
        }
    }


}
