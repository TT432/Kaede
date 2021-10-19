package org.teamHTBP.Kaede.DemoGui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;

/**
 * @author DustW
 */
public class Util {
    private static void renderTexture(TextureAtlasSprite spriteIn, MatrixStack matrixStackIn, boolean renderBack) {
        Minecraft.getInstance().getTextureManager().bindTexture(spriteIn.getAtlasTexture().getTextureLocation());
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        float minU = spriteIn.getMinU();
        float maxU = spriteIn.getMaxU();
        float minV = spriteIn.getMinV();
        float maxV = spriteIn.getMaxV();
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);

        bufferbuilder.pos(matrix4f, 0F, 1F, 1F / 256F).color(1, 1, 1, 1.0F).tex(maxU, maxV).endVertex();
        bufferbuilder.pos(matrix4f, 1F, 1F, 1F / 256F).color(1, 1, 1, 1.0F).tex(minU, maxV).endVertex();
        bufferbuilder.pos(matrix4f, 1F, 0F, 1F / 256F).color(1, 1, 1, 1.0F).tex(minU, minV).endVertex();
        bufferbuilder.pos(matrix4f, 0F, 0F, 1F / 256F).color(1, 1, 1, 1.0F).tex(maxU, minV).endVertex();

        if (renderBack) {
            bufferbuilder.pos(matrix4f, 0F, 0F, -1F / 256F).color(1, 1, 1, 1.0F).tex(maxU, minV).endVertex();
            bufferbuilder.pos(matrix4f, 1F, 0F, -1F / 256F).color(1, 1, 1, 1.0F).tex(minU, minV).endVertex();
            bufferbuilder.pos(matrix4f, 1F, 1F, -1F / 256F).color(1, 1, 1, 1.0F).tex(minU, maxV).endVertex();
            bufferbuilder.pos(matrix4f, 0F, 1F, -1F / 256F).color(1, 1, 1, 1.0F).tex(maxU, maxV).endVertex();
        }

        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }
}
