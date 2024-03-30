package com.example.opengles3;

import android.app.Activity;
import android.graphics.Color;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PureColorActivity extends Activity {
    private GLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        mGlSurfaceView = findViewById(R.id.sfView);
        mGlSurfaceView.setEGLContextClientVersion(3);
        mGlSurfaceView.setRenderer(new MyRender());
        mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyRender implements GLSurfaceView.Renderer {
        private float[] colorArr;

        public MyRender() {
            colorArr = getColorArr(Color.CYAN);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            GLES30.glClearColor(colorArr[0], colorArr[1], colorArr[2], colorArr[3]);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT);
        }

        private float[] getColorArr(int color) {
            float[] colorArr = new float[4];
            colorArr[0] = (float) Color.red(color) / 255;
            colorArr[1] = (float) Color.blue(color) / 255;
            colorArr[2] = (float) Color.green(color) / 255;
            colorArr[3] = (float) Color.alpha(color) / 255;
            return colorArr;
        }
    }
}
