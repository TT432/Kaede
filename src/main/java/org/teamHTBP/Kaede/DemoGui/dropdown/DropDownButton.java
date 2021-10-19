package org.teamHTBP.Kaede.DemoGui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.teamHTBP.Kaede.DemoGui.dropdown.atlas.ModAtlasSprite;
import org.teamHTBP.Kaede.Kaede;

import javax.annotation.Nullable;

/**
 * @author DustW
 */
public class DropDownButton extends Button {
    private final DropDownChildMenu bindMenu;
    private ItemStack container;

    public DropDownButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction,
                          ITooltip tooltip, @Nullable DropDownChildMenu bindMenu) {
        super(x, y, width, height, title, pressedAction, tooltip);

        this.bindMenu = bindMenu;
    }

    final TextureAtlasSprite bgSprite = ModAtlasSprite.DROP_DOWN.getSprite(
            new ResourceLocation(Kaede.MOD_ID, "gui/dropdown/testslot"));

    boolean needRenderHovered = true;

    public void setNeedRenderHovered(boolean needRenderHovered) {
        this.needRenderHovered = needRenderHovered;
    }

    public boolean isNeedRenderHovered() {
        return needRenderHovered;
    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().getTextureManager().bindTexture(bgSprite.getAtlasTexture().getTextureLocation());
        AbstractGui.blit(matrixStack, this.x, this.y, getBlitOffset(), width, height, bgSprite);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        itemRenderer.renderItemAndEffectIntoGUI(getContainer(), x + 1, y + 1);
        itemRenderer.renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, getContainer(),
                x + 1, y + 1, null);
    }

    public void onHovered(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (isNeedRenderHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
    }

    private IVertexBuilder getBuilder(ItemStack itemStack, IRenderTypeBuffer bufferIn, RenderType renderType) {
        return ItemRenderer.getEntityGlintVertexBuilder(bufferIn, renderType, true, itemStack.hasEffect());
    }

    public DropDownChildMenu getBindMenu() {
        return bindMenu;
    }

    public ItemStack getContainer() {
        return container;
    }

    /** 请注意，这里是客户端only的 */
    public void setContainer(ItemStack container) {
        this.container = container;
    }
}
