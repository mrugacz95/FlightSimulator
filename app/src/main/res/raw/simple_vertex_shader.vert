#version 300 es
uniform mat4 u_MVPMatrix;       // A constant representing the combined model/view/projection matrix.
uniform mat4 P;
uniform mat4 V;
uniform mat4 M;

in vec4 a_Position;      // Per-vertex position information we will pass in.
in vec4 a_Color;      // Per-vertex color information we will pass in.
in vec2 a_TexCoordinate; // Per-vertex texture coordinate information we will pass in.

out vec2 v_TexCoordinate;
out vec4 v_Color;              // This will be passed into the fragment shader.


uniform vec4 lightPos0; //Wspolrzedne zrodla swiatla w przestrzeni swiata

//Atrybuty
in vec4 normal; //wektor normalny w wierzcholku

//Zmienne interpolowane
out vec4 vN; //interpolowany wektor normalny w przestrzeni oka
out vec4 vV; //interpolowany wektor do obserwatora w przestrzeni oka
out vec4 vL; //interpolowany wektor do zrodla swiatla w przestrzeni oka

void main()                    // The entry point for our vertex shader.
{
    v_TexCoordinate = a_TexCoordinate;
    v_Color = a_Position;            // Pass the color through to the fragment shader.

	vN=normalize(V*M*normal); //Oblicz i interpoluj wektor normalny w przestrzeni oka
	vL=normalize(V*lightPos0-V*M*a_Position); //Oblicz i interpoluj wektor do zrodla swiatla w przestrzeni oka
	vV=normalize(vec4(0,0,0,1)-V*M*a_Position); //Oblicz i interpoluj wektor do obserwatora w przestrzeni oka
										// It will be interpolated across the triangle.
    //gl_Position = u_MVPMatrix  * a_Position;
    gl_Position=P*V*M*a_Position;// gl_Position is a special variable used to store the final position.
                                                 // Multiply the vertex by the matrix to get the final point in
}                                // normalized screen coordinates.