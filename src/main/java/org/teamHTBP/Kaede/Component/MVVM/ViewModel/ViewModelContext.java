package org.teamHTBP.Kaede.Component.MVVM.ViewModel;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.LinkedHashMap;
import java.util.Map;

/**一个全局ViewModel容器，用于制造与获取viewModel*/
@OnlyIn(Dist.CLIENT)
public class ViewModelContext {
    private Map<ITextComponent,ViewModel> viewModelHolder = new LinkedHashMap<>();

    /**
     * 获取GUI所存储的ViewModel
     * @param iTextComponent GUI的Title，一般认定为GUI的ID
     * @param modelClass ViewModel的
     * */
    public <T extends ViewModel> T get(ITextComponent iTextComponent, Class<T> modelClass){
        try {
            if (viewModelHolder == null) throw new NullPointerException("the container of the GUI is null ,please check your code!");
            if (viewModelHolder.containsKey(iTextComponent)) {
                T model = modelClass.cast(viewModelHolder.get(iTextComponent));
                if(model != null) return model;
            }
            T model = modelClass.newInstance();
            viewModelHolder.put(iTextComponent,model);
            return model;
        }catch (InstantiationException | IllegalAccessException ex){
            ex.printStackTrace();
            // from android jetPack
            throw new RuntimeException("can not create the instance of" + modelClass);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void clearModels(){
        if(viewModelHolder != null) viewModelHolder.clear();
    }
}
