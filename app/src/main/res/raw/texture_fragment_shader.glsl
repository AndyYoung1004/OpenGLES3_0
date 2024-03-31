#version 300 es
precision mediump float;
uniform sampler2D uTextureUnit;
in vec2 vTexCoord;
out vec4 fragColor;
void main() {
     fragColor = texture(uTextureUnit, vTexCoord);
}

//precision mediump float;
//uniform sampler2D uTextureUnit;
//varying vec2 vTexCoord;
//void main() {
//     gl_FragColor = texture2D(uTextureUnit, vTexCoord);
//}
