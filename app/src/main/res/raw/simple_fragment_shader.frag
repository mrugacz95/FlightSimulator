#version 300 es
                                    // precision in the fragment shader.
in vec4 v_Color;        	// This is the color from the vertex shader interpolated across the

uniform sampler2D u_Texture;
in vec2 v_TexCoordinate;
out vec4 fragColor;
void main()                   	// The entry point for our fragment shader.
{
        fragColor = v_Color;      // Pass the color directly through the pipeline.

}