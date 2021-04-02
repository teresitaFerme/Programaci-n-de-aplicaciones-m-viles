package es.ucm.fdi.animalcare.feature.upcoming;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class UpcomingPresenter extends BasePresenter {
    private UpcomingView mUpcomingView;
    private UpcomingModel mUpcomingModel;

    UpcomingPresenter(UpcomingView view){
        mUpcomingView = view;
        mUpcomingModel = new UpcomingModel();
    }
}
