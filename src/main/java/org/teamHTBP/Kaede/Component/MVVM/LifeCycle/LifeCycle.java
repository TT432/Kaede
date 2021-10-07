package org.teamHTBP.Kaede.Component.MVVM.LifeCycle;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.MVVM.IObserver;

@OnlyIn(Dist.CLIENT)
public abstract class LifeCycle{
    protected Stage stage;


   public enum Stage{
       CLOSE,
       CREATE,
       INIT,
       RENDER,
       HIDE
   }

   public enum LifeEvent{
       ON_CREATE,
       ON_INIT,
       ON_RENDER,
       ON_HIDE
   }

   public LifeCycle getLifeCycle(){
       return this;
   }

   public abstract Stage getCurrentStage();

   public abstract void addObserver(ILifeCycleObserver observer);
}
