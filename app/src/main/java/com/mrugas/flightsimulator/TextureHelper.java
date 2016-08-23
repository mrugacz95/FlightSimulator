package com.mrugas.flightsimulator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.List;

public class TextureHelper
{
	public static int loadTexture(final Context context, final int resourceId)
	{
		final int[] textureHandle = new int[1];
		
		GLES30.glGenTextures(1, textureHandle, 0);
		
		if (textureHandle[0] != 0)
		{
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;	// No pre-scaling

			// Read in the resource
			final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
						
			// Bind to the texture in OpenGL
			GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureHandle[0]);
			
			// Set filtering
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
			GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);
			
			// Load the bitmap into the bound texture.
			GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
			
			// Recycle the bitmap, since its data has been loaded into OpenGL.
			bitmap.recycle();						
		}
		
		if (textureHandle[0] == 0)
		{
			throw new RuntimeException("Error loading texture.");
		}
		
		return textureHandle[0];
	}
	public static Bitmap drawableToBitmap(int resourceId, Context context){

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;

		return BitmapFactory.decodeResource(context.getResources(), resourceId, options);
	}
    public static int loadCubeMap(Context context, List<Integer> texturesFaces){
        final int[] textureHandle = new int[1];
        GLES30.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0);

            GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP, textureHandle[0]);
            for(Integer i = 0; i < texturesFaces.size(); i++)
            {
                Bitmap bitmap = TextureHelper.drawableToBitmap(texturesFaces.get(i),context);

                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bitmap.getWidth() * bitmap.getHeight() * 4);
                byteBuffer.order(ByteOrder.BIG_ENDIAN);
                IntBuffer ib = byteBuffer.asIntBuffer();
                int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
                bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                for (int j=0;j< pixels.length;j++) {
                    //pixels[j] = ((pixels[j] & 0x00ff0000) >> 8) | ((pixels[j] & 0x0000ff00) << 8) | ((pixels[j] & 0x000000ff) << 24) | (pixels[j] >> 24);
                    pixels[j] = pixels[j] << 8 | pixels[j] >>> 24;
                    ib.put(pixels[j]);
                }
                //ib.put(pixels);
                ib.position(0);
                GLES30.glTexImage2D(
                        GLES30.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i,
                        0, GLES30.GL_RGBA, bitmap.getWidth(), bitmap.getHeight(), 0, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, ib
                );
                ib.clear();
                bitmap.recycle();
            }
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_R, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP, 0);
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }
        return  textureHandle[0];
    }

    //http://www.opengl-tutorial.org/intermediate-tutorials/tutorial-14-render-to-texture/
    public static int createFrameBuffer(int width, int height){
        final int[] framebuffer = new int[1];
        GLES30.glGenFramebuffers(1, framebuffer, 0);
        if(framebuffer[0] == 0) {
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, framebuffer[0]);

            final int[] renderedTexture = new int[1];
            GLES30.glGenTextures(1, renderedTexture, 0);
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, renderedTexture[0]);
            GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RGB, width, height, 0, GLES30.GL_RGB, GLES30.GL_UNSIGNED_BYTE, ByteBuffer.allocateDirect(0));
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);

            final int[]  depthrenderbuffer = new  int[1];
            GLES30.glGenRenderbuffers(1, depthrenderbuffer, 0);
            GLES30.glBindRenderbuffer(GLES30.GL_RENDERBUFFER, depthrenderbuffer[0]);
            GLES30.glRenderbufferStorage(GLES30.GL_RENDERBUFFER, GLES30.GL_DEPTH_COMPONENT, width, height);
            GLES30.glFramebufferRenderbuffer(GLES30.GL_FRAMEBUFFER, GLES30.GL_DEPTH_ATTACHMENT, GLES30.GL_RENDERBUFFER, depthrenderbuffer[0]);


            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D,renderedTexture[0], 0);
            int[] drawBuffers = new int[1];
            drawBuffers[0] = GLES30.GL_COLOR_ATTACHMENT0;
            GLES30.glDrawBuffers(1, drawBuffers, 0); // "1" is the size of DrawBuffers


        }
        if(GLES30.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER) != GLES30.GL_FRAMEBUFFER_COMPLETE)
            return -1;
        return framebuffer[0];
    }
}
