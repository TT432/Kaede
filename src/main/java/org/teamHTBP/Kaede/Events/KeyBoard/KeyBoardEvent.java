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

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyBoardEvent {
    public static final KeyBinding OPENGUI = new KeyBinding("key.open_gui",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "key.category.kaede");


    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.KeyInputEvent event) {
        if (OPENGUI.isPressed()) {
            if(Minecraft.getInstance().currentScreen == null){
                Minecraft.getInstance().displayGuiScreen(new TestScreen());
            }
        }
    }
}
