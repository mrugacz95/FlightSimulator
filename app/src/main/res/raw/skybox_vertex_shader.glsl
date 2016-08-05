attribute vec3 position;
attribute vec2 a_TexCoords;


varying vec3 v_TexCoords;
uniform mat4 projection;
uniform mat4 view;

void main()
{
    gl_Position =   projection * view * vec4(position,1f);
    v_TexCoords = vec3(position.xy,1f);
}