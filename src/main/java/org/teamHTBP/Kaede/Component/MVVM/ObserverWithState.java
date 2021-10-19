package org.teamHTBP.Kaede.Component.MVVM;

import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleComponent;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleObserver;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.LifeCycle;

public class ObserverWithState {
    LifeCycle.Stage stage;
    ILifeCycleObserver lifeCycleObserver;

    public ObserverWithState(ILifeCycleObserver observer, LifeCycle.Stage stage){
        this.lifeCycleObserver = observer;
        this.stage = stage;
    }

    protected void dispathEvent(ILifeCycleComponent owner, LifeCycle.Stage stage){

    }

}
