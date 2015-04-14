package fr.enst.infsi351.notedown.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import fr.enst.infsi351.notedown.R;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link NotesAreaFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link NotesAreaFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ControlsFragment extends Fragment {


    private View prevButton;
    private View nextButton;

    public interface OnNextClick {
        void onClick();
    }

    public interface OnPreviousClick {
        void onClick();
    }

    private OnPreviousClick previous;
    private OnNextClick next;

    public ControlsFragment() {
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
        View v = inflater.inflate(R.layout.fragment_controls, container, false);
        prevButton =v.findViewById(R.id.previous);
                prevButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previous != null)
                    previous.onClick();
            }
        });

        nextButton = v.findViewById(R.id.next);
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next != null)
                    next.onClick();
            }
        });
        return v;
    }

    public void setPreviousEnabled(boolean enabled) {
        prevButton.setEnabled(enabled);
    }

    public void setNextEnabled(boolean enabled) {
        nextButton.setEnabled(enabled);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void setOnPreviousListener(OnPreviousClick l) {
        this.previous = l;
    }

    public void setOnNextListener(OnNextClick l) {
        this.next = l;
    }
}
