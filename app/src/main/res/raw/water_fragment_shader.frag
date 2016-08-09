#version 300 es

in vec2 texCoord;

out vec4 color;

uniform sampler2D renderedTexture;

void main(){
    color = texture( renderedTexture, texCoord );
}