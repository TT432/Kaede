package org.teamHTBP.Kaede.Component.MVVM.LifeCycle;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.MVVM.IObserver;

@OnlyIn(Dist.CLIENT)
public interface ILifeCycleObserver{
    public void onStageChanged(ILifeCycleComponent source, LifeCycle.LifeEvent event);
}
