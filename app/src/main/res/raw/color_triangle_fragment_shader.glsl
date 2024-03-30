#version 300 es
precision mediump float; //声明float型变量的精度为mediump
in vec4 vColor;
out vec4 fragColor;
void main() {
     fragColor = vColor;
}
