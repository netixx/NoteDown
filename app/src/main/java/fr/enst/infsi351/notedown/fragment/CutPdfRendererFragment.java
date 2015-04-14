package fr.enst.infsi351.notedown.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import fr.enst.infsi351.notedown.R;

/**
 * Created by francois on 03/04/15.
 */
public class CutPdfRendererFragment extends PdfRendererFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        final ViewGroup imageFrame = (ViewGroup) view.findViewById(R.id.pdf_frame);
        mImageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mImageView.setDrawingCacheEnabled(true);
                        mImageView.setImageBitmap(splitBitmap(v.getDrawingCache(), event.getY()));
                        mImageView.setDrawingCacheEnabled(false);

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

    int splitSize = 200;
    private Bitmap splitBitmap(Bitmap bm, float x) {
        if (x >= bm.getHeight()) {
            return bm;
        }
        int newheight = bm.getHeight()+splitSize;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap newBm = Bitmap.createBitmap(bm.getWidth(), newheight , conf);
        Canvas c = new Canvas(newBm);
        Paint p = new Paint();
        //left, top, right, bottom
        //split at x
        Rect upBm = new Rect(0, 0, bm.getWidth(),(int) x);
        Rect downBm = new Rect(0, (int)x, bm.getWidth(), bm.getHeight());

        //add splitsize space
        RectF upScreen = new RectF(0, 0, newBm.getWidth(), x);
        RectF downScreen = new RectF(0, x+splitSize, newBm.getWidth(), newBm.getHeight());

        //copy on bitmap
        c.drawBitmap(bm, upBm, upScreen, p);
        c.drawBitmap(bm, downBm, downScreen, p);

        return newBm;
    }

    private Rect getImageBounds(ImageView img) {

//        int[] offset = new int[2];
        float[] values = new float[9];
//
        Matrix m = img.getImageMatrix();
        m.getValues(values);

        int top = (int) values[5];
        int left = (int) values[2];
        int bottom = 0;
        int right = 0;

//            if (includeLayout) {
//                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) img.getLayoutParams();
//                int paddingTop = (int) (img.getPaddingTop() );
//                int paddingLeft = (int) (img.getPaddingLeft() );
//
//                offset[0] += paddingTop + lp.topMargin;
//                offset[1] += paddingLeft + lp.leftMargin;
//            }
        return new Rect(left, top, right, bottom);
    }
}
