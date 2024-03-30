package com.example.opengles3;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.opengles3.utils.GLUtils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ColorTriangle extends Activity {
    private GLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        mGlSurfaceView = findViewById(R.id.sfView);
        mGlSurfaceView.setEGLContextClientVersion(3);
        mGlSurfaceView.setRenderer(new MyRender(getApplicationContext()));
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

    public class MyRender implements GLSurfaceView.Renderer {
        private FloatBuffer vertexBuffer;
        private FloatBuffer colorBuffer;
        private GLUtils mGLUtils;

        public MyRender(Context context) {
            mGLUtils = new GLUtils(context);
            getFloatBuffer();
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            //设置背景颜色
            GLES30.glClearColor(0.1f, 0.2f, 0.3f, 0.4f);
            //编译着色器
            final int vertexShaderId = mGLUtils.compileShader(GLES30.GL_VERTEX_SHADER, R.raw.color_triangle_vertex_shader);
            final int fragmentShaderId = mGLUtils.compileShader(GLES30.GL_FRAGMENT_SHADER, R.raw.color_triangle_fragment_shader);
            //链接程序片段
            int programId = mGLUtils.linkProgram(vertexShaderId, fragmentShaderId);
            GLES30.glUseProgram(programId);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视图窗口
            GLES30.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //将颜色缓冲区设置为预设的颜色
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
            GLES30.glLineWidth(5);
            //启用顶点的数组句柄
            GLES30.glEnableVertexAttribArray(0);
            GLES30.glEnableVertexAttribArray(1);
            //准备顶点坐标和颜色数据
            GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer);
            GLES30.glVertexAttribPointer(1, 4, GLES30.GL_FLOAT, false, 0, colorBuffer);
//        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 3); //绘制三角形的三个顶点
//        GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, 3); //绘制三角形的三条边
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3); //绘制三角形的内部
            //禁止顶点数组句柄
            GLES30.glDisableVertexAttribArray(0);
            GLES30.glDisableVertexAttribArray(1);
        }

        private void getFloatBuffer() {
            float[] vertex = new float[] {
                    0.0f, 0.5f, 0.0f,
                    -0.5f, -0.5f, 0.0f,
                    0.5f, -0.5f, 0.0f
            };
            float[] color = {
                    0.0f, 1.0f, 0.0f, 1.0f,
                    1.0f, 0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f, 1.0f
            };
            vertexBuffer = mGLUtils.getFloatBuffer(vertex);
            colorBuffer = mGLUtils.getFloatBuffer(color);
        }
    }
}
