package fr.enst.infsi351.notedown;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class NotesOnlyActivity extends ActionBarActivity {
    NotesAreaFragment notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_only);
        notes = (NotesAreaFragment) getFragmentManager().findFragmentById(R.id.notes);
        if (notes.getCurrentPage() == 0) {
            Button prev = (Button) findViewById(R.id.previous);
            prev.setEnabled(false);
        }
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

    public void nextPage(View view) {
//        NotesAreaFragment notes = (NotesAreaFragment) getFragmentManager().findFragmentById(R.id.notes);
        notes.nextPage();
        checkUi();
    }

    public void previousPage(View view) {
//        NotesAreaFragment notes = (NotesAreaFragment) getFragmentManager().findFragmentById(R.id.notes);
        notes.previousPage();
        checkUi();
    }

    private void checkUi() {
        Button prev = (Button) findViewById(R.id.previous);
        if (notes.getCurrentPage() == 0) {
            prev.setEnabled(false);
        } else {
            prev.setEnabled(true);
        }
    }
}
