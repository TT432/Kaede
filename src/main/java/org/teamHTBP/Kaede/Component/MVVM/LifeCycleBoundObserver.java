package org.teamHTBP.Kaede.Component.MVVM;

import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleComponent;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleObserver;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.LifeCycle;

public class LifeCycleBoundObserver implements ILifeCycleObserver {
    private final ILifeCycleComponent owner;
    private IObserver<?> observer;

    public LifeCycleBoundObserver(ILifeCycleComponent owner, IObserver<?> observer) {
        this.owner = owner;
        this.observer = observer;
    }

    @Override
    public void onStageChanged(ILifeCycleComponent source, LifeCycle.LifeEvent event) {
        if(owner.getLifeCycle().getCurrentStage() == LifeCycle.Stage.CLOSE){
            owner.getLifeCycle().removeObserver(this);
            return;
        }
        activeStateChanged(true);
    }

    public void activeStateChanged(boolean newState){
        //TODO
    }
}
