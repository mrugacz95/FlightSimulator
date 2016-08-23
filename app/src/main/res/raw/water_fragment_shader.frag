#version 300 es

in vec2 texCoord;

layout(location = 1) out vec4 color;

uniform sampler2D renderedTexture;
uniform float time;
void main(){
    int lod;
    textureSize(renderedTexture, lod);
    float time2 = time / 100.f;
    vec2 texCoord2 = mod(texCoord*50.f,256.f) ;
    color = texture( renderedTexture,  texCoord2) ;
}