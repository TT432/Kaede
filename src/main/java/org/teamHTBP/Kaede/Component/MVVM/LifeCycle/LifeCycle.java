package org.teamHTBP.Kaede.Component.MVVM.LifeCycle;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.MVVM.IObserver;

/**
 * 组件的生命类，
 * 生命拥有状态{@link LifeCycle.Stage} 和 生命事件 {@link LifeCycle.LifeEvent}
 * */
@OnlyIn(Dist.CLIENT)
public abstract class LifeCycle{
    protected Stage stage;

    /**
     * 组件生命的所有状态
     * <pre>
     *     Close - 当组件被关闭销毁时
     *     Create - 当组件被实例化时
     *     Init - 当组件被初始化时
     *     Render - 当组件*每次*被渲染时
     *     Hide - 当组件被隐藏时
     * </pre>
     * */
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
        ON_HIDE,
        ON_CLOSE;

        public Stage getStateAfter(LifeEvent event){
            switch (event){
                case ON_CREATE:
                case ON_HIDE:
                    return Stage.CREATE;
                case ON_INIT:
                    return Stage.INIT;
                case ON_RENDER:
                    return Stage.CREATE;
                case ON_CLOSE:
                    return null;
            }
            return null;
        }
    }

    /**获得组件的生命*/
   public LifeCycle getLifeCycle(){
       return this;
   }

   /**获得组件的生命状态*/
   public abstract Stage getCurrentStage();

   public abstract void addObserver(ILifeCycleObserver observer);

   public abstract void removeObserver(ILifeCycleObserver observer);
}
