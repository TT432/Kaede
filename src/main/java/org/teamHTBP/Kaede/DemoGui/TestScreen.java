package org.teamHTBP.Kaede.DemoGui;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.LifeCycleComponent.AbstractLifeCycleScreen;

@OnlyIn(Dist.CLIENT)
public class TestScreen extends AbstractLifeCycleScreen {
    public TestScreen() {
        super("testScreen");
    }

    @Override
    public void init() {
        super.init();
        addButton(new Button(10,10,200,20, new StringTextComponent("test"), (button)->{
            assert this.minecraft != null;
            this.minecraft.displayGuiScreen(new AnotherTestScreen());
        }));
    }

    @Override
    public void onClose() {
        super.onClose();
        System.out.println("close");
    }
}
