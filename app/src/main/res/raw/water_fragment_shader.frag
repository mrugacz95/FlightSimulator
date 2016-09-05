#version 300 es

in vec2 texCoord;

 out vec4 color;
uniform vec3 cameraDir;
uniform vec3 cameraPos;
uniform sampler2D texture;
uniform sampler2D waterBump;
uniform float time;
void main(){
    vec3 sunVector = vec3(-0.3,-0.2,-1);
    sunVector= normalize(sunVector);

    float time2 = time / 3141.0f;
    vec2 texCoord2 = mod(texCoord*50.f,1.f) ;
    vec2 texCoord3 = mod(texCoord*10.f+sin(time2)*0.1f,1.f) ;
    //if(texCoord.x > 0.3f)
    color = vec4(0,0.6,1.0,0.7)*0.6 + 0.2*texture( texture,  texCoord3);
   // else
       // color = texture( waterBump,  texCoord);
        // color = vec4(0,0.6,1.0,0.7)*0.6 + 0.2*texture( texture,  texCoord) + 0.3*texture( waterBump,  texCoord3);
}