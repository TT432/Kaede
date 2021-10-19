package org.teamHTBP.Kaede.DemoGui.progressbar;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class CircleProgressBar implements IRenderable {
    private int posX;
    private int posY;
    private int textureWidth;
    private int textureHeight;
    private int width;
    private int height;
    private int speed;
    private ResourceLocation texture;

    public CircleProgressBar(int posX, int posY, int width, int textureWidth, int textureHeight,
                             int height, int speed, ResourceLocation texture) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.speed = speed;
        this.texture = texture;
    }

    /** 0 - 100 (%) */
    private float percentage = 0;

    private void addPercentage(float percentage) {
        this.percentage = (this.percentage + percentage) % 100;
    }

    float lastPartialTicks = 0;

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().textureManager.bindTexture(texture);

        addPercentage((lastPartialTicks < partialTicks ? partialTicks - lastPartialTicks : partialTicks) / 10);
        lastPartialTicks = partialTicks;

        matrixStack.push();

        matrixStack.translate(posX, posY, 0);

        F2[] uv = getUV(percentage);
        F2[] points = getPoints(uv);

        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);

        for (int i = 0; i < uv.length; i++) {
            Vector4f pos = new Vector4f(points[i].f1, points[i].f2, 0, 1.0F);
            pos.transform(matrixStack.getLast().getMatrix());

            bufferbuilder.addVertex(pos.getX(), pos.getY(), 0, 1, 1, 1, 1,
                    uv[i].f1, uv[i].f2, OverlayTexture.NO_OVERLAY, LightTexture.packLight(15, 15),
                    0, 0, 0);
        }

        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);

        matrixStack.pop();
    }

    public F2[] getPoints(F2[] uv) {
        F2[] result = new F2[uv.length];

        for (int i = 0; i < uv.length; i++) {
            result[i] = new F2(uv[i].f1 * width, uv[i].f2 * height);
        }

        return result;
    }

    public static final F2 CENTER = new F2(0.5F, 0.5F);
    public static final F2 CB = new F2(0.5F, 0);
    public static final F2 LB = new F2(0, 0);
    public static final F2 LU = new F2(0, 1);
    public static final F2 RU = new F2(1, 1);
    public static final F2 RB = new F2(1, 0);
    public static final double RADIANS_90 = Math.toRadians(90);
    public static final double RADIANS_270 = Math.toRadians(270);

    public F2[] getUV(float percentage) {
        ArrayList<F2> result = new ArrayList<>();

        result.add(CENTER);
        result.add(CB);

        double degrees = percentage / 100 * 360;
        double radians = Math.toRadians(degrees);
        int side = (int) (degrees / 45);

        switch (side) {
            case 1:
            case 2: {
                result.add(LB);
                break;
            }
            case 3:
            case 4: {
                result.add(LB);
                result.add(LU);
                break;
            }
            case 5:
            case 6: {
                result.add(LB);
                result.add(LU);
                result.add(RU);
                break;
            }
            case 7: {
                result.add(LB);
                result.add(LU);
                result.add(RU);
                result.add(RB);
                break;
            }
            default: {break;}
        }

        if (degrees >= 0 && degrees <= 45) {
            result.add(new F2((float) (1 - Math.tan(radians)) / 2, 0));
        }
        else if (degrees >= 45 && degrees <= 135) {
            result.add(new F2(0, (float) ((1 + Math.tan(radians - RADIANS_90)) / 2)));
        }
        else if (degrees >= 135 && degrees <= 225) {
            result.add(new F2((float) ((1 - Math.tan(Math.PI - radians)) / 2), 1));
        }
        else if (degrees >= 225 && degrees <= 315) {
            result.add(new F2(1, (float) ((1 - Math.tan(radians - RADIANS_270)) / 2)));
        }
        else if (degrees >= 315 && degrees <= 360) {
            result.add(new F2((float) (1 - Math.tan(radians)) / 2, 0));
        }

        return result.toArray(new F2[0]);
    }

    static class F2 {
        final float f1;
        final float f2;

        public F2(float f1, float f2) {
            this.f1 = f1;
            this.f2 = f2;
        }
    }
}
