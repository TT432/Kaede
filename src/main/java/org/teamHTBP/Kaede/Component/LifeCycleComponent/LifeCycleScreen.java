package org.teamHTBP.Kaede.Component.LifeCycleComponent;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import org.teamHTBP.Kaede.Component.IComponent;

import java.util.LinkedList;
import java.util.List;

public class LifeCycleScreen extends Screen implements ILifeCycleComponent{
    private final List<ILifeCycleComponent> lifeCycleComponents = new LinkedList<>();


    public LifeCycleScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    public LifeCycleScreen(String titleIn) {
        super(new StringTextComponent(titleIn));
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.afterRender();
        lifeCycleComponents.forEach(component -> component.render(matrixStack,mouseX,mouseY,partialTicks));
        lifeCycleComponents.forEach(ILifeCycleComponent::afterRender);
    }

    /**调用生命周期的onCreate方法*/
    @Override
    protected void init() {
        super.init();
        lifeCycleComponents.forEach(ILifeCycleComponent::onCreate);
    }

    /**加入生命周期组件*/
    public void addLifeComponent(ILifeCycleComponent component)throws NullPointerException{
        if(component == null) throw new NullPointerException("component is Empty,please check your component");
        this.lifeCycleComponents.add(component);
    }
}
