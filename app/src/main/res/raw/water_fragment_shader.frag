#version 300 es

in vec2 texCoord;

layout(location = 1) out vec4 color;
uniform vec3 cameraDir;
uniform vec3 cameraPos;
uniform sampler2D texture;
uniform sampler2D waterBump;
uniform float time;
void main(){
    vec3 sunVector = vec3(-0.3,-0.2,-1);
    sunVector= normalize(sunVector);

    float time2 = time / 100.f;
    vec2 texCoord2 = mod(texCoord*50.f,512.f) ;
    vec2 texCoord3 = mod(texCoord*5.f,512.f) ;
    color = vec4(0,0.6,1.0,0.7)*0.6 + 0.2*texture( texture,  texCoord) + 0.3*texture( waterBump,  texCoord3);
}