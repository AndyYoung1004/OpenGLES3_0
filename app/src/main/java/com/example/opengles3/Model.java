package com.example.opengles3;


public class Model {
    private static float r = 1.0f;

    private final int PLANE_NUM = 3 * 6; // 面数
    private final int SQUARE_NUM_PER_PLANE = 9; // 每面方块数
    private final int VERTEXT_NUM_PER_SQUARE = 4; // 每个方块顶点数
    private final int DIMENSION_PER_VERTEXT = 3; // 每个顶点坐标维度
    private final int DIMENSION_PER_TEXTURE = 2; // 每个纹理坐标维度
    private final float SQUARE_SIDE = 0.7f; // 每个方块的边长
    private final float HALF_PLANE_SIDE = 1.5f * SQUARE_SIDE; //每个面的边长的一半
    private final int[] RESOURCE_ID = new int[] { // 图片资源id
            R.raw.thelittleprince, R.raw.thelittleprince, R.raw.thelittleprince, R.raw.thelittleprince,
            R.raw.thelittleprince, R.raw.thelittleprince, R.raw.thelittleprince
    };

    // 顶点坐标数组
    private float[][][][] vertex = new float[PLANE_NUM][SQUARE_NUM_PER_PLANE][VERTEXT_NUM_PER_SQUARE][DIMENSION_PER_VERTEXT];
    // 纹理坐标数组
    private float[][][][] texture = new float[PLANE_NUM][SQUARE_NUM_PER_PLANE][VERTEXT_NUM_PER_SQUARE][DIMENSION_PER_TEXTURE];
    // 贴图资源数组
    private int[][] mipmap = new int[PLANE_NUM][SQUARE_NUM_PER_PLANE];

    public Model() {
        initModel();
    }

    // 初始化模型顶点坐标和纹理坐标
    private void initModel() {
        for (int i = 0; i < 3; i++) { // 遍历三视图
            initView(i);
        }
    }

    // 初始化三视图顶点坐标和纹理坐标
    private void initView(int direction) {
        int baseIndex = direction * 6;
        int axis = 2 - direction; // 固定的坐标轴
        for (int i = 0; i < 6; i++) {
            int planeIndex = baseIndex + i;
            float value = HALF_PLANE_SIDE - ((i + 1) / 2) * SQUARE_SIDE;
            initPlane(planeIndex, axis, value);
            initMipmap(direction, planeIndex, i);
        }
    }

    // 初始化贴图资源
    private void initMipmap(int direction, int planeIndex, int seq) {
        int near = direction * 2;
        int far = near + 1;
        int inside = 6;
        int index = (seq == 0 ? near : (seq == 5 ? far : inside));
        for (int i = 0; i < SQUARE_NUM_PER_PLANE; i++) {
            mipmap[planeIndex][i] = RESOURCE_ID[index];
        }
    }

    // 初始化一个面顶点坐标和纹理坐标
    private void initPlane(int planeIndex, int axis, float value) {
        for (int i = 0; i < 9; i++) {
            initSquare(planeIndex, i, axis, value);
        }
    }

    // 初始化一个方块顶点坐标和纹理坐标
    private void initSquare(int planeIndex, int squareIndex, int axis, float value) {
        float row = HALF_PLANE_SIDE - SQUARE_SIDE * (squareIndex / 3);
        float col = -HALF_PLANE_SIDE + SQUARE_SIDE * (squareIndex % 3);
        switch(axis) {
            case 0: // 右视图
                for (int i = 0; i < 4; i++) {
                    vertex[planeIndex][squareIndex][i][0] = value;
                    vertex[planeIndex][squareIndex][i][1] = row - SQUARE_SIDE * (i / 2);
                    vertex[planeIndex][squareIndex][i][2] = col + SQUARE_SIDE * (i % 2);
                }
                break;
            case 1: // 俯视图
                for (int i = 0; i < 4; i++) {
                    vertex[planeIndex][squareIndex][i][0] = col + SQUARE_SIDE * (i % 2);
                    vertex[planeIndex][squareIndex][i][1] = value; //axis
                    vertex[planeIndex][squareIndex][i][2] = row - SQUARE_SIDE * (i / 2);
                }
                break;
            case 2: // 正视图
                for (int i = 0; i < 4; i++) {
                    vertex[planeIndex][squareIndex][i][0] = col + SQUARE_SIDE * (i % 2);
                    vertex[planeIndex][squareIndex][i][1] = row - SQUARE_SIDE * (i / 2);
                    vertex[planeIndex][squareIndex][i][2] = value;
                }
                break;
        }
        for (int i = 0; i < 4; i++) {
            texture[planeIndex][squareIndex][i][0] = i % 2;
            texture[planeIndex][squareIndex][i][1] = i / 2;
        }
    }

    public static int[] resIds = new int[] {
            R.raw.thelittleprince, R.raw.thelittleprince, R.raw.thelittleprince,
            R.raw.thelittleprince, R.raw.thelittleprince, R.raw.thelittleprince
    };

    // 获取顶点坐标
    public float[] getVertex() {
        int length = PLANE_NUM * SQUARE_NUM_PER_PLANE * VERTEXT_NUM_PER_SQUARE * DIMENSION_PER_VERTEXT;
        float[] res = new float[length];
        int index = 0;
        for (int i = 0; i < PLANE_NUM; i++) {
            for (int j = 0; j < SQUARE_NUM_PER_PLANE; j++) {
                for (int k = 0; k < VERTEXT_NUM_PER_SQUARE; k++) {
                    int ver = k * DIMENSION_PER_VERTEXT;
                    for (int l = 0; l < DIMENSION_PER_VERTEXT; l++) {
                        res[index++] = vertex[i][j][k][l];
                    }
                }
            }
        }
        return res;
    }

    // 获取纹理坐标
    public float[] getTexture() {
        int length = PLANE_NUM * SQUARE_NUM_PER_PLANE * VERTEXT_NUM_PER_SQUARE * DIMENSION_PER_TEXTURE;
        float[] res = new float[length];
        int index = 0;
        for (int i = 0; i < PLANE_NUM; i++) {
            for (int j = 0; j < SQUARE_NUM_PER_PLANE; j++) {
                for (int k = 0; k < VERTEXT_NUM_PER_SQUARE; k++) {
                    for (int l = 0; l < DIMENSION_PER_TEXTURE; l++) {
                        res[index++] = texture[i][j][k][l];
                    }
                }
            }
        }
        return res;
    }

    public int[] getMipmap() {
        int length = PLANE_NUM * SQUARE_NUM_PER_PLANE;
        int[] res = new int[length];
        int index = 0;
        for (int i = 0; i < PLANE_NUM; i++) {
            for (int j = 0; j < SQUARE_NUM_PER_PLANE; j++) {
                res[index++] = mipmap[i][j];
            }
        }
        return res;
    }
}
