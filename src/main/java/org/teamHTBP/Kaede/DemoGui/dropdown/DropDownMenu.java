package org.teamHTBP.Kaede.DemoGui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.glfw.GLFW;
import org.teamHTBP.Kaede.Component.LifeCycleComponent.AbstractLifeCycleScreen;
import org.teamHTBP.Kaede.DemoGui.dropdown.atlas.ModAtlasSprite;
import org.teamHTBP.Kaede.DemoGui.dropdown.capability.DropDownPlayerCap;
import org.teamHTBP.Kaede.DemoGui.progressbar.CircleProgressBar;
import org.teamHTBP.Kaede.Kaede;

import java.util.Collections;

/**
 * @author DustW
 */
public class DropDownMenu extends AbstractLifeCycleScreen {
    DropDownChildMenu childMenu;

    public DropDownMenu() {
        super("drop-down_menu");

        childMenu = new DropDownChildMenu(this);
    }

    private boolean isOpen = false;
    private int opening;
    private DropDownButton activeButton;

    public int getOpening() {
        return opening;
    }

    public void setActiveContext(ItemStack itemStack) {
        activeButton.setContainer(itemStack);
    }

    public ItemStack getActiveContext() {
        return activeButton.getContainer();
    }

    @Override
    public void init() {
        super.init();

        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        DropDownPlayerCap cap = DropDownPlayerCap.get(Minecraft.getInstance().player);

        DropDownButton slot1 = new DropDownButton(width / 2 - 50, height / 2, 18, 18,
                new StringTextComponent(" "),
                (button) -> {
                    opening = 0;
                    activeButton = (DropDownButton) button;
                    openChildMenu();
                },
                (Button button, MatrixStack matrixStack, int mouseX, int mouseY) -> {
                    GuiUtils.drawHoveringText(matrixStack, Collections.singletonList(new StringTextComponent("点击展开")),
                            mouseX, mouseY, width, height, -1, Minecraft.getInstance().fontRenderer);
                }, childMenu);

        slot1.setContainer(cap.getItemFromSlot(0));

        addButton(slot1);

        bar = new CircleProgressBar(150, 150, 18, 18, 18, 18,
                1, new ResourceLocation(Kaede.MOD_ID, "textures/gui/dropdown/testslot.png"));
    }

    CircleProgressBar bar;

    final TextureAtlasSprite bgSprite = ModAtlasSprite.DROP_DOWN.getSprite(
            new ResourceLocation(Kaede.MOD_ID, "gui/dropdown/testbg")
    );

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);

        minecraft.getTextureManager().bindTexture(bgSprite.getAtlasTexture().getTextureLocation());
        AbstractGui.blit(matrixStack, getBgRenderWidth(), getBgRenderHeight(), 0, getGuiWidth(), getGuiHeight(), bgSprite);
    }

    public int getGuiHeight() {
        return 166;
    }

    public int getGuiWidth() {
        return 166;
    }

    int getBgRenderHeight() {
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        return height / 2 - getGuiHeight() / 2;
    }

    int getBgRenderWidth() {
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        return width / 2 - getGuiWidth() / 2;
    }

    public void openChildMenu() {
        isOpen = true;
        getEventListeners().forEach(listener -> {
            if (listener instanceof DropDownButton) {
                ((DropDownButton) listener).setNeedRenderHovered(false);
            }
        });
        getEventListeners().clear();
        childMenu.init(Minecraft.getInstance(), width, height);
        childMenu.getEventListeners().forEach(this::addListener);
    }

    public void closeChildMenu() {
        isOpen = false;
        getEventListeners().clear();
        buttons.clear();
        this.init();
        childMenu.onClose();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if (isOpen) {
            matrixStack.push();
            matrixStack.translate(0, 0, 200);
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.zLevel = 300;
            childMenu.render(matrixStack, mouseX, mouseY, partialTicks);
            renderTooltips(matrixStack, mouseX, mouseY);
            itemRenderer.zLevel = 0;
            matrixStack.pop();
        }
        else {
            // 虽然这里看起来是一样的，但是 MatrixStack 不一样了
            renderTooltips(matrixStack, mouseX, mouseY);
        }

        bar.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void renderTooltips(MatrixStack matrixStack, int mouseX, int mouseY) {
        getEventListeners().forEach(listener -> {
            if (listener instanceof DropDownButton) {
                if (((DropDownButton) listener).isHovered()) {
                    ((DropDownButton) listener).onHovered(matrixStack, mouseX, mouseY);
                }
            }
        });
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && isOpen) {
            closeChildMenu();
            return true;
        }
        else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }
}
