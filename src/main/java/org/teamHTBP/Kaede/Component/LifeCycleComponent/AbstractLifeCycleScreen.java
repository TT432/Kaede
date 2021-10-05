package org.teamHTBP.Kaede.Component.LifeCycleComponent;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.EnumLifeCycle;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleComponent;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleObserver;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.IViewModelContextHolder;
import org.teamHTBP.Kaede.Component.MVVM.ViewModelContext;

import java.util.LinkedList;
import java.util.List;

/**
 * 有生命感知且可被观察的Screen,实现<code>ILifeCycleComponent</code>接口,
 * 所有的生命周期都可以通过{@link EnumLifeCycle}看到<br/>
 * 生命周期具体为：
 * beforeInit（该类被实例化，调用init前）{@link ILifeCycleComponent#beforeInit() } <br/>
 * -> init（Screen被调用init时）{@link ILifeCycleComponent#init() } <br/>
 * -> render（Screen每次被渲染时）{@link ILifeCycleComponent#onRender()}<br/>
 * -> *hide （Screen被隐藏时，需要手动实现调用）{@link ILifeCycleComponent#onHide()}<br/>
 * -> onClose (Screen被执行close时){@link ILifeCycleComponent#onClose()}<br/>
 * 使用{@link AbstractLifeCycleScreen#getLifeCycle()} 可以获得该组件的状态情况<br/>
 * 该组件是可观察的，可实现<code>ILifeCycleObserver</code>实现生命观察者，并使用{@link AbstractLifeCycleScreen#setLifeCycleObserver(ILifeCycleObserver)}进行观察者注册
 * 该组件支持MVVM架构，实现了<code>IViewModelContextHolder</code>,可以获取该组件储存的ViewModel
 *
 * */
@OnlyIn(Dist.CLIENT)
public abstract class AbstractLifeCycleScreen extends Screen implements ILifeCycleComponent, IViewModelContextHolder {

    /**该GUI组件所储存的所有生命周期组件*/
    private final List<ILifeCycleComponent> lifeCycleComponents = new LinkedList<>();
    /**所储存的ViewModel容器*/
    private ViewModelContext context;
    /**Screen现在所处的生命周期*/
    private EnumLifeCycle lifeCycle;
    /**组件生命观察者*/
    private ILifeCycleObserver lifeCycleObserver;

    /**
     * 构造方法
     * @param titleIn GUI的title，<b>注意：这个是Kaede框架区别GUI不同的唯一方式，请慎重考虑名字<b/>
     * */
    public AbstractLifeCycleScreen(ITextComponent titleIn) {
        super(titleIn);
        context = new ViewModelContext();
        beforeInit();
    }

    /**
    * 使用String标识GUI的title
     * @param titleIn String类型的title，<b>注意：这个是Kaede框架区别GUI不同的唯一方式，请慎重考虑名字<b/>
    * */
    public AbstractLifeCycleScreen(String titleIn) {
        this(new StringTextComponent(titleIn));
    }

    @Override
    public void init() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        setLifeCycle(EnumLifeCycle.RENDER);
        onRender();
        lifeCycleComponents.forEach(component -> component.render(matrixStack,mouseX,mouseY,partialTicks));
        lifeCycleComponents.forEach(ILifeCycleComponent::onRender);
        afterRenderComponent(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**after the screen render screen and the component*/
    public void afterRenderComponent(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks){

    }


    /**调用生命周期的onDestory方法*/
    @Override
    public void onClose() {
        super.onClose();
        setLifeCycle(EnumLifeCycle.CLOSE);
    }

    /**加入生命周期组件*/
    public void addLifeComponent(ILifeCycleComponent component)throws NullPointerException{
        if(component == null) throw new NullPointerException("component is Empty,please check your component");
        this.lifeCycleComponents.add(component);
    }

    public ViewModelContext getViewModelContext(){
        return this.context;
    }

    /**设置生命观察者*/
    protected void setLifeCycleObserver(ILifeCycleObserver observer){
        if(this.lifeCycle == EnumLifeCycle.CLOSE) return;
        this.lifeCycleObserver = observer;
    }

    /**设置生命周期*/
    private void setLifeCycle(EnumLifeCycle lifeCycle){
        this.lifeCycle = lifeCycle;
        dispatchLifeCycleEvent(lifeCycle);
    }

    /**提醒观察者生命周期发生改变*/
    private void dispatchLifeCycleEvent(EnumLifeCycle lifeCycle){
        if(lifeCycleObserver != null) lifeCycleObserver.onLifeCycleChanged(lifeCycle);
    }


    @Override
    public final EnumLifeCycle getLifeCycle() {
        return this.lifeCycle;
    }
}
