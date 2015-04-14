package fr.enst.infsi351.notedown.fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import fr.enst.infsi351.notedown.R;

/**
 * Created by francois on 03/04/15.
 */
public class SideBySidePdfRendererFragment extends PdfRendererFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        final ViewGroup imageFrame = (ViewGroup) view.findViewById(R.id.image_frame);
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

}
