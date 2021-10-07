package org.teamHTBP.Kaede.Component.LifeCycleComponent;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.MVVM.IObserver;
import org.teamHTBP.Kaede.Component.MVVM.LifeCycle.*;
import org.teamHTBP.Kaede.Component.MVVM.ViewModel.ViewModelContext;

import java.util.LinkedList;
import java.util.List;

/**
 * 有生命感知且可被观察的Screen,实现<code>ILifeCycleComponent</code>接口,<br/>
 * 所有的生命周期都可以通过{@link LifeCycle.Stage}看到:<br/>
 * <pre>
 * 生命周期具体为：
 * beforeInit（该类被实例化，调用init前）{@link ILifeCycleComponent#beforeInit() } <br/>
 * -> init（Screen被调用init时）{@link ILifeCycleComponent#init() } <br/>
 * -> render（Screen每次被渲染时）{@link ILifeCycleComponent#onRender()}<br/>
 * -> *hide （Screen被隐藏时，需要手动实现调用）{@link ILifeCycleComponent#onHide()}<br/>
 * -> onClose (Screen被执行close时){@link ILifeCycleComponent#onClose()}<br/>
 * </pre>
 * 使用{@link AbstractLifeCycleScreen#getLifeCycle()} 可以获得该组件的状态情况<br/>
 * 该组件是可观察的，可实现<code>ILifeCycleObserver</code>实现生命观察者，并使用{@link AbstractLifeCycleScreen#addLifeCycleObserver(ILifeCycleObserver)}进行观察者注册
 * 该组件支持MVVM架构，实现了<code>IViewModelContextHolder</code>,可以获取该组件储存的ViewModel
 *
 * */
@OnlyIn(Dist.CLIENT)
public abstract class AbstractLifeCycleScreen extends Screen implements ILifeCycleComponent, IViewModelContextHolder {

    /**所储存的ViewModel容器*/
    private final ViewModelContext context;
    /**Screen现在所处的生命周期*/
    private LifeCycleManager lifeCycle;

    /**
     * 构造方法
     * @param titleIn GUI的title,<b>注意：这个是Kaede框架区别GUI不同的唯一方式，请慎重考虑名字<b/>
     * */
    public AbstractLifeCycleScreen(ITextComponent titleIn) {
        super(titleIn);
        context = new ViewModelContext();
        lifeCycle = new LifeCycleManager(this);
        beforeInit();
        lifeCycle.markStage(LifeCycle.Stage.CREATE);
    }

    /**
    * 使用String标识GUI的title
     * @param titleIn String类型的title，<b>注意：这个是Kaede框架区别GUI不同的唯一方式，请慎重考虑名字<b/>
    * */
    public AbstractLifeCycleScreen(String titleIn) {
        this(new StringTextComponent(titleIn));
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        lifeCycle.markStage(LifeCycle.Stage.INIT); //设置LifeCycle为Init状态
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        lifeCycle.markStage(LifeCycle.Stage.RENDER);
        onRender();
    }

    /**调用生命周期的onDestory方法*/
    @Override
    public void onClose() {
        lifeCycle.markStage(LifeCycle.Stage.CLOSE);
        super.onClose();
    }

    public ViewModelContext getViewModelContext(){
        return this.context;
    }

    /**加入生命观察者*/
    @Override
    public void addLifeCycleObserver(ILifeCycleObserver observer){
        getLifeCycle().addObserver(observer);
    }


    @Override
    public final LifeCycle getLifeCycle() {
        return this.lifeCycle;
    }
}
