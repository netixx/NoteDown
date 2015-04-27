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
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.enst.infsi351.notedown.R;
import fr.enst.infsi351.notedown.view.CutNoteView;

/**
 * Created by francois on 03/04/15.
 */
public class CutPdfRendererFragment extends PdfRendererFragment {

    private HashMap<Integer, List<CutNoteView>> cuts = new HashMap<>();

    int splitSize = 200;
    private ViewGroup imageFrame;

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        splitSize = getResources().getDimensionPixelSize(R.dimen.cut_note_height);
        super.onViewCreated(view, savedInstance);
        imageFrame = (ViewGroup) view.findViewById(R.id.pdf_frame);
        mImageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        double heightFact = cutAt(event.getY());
                        addCut(heightFact, event.getY());

                    default:

                }
                return false;
            }
        });
    }

    public double cutAt(float cutPoint) {
        mImageView.setDrawingCacheEnabled(true);
        Bitmap oBitmap = mImageView.getDrawingCache();
        Bitmap newBitmap = splitBitmap(oBitmap, cutPoint);
        mImageView.setImageBitmap(newBitmap);
        double heightFact = ((double) oBitmap.getHeight())/newBitmap.getHeight();
        mImageView.setDrawingCacheEnabled(false);
        return heightFact;
    }

    @Override
    public void showPage(int currentPage, int move)  {
        engine.showPage(currentPage + move, mImageView);
        //add cuts here
        if (cuts.containsKey(currentPage+move)) {
            for (CutNoteView c : cuts.get(currentPage+move)) {
            }
        }
    }

    private void addCut(double heightFactor, float cutPoint) {
        if (! cuts.containsKey(getCurrentPageIndex())) {
            cuts.put(getCurrentPageIndex(), new ArrayList<CutNoteView>());
        }
        List<CutNoteView> c = cuts.get(getCurrentPageIndex());
        for (CutNoteView n : c) {
            //move note according to new redimensionning
            FrameLayout.LayoutParams p = (LayoutParams) n.getLayoutParams();
            p.height = (int) (p.height * heightFactor);
            p.setMargins(p.leftMargin, (int) (n.initialY*heightFactor), 0, 0);
            n.invalidate();
        }
        Rect drawBounds = mImageView.getDrawable().getBounds();
        Rect bounds = getImageBounds(mImageView);
        CutNoteView note = new CutNoteView(imageFrame.getContext(), (int) cutPoint);
        FrameLayout.LayoutParams params = new LayoutParams(imageFrame.getWidth(), (int) (splitSize*heightFactor));
        params.setMargins(bounds.left, (int) (cutPoint * heightFactor), 0, 0);
        imageFrame.addView(note, params);
        c.add(note);

    }

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
