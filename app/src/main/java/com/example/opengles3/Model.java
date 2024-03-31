package com.example.opengles3;


public class Model {
    private static float r = 1.0f;

    public static int[] resIds = new int[] {
            R.raw.thelittleprince, R.raw.thelittleprince, R.raw.thelittleprince,
            R.raw.thelittleprince, R.raw.thelittleprince, R.raw.thelittleprince
    };

    public static float[] vertex = new float[] {
            //前面
            r, r, r,
            -r, r, r,
            -r, -r, r,
            r, -r, r,
            //后面
            r, r, -r,
            -r, r, -r,
            -r, -r, -r,
            r, -r, -r,
            //上面
            r, r, r,
            r, r, -r,
            -r, r, -r,
            -r, r, r,
            //下面
            r, -r, r,
            r, -r, -r,
            -r, -r, -r,
            -r, -r, r,
            //右面
            r, r, r,
            r, r, -r,
            r, -r, -r,
            r, -r, r,
            //左面
            -r, r, r,
            -r, r, -r,
            -r, -r, -r,
            -r, -r, r
    };

    public static float[] texture = new float[] {
            //前面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //后面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //上面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //下面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //右面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //左面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f
    };
}
