package com.example.opengles3.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MyGLUtils {
    private Context mContext;
    private Bitmap mBitmap;

    public MyGLUtils(Context context) {
        mContext = context;
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

    //加载纹理贴图
    public int loadTexture(int resourceId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);
        final int[] textureIds = new int[1];
        // 生成纹理id
        GLES30.glGenTextures(1, textureIds, 0);
        // 绑定纹理到OpenGL
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        // 加载bitmap到纹理中
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, mBitmap, 0);
        // 生成MIP贴图
        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
        // 取消绑定纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
        return textureIds[0];
    }

    //调整图片宽高适配屏幕尺寸
    public void transform(int width, int height, float[] matrix) {
        int mapWidth = mBitmap.getWidth();
        int mapHeight = mBitmap.getHeight();
        float mapRatio = mapWidth / (float)mapHeight;
        float ratio = width / (float)height;
        float w = 1.0f;
        float h = 1.0f;
        if (mapRatio > ratio) { //宽度最大，高度适配
            h = mapRatio / ratio;
        } else { //高度最大，宽度适配
            w = ratio / mapRatio;
        }
        Matrix.orthoM(matrix, 0, -w, w, -h, h,-1, 1);
    }
}

