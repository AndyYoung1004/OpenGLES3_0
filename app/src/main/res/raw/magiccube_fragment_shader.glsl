#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;
out vec4 fragColor;
void main() {
     fragColor = texture(uTextureUnit,vTexCoord);
}
