package org.teamHTBP.Kaede.Component.MVVM.LifeCycle;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.MVVM.IObserver;
import org.teamHTBP.Kaede.Component.MVVM.ObserverWithState;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 来自于Android Jetpack中的 LifecycleRegistry
 * 可以处理多个Observer的监听
 * */
@OnlyIn(Dist.CLIENT)
public class LifeCycleManager extends LifeCycle{
    private final ILifeCycleComponent owner;

    private LinkedHashMap<ILifeCycleObserver, ObserverWithState> observers;

    public LifeCycleManager(ILifeCycleComponent owner) {
        this.owner = owner;
        this.observers = new LinkedHashMap<>();
    }

    @Override
    public Stage getCurrentStage() {
        return this.stage;
    }

    @Override
    public void addObserver(ILifeCycleObserver observer) {

    }

    public void markStage(Stage stage){
        this.stage = stage;
    }


    private void notifyObservers(){

    }
}
