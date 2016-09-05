#version 300 es
uniform mat4 u_MVPMatrix;
uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;

in vec4 a_Position;      // Per-vertex position information we will pass in.
in vec4 a_Color;      // Per-vertex color information we will pass in.
in vec2 a_TexCoordinate; // Per-vertex texture coordinate information we will pass in.

out vec2 texCoord;
out vec4 v_Color;              // This will be passed into the fragment shader.

void main()                    // The entry point for our vertex shader.
{
    texCoord = a_TexCoordinate;
    v_Color = a_Position;            // Pass the color through to the fragment shader.
													// It will be interpolated across the triangle.
    gl_Position = u_MVPMatrix  * a_Position;      // gl_Position is a special variable used to store the final position.
                                                 // Multiply the vertex by the matrix to get the final point in
}                                // normalized screen coordinates.