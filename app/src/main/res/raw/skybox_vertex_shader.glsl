attribute vec3 position;


varying vec3 v_TexCoords;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

void main()
{
    vec4 pos =   projection * view * vec4(position.xyz,1f);
    pos = pos.xyww;
    gl_Position = pos;
    v_TexCoords = position.xyz;
}