package org.teamHTBP.Kaede.Component.MVVM;

public interface IObserver<T> {
    public void onChanged(T newData);
}
