package fr.enst.infsi351.notedown.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.enst.infsi351.notedown.FileChooserDialog;
import fr.enst.infsi351.notedown.FileChooserDialog.FileSelectedListener;
import fr.enst.infsi351.notedown.R;
import fr.enst.infsi351.notedown.util.RecentItem;
import fr.enst.infsi351.notedown.util.RecentListAdapter;
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
        ListView list = (ListView) findViewById(R.id.recent_sessions);
        final List<RecentItem> recent_list = new ArrayList<>();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        try {
            recent_list.add(new RecentItem("MAT311", f.parse("15-06-2011")));
            recent_list.add(new RecentItem("INFSI351", f.parse("13-04-2015")));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        RecentListAdapter arrayAdapter = new RecentListAdapter(this, recent_list);
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                your_array_list );


        list.setAdapter(arrayAdapter);
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
