#version 300 es
layout (location = 0) in vec4 aPosition;
layout (location = 1) in vec2 aTextureCoord;
uniform mat4 mvpMatrix;
out vec2 vTexCoord;
void main() {
     gl_Position  = mvpMatrix * aPosition;
     vTexCoord = aTextureCoord;
}

//attribute vec4 aPosition;
//attribute vec2 aTextureCoord;
//uniform mat4 mvpMatrix;
//varying vec2 vTexCoord;
//void main() {
//     gl_Position  = mvpMatrix * aPosition;
//     vTexCoord = aTextureCoord;
//}
