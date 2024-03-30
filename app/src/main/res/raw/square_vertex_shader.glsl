#version 300 es
layout (location = 0) in vec4 vPosition;
void main() {
     gl_Position  = vPosition;
     gl_PointSize = 20.0; //顶点大小（GL_POINTS模式下才会生效）
}
