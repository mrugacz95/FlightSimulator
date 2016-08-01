precision mediump float;         // Set the default precision to medium. We don't need as high of a
                                    // precision in the fragment shader.
varying vec4 v_Color;        	// This is the color from the vertex shader interpolated across the

uniform sampler2D u_Texture;
varying vec2 v_TexCoordinate;

void main()                   	// The entry point for our fragment shader.
{
        gl_FragColor = texture2D(u_Texture, v_TexCoordinate);      // Pass the color directly through the pipeline.

}