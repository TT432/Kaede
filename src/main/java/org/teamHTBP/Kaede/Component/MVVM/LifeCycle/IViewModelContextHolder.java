package org.teamHTBP.Kaede.Component.MVVM.LifeCycle;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.teamHTBP.Kaede.Component.MVVM.ViewModel.ViewModelContext;

@OnlyIn(Dist.CLIENT)
public interface IViewModelContextHolder {
    public ViewModelContext getViewModelContext();
}
