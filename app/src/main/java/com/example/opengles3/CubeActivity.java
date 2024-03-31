package com.example.opengles3;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.opengles3.utils.GLUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CubeActivity extends Activity {
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
        private ByteBuffer indexBuffer;
        private GLUtils mGLUtils;
        private int mProgramId;
        private float mRatio;

        public MyRender(Context context) {
            mGLUtils = new GLUtils(context);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            //设置背景颜色
            GLES30.glClearColor(0.1f, 0.2f, 0.3f, 0.4f);
            //启动深度测试
            gl.glEnable(GLES30.GL_DEPTH_TEST);
            //编译着色器
            final int vertexShaderId = mGLUtils.compileShader(GLES30.GL_VERTEX_SHADER, R.raw.cube_vertex_shader);
            final int fragmentShaderId = mGLUtils.compileShader(GLES30.GL_FRAGMENT_SHADER, R.raw.cube_fragment_shader);
            //链接程序片段
            mProgramId = mGLUtils.linkProgram(vertexShaderId, fragmentShaderId);
            GLES30.glUseProgram(mProgramId);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视图窗口
            GLES30.glViewport(0, 0, width, height);
            getFloatBuffer();
            mRatio = 1.0f * width / height;
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //将颜色缓冲区设置为预设的颜色
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
            mGLUtils.transform(mProgramId, mRatio); //计算MVP变换矩阵
            //启用顶点的数组句柄
            GLES30.glEnableVertexAttribArray(0);
            GLES30.glEnableVertexAttribArray(1);
            //准备顶点坐标和颜色数据
            GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer);
            GLES30.glVertexAttribPointer(1, 4, GLES30.GL_FLOAT, false, 0, colorBuffer);
            //绘制正方体的表面（6个面，每面2个三角形，每个三角形3个顶点）
            gl.glDrawElements(GLES30.GL_TRIANGLES, 6 * 2 * 3, GLES30.GL_UNSIGNED_BYTE, indexBuffer);
            //禁止顶点数组句柄
            GLES30.glDisableVertexAttribArray(0);
            GLES30.glDisableVertexAttribArray(1);
        }

        private void getFloatBuffer() {
            float r = 1.0f;
            float[] vertex = new float[] {
                    r, r, r, //0
                    -r, r, r, //1
                    -r, -r, r, //2
                    r, -r, r, //3
                    r, r, -r, //4
                    -r, r, -r, //5
                    -r, -r, -r, //6
                    r, -r, -r //7
            };
            byte[] index = new byte[] {
                    0, 2, 1, 0, 2, 3, //前面
                    0, 5, 1, 0, 5, 4, //上面
                    0, 7, 3, 0, 7, 4, //右面
                    6, 4, 5, 6, 4, 7, //后面
                    6, 3, 2, 6, 3, 7, //下面
                    6, 1, 2, 6, 1, 5 //左面
            };
            float c = 1.0f;
            float[] color = new float[] {
                    c, c, c, 1,
                    0, c, c, 1,
                    0, 0, c, 1,
                    c, 0, c, 1,
                    c, c, 0, 1,
                    0, c, 0, 1,
                    0, 0, 0, 1,
                    c, 0, 0, 1
            };
            vertexBuffer = mGLUtils.getFloatBuffer(vertex);
            indexBuffer = mGLUtils.getByteBuffer(index);
            colorBuffer = mGLUtils.getFloatBuffer(color);
        }
    }
}
