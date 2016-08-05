varying vec3 v_TexCoords;

uniform samplerCube skybox;

void main()
{
    gl_FragColor = textureCube(skybox, v_TexCoords);
}