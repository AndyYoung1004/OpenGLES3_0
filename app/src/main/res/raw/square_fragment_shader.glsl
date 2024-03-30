#version 300 es
precision mediump float; //声明float型变量的精度为mediump
out vec4 fragColor;
void main() {
     fragColor = vec4(0.2, 1.0, 1.0, 1.0); //顶点、边线、内部填充颜色
}
