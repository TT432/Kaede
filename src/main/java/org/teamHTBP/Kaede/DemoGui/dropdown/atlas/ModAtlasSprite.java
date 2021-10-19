package org.teamHTBP.Kaede.DemoGui.dropdown.atlas;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.teamHTBP.Kaede.DemoGui.dropdown.atlas.DropDownAtlasSprite;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAtlasSprite {
    public static DropDownAtlasSprite DROP_DOWN;

    @SubscribeEvent
    public static void modelRegistry(ModelRegistryEvent event) {
        DROP_DOWN = new DropDownAtlasSprite();
    }
}
