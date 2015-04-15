package fr.enst.infsi351.notedown.fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enst.infsi351.notedown.view.PdfMarker;
import fr.enst.infsi351.notedown.R;

/**
 * Created by francois on 03/04/15.
 */
public class SideBySidePdfRendererFragment extends PdfRendererFragment {

    private ViewGroup frame;


    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        frame = (ViewGroup) view.findViewById(R.id.pdf_frame);
        mImageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        mImageView.setDrawingCacheEnabled(true);
//                        mImageView.setImageBitmap(splitBitmap(v.getDrawingCache(), event.getY()));
//                        mImageView.setDrawingCacheEnabled(false);

//                        Rect bounds = getImageBounds(mImageView);
//                        View note = new NoteView(v.getContext());
//                        FrameLayout.LayoutParams params = new LayoutParams(mImageView.getWidth(),splitSize);
//                        System.out.println(mImageView.getDrawable().getBounds().left);
//                        params.setMargins(bounds.left, (int) event.getY(), 0, 0);
//                        imageFrame.addView(note, params);
                    default:

                }
                return false;
            }
        });

    }

    private Map<Integer, List<PdfMarker>> markers = new HashMap<>();

    @Override
    protected void showPage(int currentPage, int move) {
        clearMarkers(currentPage);
        engine.showPage(currentPage+move, mImageView);
        renderMarkers(currentPage + move);
    }

    private void clearMarkers(int currentPage) {
        if (markers.containsKey(currentPage)) {
            for (PdfMarker m : markers.get(currentPage)) {
                frame.removeView(m);
            }
        }
    }

    public void renderMarkers(int index) {
        if (markers.containsKey(index)) {
            for (PdfMarker m : markers.get(index)) {
                frame.addView(m);
            }
        }
    }

    public void addMarkerToCurrentPage(PdfMarker m) {
        int index = engine.getCurrentPage().getIndex();
        if (! markers.containsKey(index)) {
            markers.put(index, new ArrayList<PdfMarker>());
        }
        markers.get(index).add(m);
        frame.addView(m);
    }

    public void removeMarkerFromCurrentPage(PdfMarker m) {
        int index = engine.getCurrentPage().getIndex();
        if (markers.containsKey(index)) {
            markers.get(index).remove(m);
        }
        frame.removeView(m);
    }

}
