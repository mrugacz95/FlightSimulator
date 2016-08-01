package com.mrugas.flyingsimulator.managers;

import android.content.Context;

import com.mrugas.flyingsimulator.R;
import com.mrugas.flyingsimulator.TextureHelper;

/**
 * Created by mruga on 01.08.2016.
 */
public class Texture {

    private int textureDataHandle;

    public Texture(Context context, int resId){
        textureDataHandle = TextureHelper.loadTexture(context,resId);
    }

    public int getTextureDataHandle() {
        return textureDataHandle;
    }
}
