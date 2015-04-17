package fr.enst.infsi351.notedown.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

public class InputDialog {

//    private final String title;
//    private final String inputHint;
    private AlertDialog.Builder alert;
    private final EditText input;

    public InputDialog(Context c, String title, String inputHint, final OnConfirm confirm, final OnCancel cancel) {
//        this.title = title;
//        this.inputHint = inputHint;

        alert = new AlertDialog.Builder(c);

        alert.setTitle(title);
//        alert.setMessage("Message");

        // Set an EditText view to get user input
        input = new EditText(c);
        input.setHint(inputHint);
        alert.setView(input);

        alert.setPositiveButton("Ok", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (confirm != null)
                    confirm.onConfirm(input.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cancel != null)
                    cancel.onCancel();
            }
        });

    }

    public interface OnConfirm {
        void onConfirm(String text);
    }

    public interface OnCancel {
        void onCancel();
    }

    public void showDialog() {
        alert.show();
    }

}

