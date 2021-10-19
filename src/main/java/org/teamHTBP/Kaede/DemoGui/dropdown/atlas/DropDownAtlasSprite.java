package org.teamHTBP.Kaede.DemoGui.dropdown.atlas;

import net.minecraft.util.ResourceLocation;
import org.teamHTBP.Kaede.Kaede;

/**
 * @author DustW
 */
public class DropDownAtlasSprite extends AtlasSpriteManager {
    public DropDownAtlasSprite() {
        super(new ResourceLocation("atlas", "textures/atlas/atlas.png"));
    }

    @Override
    protected void register() {
        add(new ResourceLocation(Kaede.MOD_ID, "gui/dropdown/testbg"));
        add(new ResourceLocation(Kaede.MOD_ID, "gui/dropdown/testslot"));
    }
}
