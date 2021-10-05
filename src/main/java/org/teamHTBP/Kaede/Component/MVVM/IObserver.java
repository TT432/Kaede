package org.teamHTBP.Kaede.Component.MVVM;

import org.teamHTBP.Kaede.Component.LifeCycleComponent.ILifeCycleComponent;

public interface IObserver<T> {
    public void onChanged(T newData);
}
