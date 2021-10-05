package org.teamHTBP.Kaede.Component.MVVM;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

public class LiveData<T> {
    T data;

    List<IObserver<T>> observerList = new LinkedList<>();

    public LiveData(T initData){
        data = initData;
    }

    /**获取值*/
    public T getData(){
        return this.data;
    }

    public void setData(T newData){
        this.data = newData;
        observerList.forEach(tiObserver -> tiObserver.onChanged(newData));
    }
}
