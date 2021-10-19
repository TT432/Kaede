package org.teamHTBP.Kaede.Events.KeyBoard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import org.teamHTBP.Kaede.DemoGui.TestScreen;
import org.teamHTBP.Kaede.DemoGui.dropdown.DropDownMenu;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyBoardEvent {
    public static final String CATEGORY = "key.category.kaede";

    public static final KeyBinding OPENGUI = new KeyBinding("key.open_gui",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            CATEGORY);

    public static final KeyBinding OPENGUI2 = new KeyBinding("key.open_gui2",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            CATEGORY);


    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.KeyInputEvent event) {
        if (OPENGUI.isPressed()) {
            if(Minecraft.getInstance().currentScreen == null){
                Minecraft.getInstance().displayGuiScreen(new TestScreen());
            }
        }

        if (OPENGUI2.isPressed()) {
            if (Minecraft.getInstance().currentScreen == null) {
                Minecraft.getInstance().displayGuiScreen(new DropDownMenu());
            }
        }
    }
}
