package org.teamHTBP.Kaede.Component.MVVM.ViewModel;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * GUI基于MVVM架构编写
 * ViewModel用于存放所有需要观察的数据
 * 模仿Android JetPack编写
 * */
@OnlyIn(Dist.CLIENT)
public abstract class ViewModel {
    public abstract void onClear();
}
