package com.example.opengles3.utils;


import android.content.Context;
import android.opengl.GLES30;
import android.opengl.Matrix;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class GLUtils {
    private Context mContext;
    private int mRotateAgree = 0;

    public GLUtils(Context context) {
        mContext = context;
    }

    //获取圆周上散点坐标
    public float[] getCircle(float centerX, float centerY, float radius, int num) {
        float unit = (float)(2 * Math.PI / num);
        float[] coords = new float[num * 3];
        for (int i = 0; i < num; i++) {
            coords[i * 3] = (float)(centerX + radius * Math.cos(unit * i));
            coords[i * 3 + 1] = (float)(centerY + radius * Math.sin(unit * i));
            coords[i * 3 + 2] = 0;
        }
        return coords;
    }

    //调整坐标
    public float[] adjustCoord(float[] coords, int width, int height) {
        float ratio = width > height ? (1.0f * height / width) : (1.0f * width / height);
        int start = width > height ? 0 : 1;
        float[] tempCoord =  Arrays.copyOf(coords, coords.length);
        int num = tempCoord.length / 3;
        for (int i = 0; i < num; i++) {
            tempCoord[start + i * 3] *= ratio;
        }
        return tempCoord;
    }

    public FloatBuffer getFloatBuffer(float[] floatArr) {
        FloatBuffer fb = ByteBuffer.allocateDirect(floatArr.length * Float.BYTES)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        fb.put(floatArr);
        fb.position(0);
        return fb;
    }

    //通过代码片段编译着色器
    public int compileShader(int type, String shaderCode){
        int shader = GLES30.glCreateShader(type);
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);
        return shader;
    }

    //通过外部资源编译着色器
    public int compileShader(int type, int shaderId){
        String shaderCode = readShaderFromResource(shaderId);
        return compileShader(type, shaderCode);
    }

    //链接到着色器
    public int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programId = GLES30.glCreateProgram();
        //将顶点着色器加入到程序
        GLES30.glAttachShader(programId, vertexShaderId);
        //将片元着色器加入到程序
        GLES30.glAttachShader(programId, fragmentShaderId);
        //链接着色器程序
        GLES30.glLinkProgram(programId);
        return programId;
    }

    //从shader文件读出字符串
    private String readShaderFromResource(int shaderId) {
        InputStream is = mContext.getResources().openRawResource(shaderId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //计算MVP变换矩阵
    public void transform(int programId, float ratio) {
        //初始化modelMatrix, viewMatrix, projectionMatrix
        float[] modelMatrix = getIdentityMatrix(16, 0); //模型变换矩阵
        float[] viewMatrix = getIdentityMatrix(16, 0); //观测变换矩阵
        float[] projectionMatrix = getIdentityMatrix(16, 0); //投影变换矩阵
        //获取modelMatrix, viewMatrix, projectionMatrix
        mRotateAgree = (mRotateAgree + 2) % 360;
        Matrix.rotateM(modelMatrix, 0, mRotateAgree, 1, 1, 1); //获取模型旋转变换矩阵
        Matrix.setLookAtM(viewMatrix, 0, 0, 5, 10, 0, 0, 0, 0, 1, 0); //获取观测变换矩阵
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 20); //获取投影变换矩阵
        //计算MVP变换矩阵: mvpMatrix = projectionMatrix * viewMatrix * modelMatrix
        float[] mvpMatrix = new float[16];
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        //设置MVP变换矩阵
        int mvpMatrixHandle = GLES30.glGetUniformLocation(programId, "mvpMatrix");
        GLES30.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
    }

    private float[] getIdentityMatrix(int size, int offset) {
        float[] matrix = new float[size];
        Matrix.setIdentityM(matrix, offset);
        return matrix;
    }

    public ByteBuffer getByteBuffer(byte[] byteArr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(byteArr.length * Byte.BYTES)
                .order(ByteOrder.nativeOrder());
        bb.put(byteArr);
        bb.position(0);
        return bb;
    }
}
