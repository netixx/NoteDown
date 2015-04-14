package fr.enst.infsi351.notedown.fragment;/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enst.infsi351.notedown.PdfMarker;
import fr.enst.infsi351.notedown.R;
import fr.enst.infsi351.notedown.util.PdfEngine;
import fr.enst.infsi351.notedown.util.TakeNoteSession;

/**
 * This fragment has a big {@ImageView} that shows PDF pages, and 2 {@link android.widget.Button}s to move between
 * pages. We use a {@link android.graphics.pdf.PdfRenderer} to render PDF pages as {@link android.graphics.Bitmap}s.
 *
 *
 */
public class PdfRendererFragment extends Fragment {

    private Map<Integer, List<PdfMarker>> markers = new HashMap<>();
    /**
     * Key string for saving the state of current page index.
     */
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";

    /**
     * {@link android.widget.ImageView} that shows a PDF page as a {@link android.graphics.Bitmap}
     */
    protected ImageView mImageView;

    private ViewGroup frame;
    private PdfEngine engine;

    public PdfRendererFragment() {
        engine = new PdfEngine();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pdf_renderer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Retain view references.
        mImageView = (ImageView) view.findViewById(R.id.image);
        frame = (ViewGroup) view.findViewById(R.id.pdf_frame);
//        mButtonPrevious = (Button) view.findViewById(R.id.previous);
//        mButtonNext = (Button) view.findViewById(R.id.next);
//        // Bind events.
//        mButtonPrevious.setOnClickListener(this);
//        mButtonNext.setOnClickListener(this);
        // Show the first page by default.
        int index = 0;
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState) {
            index = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
        }
        engine.showPage(index, mImageView);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        File targetPdf = new File(activity.getIntent().getStringExtra(TakeNoteSession.TARGET_PDF));
        try {
            engine.openRenderer(targetPdf);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

    @Override
    public void onDetach() {
        try {
            engine.closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != engine.getCurrentPage()) {
            outState.putInt(STATE_CURRENT_PAGE_INDEX, engine.getCurrentPage().getIndex());
        }
    }

    public void showNextPage() {
        showPage(engine.getCurrentPage().getIndex(), 1);
    }

    public void showPreviousPage() {
        showPage(engine.getCurrentPage().getIndex(), -1);
    }

    private void showPage(int currentPage, int move) {
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

    public int getCurrentPageIndex() {
        return engine.getCurrentPage().getIndex();
    }

    public int getPageCount() {
        return engine.getPageCount();
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
