package fr.enst.infsi351.notedown;

import fr.enst.infsi351.notedown.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
    }

    public void setPDFActivity(View view) {
        Intent intent = new Intent(this, FullscreenActivity.class);
        startActivity(intent);
    }
}
