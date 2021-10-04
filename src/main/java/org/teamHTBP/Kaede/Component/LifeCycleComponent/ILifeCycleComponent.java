package org.teamHTBP.Kaede.Component.LifeCycleComponent;

import org.teamHTBP.Kaede.Component.IComponent;

/**有生命周期的组件接口*/
public interface ILifeCycleComponent extends IComponent {
    /**在组件在初始化之前会调用这个方法*/
    public default void beforeCreate(){}

    /**当组件被init的时候会调用这个方法*/
    public void onCreate();

    /**当组件被render时会调用这个方法,注意是每次render完之后会调用这个方法*/
    public default void afterRender(){}

    /**当组件被隐藏起来的时候会调用这个方法*/
    public default void onHide(){}

    /**当窗口关闭时或组件被销毁时会调用这个方法*/
    public default void onDestory(){}
}
