package fr.enst.infsi351.notedown.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import fr.enst.infsi351.notedown.R;
import fr.enst.infsi351.notedown.dialog.InputDialog;
import fr.enst.infsi351.notedown.dialog.InputDialog.OnCancel;
import fr.enst.infsi351.notedown.dialog.InputDialog.OnConfirm;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderFragment extends Fragment {

    public HeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page_header, container, false);
        v.findViewById(R.id.panic_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog d = new InputDialog(v.getContext(), "Confirm panic",
                        "Ask a question to the teacher ?",
                        new OnConfirm() {
                            @Override
                            public void onConfirm(String text) {
                                //do nothing !
                            }
                        },
                new OnCancel() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }
                });
                d.showDialog();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
