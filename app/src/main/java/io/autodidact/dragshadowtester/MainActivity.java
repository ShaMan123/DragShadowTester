package io.autodidact.dragshadowtester;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    View mShadow;
    int i = 0;
    DragWrapper mCurrentDragger;

    static final String TAG = "DragShadowBuilder";

    static class DragShadow extends View.DragShadowBuilder {
        DragShadow(View view) {
            super(view);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
            Log.d(TAG, "onProvideShadowMetrics: " + outShadowSize + "  " + outShadowTouchPoint);
        }
    }

    class DragWrapper implements View.OnTouchListener, View.OnDragListener {
        final View mView;
        DragWrapper(View v) {
            mView = v;
            mView.setOnTouchListener(this);
            mView.setOnDragListener(this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d(TAG, "Drag START " + mView);
                mView.startDragAndDrop(null, new DragShadow(mView), null, 0);
                mCurrentDragger = this;
                return true;
            }
            return false;
        }

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED && mCurrentDragger == this) {
                mCurrentDragger = null;
                Log.d(TAG, "Drag END " + mView);
            }
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrollView scrollView = findViewById(R.id.scroll);
        Button button= findViewById(R.id.button);
        SeekBar seekBar= findViewById(R.id.seekbar);
        View block = findViewById(R.id.block);
        View largeBlock = findViewById(R.id.large_block);
        View blockChild = findViewById(R.id.block_child);
        View blockChild2 = findViewById(R.id.block_child2);
        Switch switchView = findViewById(R.id.switchView);

        final View[] shadows = new View[]{button, switchView, largeBlock, block, scrollView, seekBar};
        ArrayList<DragWrapper> dragWrappers = new ArrayList<>();

        for (View v: shadows) {
            dragWrappers.add(new DragWrapper(v));
        }

        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mShadow = shadows[i++ % shadows.length];
                if (mCurrentDragger != null) {
                    Log.d(TAG, "Drag shadow UPDATE " + mShadow);
                    mCurrentDragger.mView.updateDragShadow(new DragShadow(mShadow));
                }
            }
        }, 0, 1000);

    }
}
