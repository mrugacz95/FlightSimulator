#version 300 es
precision mediump float;         // Set the default precision to medium. We don't need as high of a
                                    // precision in the fragment shader.
in vec4 texCoord;        	// This is the color from the vertex shader interpolated across the

uniform sampler2D u_Texture;
in vec2 v_TexCoordinate;
out vec4 fragColor;
void main()                   	// The entry point for our fragment shader.
{
        fragColor = texture(u_Texture, v_TexCoordinate);      // Pass the color directly through the pipeline.

}