package org.teamHTBP.Kaede.Component.LifeCycleComponent;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.EnumLifeCycle;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.ILifeCycleObserver;
import org.teamHTBP.Kaede.Component.MVVM.ViewModelContext;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractLifeCycleScreen extends Screen implements ILifeCycleComponent{
    /**该GUI组件所储存的所有生命周期组件*/
    private final List<ILifeCycleComponent> lifeCycleComponents = new LinkedList<>();
    /**所储存的ViewModel容器*/
    private ViewModelContext context;
    /**Screen现在所处的生命周期*/
    private EnumLifeCycle lifeCycle;
    /**生命观察者*/
    private ILifeCycleObserver lifeCycleObserver;

    public AbstractLifeCycleScreen(ITextComponent titleIn) {
        super(titleIn);
        context = new ViewModelContext();
    }

    public AbstractLifeCycleScreen(String titleIn) {
        this(new StringTextComponent(titleIn));
    }

    @Override
    public final void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        setLifeCycle(EnumLifeCycle.RENDER);
        onRender();
        lifeCycleComponents.forEach(component -> component.render(matrixStack,mouseX,mouseY,partialTicks));
        lifeCycleComponents.forEach(ILifeCycleComponent::onRender);
        afterRenderComponent(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**after the screen render screen and the component*/
    public abstract void afterRenderComponent(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);

    /**调用生命周期的onCreate方法*/
    @Override
    public final void init() {
        beforeInit();
        super.init();
        setLifeCycle(EnumLifeCycle.CREATE);
        onCreate();
        lifeCycleComponents.forEach(ILifeCycleComponent::onCreate);
    }

    public abstract void beforeInit();

    /**调用生命周期的onDestory方法*/
    @Override
    public final void onClose() {
        super.onClose();
        setLifeCycle(EnumLifeCycle.DESTORY);
        onDestory();
    }

    /**加入生命周期组件*/
    public void addLifeComponent(ILifeCycleComponent component)throws NullPointerException{
        if(component == null) throw new NullPointerException("component is Empty,please check your component");
        this.lifeCycleComponents.add(component);
    }

    protected ViewModelContext getViewModelContext(){
        return this.context;
    }

    /**设置生命观察者*/
    protected void setLifeCycleObserver(ILifeCycleObserver observer){
        if(this.lifeCycle == EnumLifeCycle.DESTORY) return;
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
}
