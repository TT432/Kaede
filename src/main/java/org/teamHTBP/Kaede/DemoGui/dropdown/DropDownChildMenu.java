package org.teamHTBP.Kaede.DemoGui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.StringTextComponent;
import org.teamHTBP.Kaede.Component.LifeCycleComponent.AbstractLifeCycleScreen;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.KaedeNetworkManager;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.client.DropDownMenuOperateSyncClient;

/**
 * @author DustW
 */
public class DropDownChildMenu extends AbstractLifeCycleScreen {
    private final DropDownMenu fatherMenu;
    private final DropDownButton[] playerInventoryButtons;

    public DropDownChildMenu(DropDownMenu fatherMenu) {
        super("drop-down_child_menu");

        this.fatherMenu = fatherMenu;
        playerInventoryButtons = getInventoryButtons(2, 50, 50, 9, 18, 18);
    }

    int clicked;

    public int getClicked() {
        return clicked;
    }

    public void sync() {
        KaedeNetworkManager.clientSendToServer(new DropDownMenuOperateSyncClient(fatherMenu.getOpening(), getClicked()));
    }

    /** 他应该只在客户端被调用 */
    public void switchItemClient() {
        ItemStack clickedItem = playerInventoryButtons[clicked].getContainer();
        playerInventoryButtons[clicked].setContainer(fatherMenu.getActiveContext());
        fatherMenu.setActiveContext(clickedItem);
    }

    public DropDownButton[] getInventoryButtons(int offset, int posX, int posY, int slotOfLine, int slotW, int slotH) {
        DropDownButton[] result = new DropDownButton[36];
        NonNullList<ItemStack> playerInv = Minecraft.getInstance().player.inventory.mainInventory;
        for (int i = 0; i < result.length; i++) {
            ItemStack itemStack = playerInv.get(i);

            int finalI = i;
            result[i] = new DropDownButton(
                    posX + (i * (slotW + offset)) % (slotOfLine * (slotW + offset)), posY + (i / slotOfLine) * (slotH + offset),
                    slotW, slotH, new StringTextComponent(" "),
                    (button) -> {
                        clicked = finalI;
                        sync();
                        switchItemClient();
                    },
                    (Button button, MatrixStack matrixStack, int mouseX, int mouseY) -> {
                        renderTooltip(matrixStack, itemStack, mouseX, mouseY);
                    },
                    null
            );

            result[i].setContainer(itemStack);
        }

        return result;
    }

    @Override
    public void init() {
        super.init();

        for (DropDownButton playerInventoryButton : playerInventoryButtons) {
            addButton(playerInventoryButton);
        }
    }
}
