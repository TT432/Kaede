package org.teamHTBP.Kaede.Component.MVVM.ViewModel;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.MVVM.IObserver;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleComponent;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleObserver;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.LifeCycle;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 封装一个实例成为一个可被观察的实例<br/>
 * T表示储存实例的类型，比如<code>LiveData(Object) </code>就是指封装了一个Object实例的LiveData</pre><br/>
 * 使用{@link LiveData#observe(ILifeCycleComponent, IObserver)}可以对数据进行观察<br/>
 * 使用{@link LiveData#getData()}可以获取被封装的数据<br/>
 * 使用{@link LiveData#setData(Object)} 向LiveData发送新的数据<br/>
 * <b>注意：LiveData最好存在自己编写的ViewModel(见：{@link ViewModel}）中，ViewModel的生命长度比Screen要长，并且可以保持</b><br/>
 * <i>如果你对android jetpack熟悉的话，还有一个postData，但是Kaede目前没有多线程发送的机制，自己能力不够实在抱歉...</i>
 * */
@OnlyIn(Dist.CLIENT)
public class LiveData<T> {
    /**数据*/
    T data;

    /**正在监听该LiveData的观察者*/
    Map<IObserver<?>, ILifeCycleObserver> registeredObservers = new LinkedHashMap<>();

    public LiveData(T initData){
        data = initData;
    }

    /**获取LiveData中值*/
    public T getData(){
        return this.data;
    }

    /**在主线程上设置数据*/
    public void setData(T newData){
        this.data = newData;
    }

    /***/
    public void observe(ILifeCycleComponent container,IObserver<T> observer){
        if(container.getLifeCycle().getCurrentStage() == LifeCycle.Stage.CLOSE || observer == null) return;
        assert registeredObservers != null;
    }
}
