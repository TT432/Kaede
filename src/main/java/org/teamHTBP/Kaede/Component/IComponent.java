package org.teamHTBP.Kaede.Component;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IRenderable;

public interface IComponent extends IRenderable{
    /**组件渲染*/
    void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);
}
