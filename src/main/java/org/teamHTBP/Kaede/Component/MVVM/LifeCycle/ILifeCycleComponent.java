package org.teamHTBP.Kaede.Component.MVVM.LifeCycle;

import org.teamHTBP.Kaede.Component.IComponent;

/**
 * 有生命周期的组件接口
 * 所有的生命周期都可以通过{@link EnumLifeCycle}看到<br/>
 * 生命周期具体回调方法为：
 * beforeInit {@link ILifeCycleComponent#beforeInit() } <br/>
 * -> init  {@link ILifeCycleComponent#init() } <br/>
 * -> render  {@link ILifeCycleComponent#onRender()}<br/>
 * -> *hide {@link ILifeCycleComponent#onHide()}<br/>
 * -> onClose  {@link ILifeCycleComponent#onClose()}<br/>
 * */
public interface ILifeCycleComponent extends IComponent {
    /**在组件在初始化之前会调用这个方法*/
    public default void beforeInit(){}

    /**当组件被init的时候会调用这个方法*/
    public default void init(){}

    /**当组件被render时会调用这个方法*/
    public default void onRender(){}

    /**当组件被隐藏起来的时候会调用这个方法*/
    public default void onHide(){}

    /**当窗口关闭时或组件被销毁时会调用这个方法*/
    public default void onClose(){}

    /**获取当前的状态*/
    public EnumLifeCycle getLifeCycle();
}
