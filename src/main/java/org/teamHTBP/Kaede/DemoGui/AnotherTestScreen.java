package org.teamHTBP.Kaede.DemoGui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.LifeCycleComponent.AbstractLifeCycleScreen;

@OnlyIn(Dist.CLIENT)
public class AnotherTestScreen extends AbstractLifeCycleScreen {
    public AnotherTestScreen() {
        super("hello");
    }
}
