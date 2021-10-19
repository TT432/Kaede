package org.teamHTBP.Kaede.DemoGui.LiveData;

import org.teamHTBP.Kaede.Component.MVVM.ViewModel.LiveData;
import org.teamHTBP.Kaede.Component.MVVM.ViewModel.ViewModel;

public class TestScreenViewModel extends ViewModel {
    LiveData<Integer> count = new LiveData<>(0);



    @Override
    public void onClear() {
        
    }
}
