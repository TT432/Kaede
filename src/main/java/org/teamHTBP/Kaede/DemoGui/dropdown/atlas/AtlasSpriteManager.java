package org.teamHTBP.Kaede.DemoGui.dropdown.atlas;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author DustW
 */
public abstract class AtlasSpriteManager {
    private final ResourceLocation ATLAS;
    private final ArrayList<ResourceLocation> TEXTURES;

    public AtlasSpriteManager(ResourceLocation atlasName) {
        ATLAS = atlasName;
        TEXTURES = new ArrayList<>();
        register();
        makeAtlas();
    }

    private void makeAtlas() {
        Set<RenderMaterial> material = ModelBakery.LOCATIONS_BUILTIN_TEXTURES;
        TEXTURES.forEach(texture -> material.add(new RenderMaterial(ATLAS, texture)));
    }

    public final TextureAtlasSprite getSprite(ResourceLocation texture) {
        return Minecraft.getInstance()
                .getModelManager()
                .getAtlasTexture(ATLAS)
                .getSprite(texture);
    }

    /** 省略开头的textures和结尾的.png */
    protected final void add(ResourceLocation texture) {
        TEXTURES.add(texture);
    }

    /** 在这个方法内注册所有贴图 */
    protected abstract void register();
}
