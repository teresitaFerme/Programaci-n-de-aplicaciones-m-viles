package es.ucm.fdi.animalcare.main;

import android.content.Intent;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class MainPresenter extends BasePresenter {
    private MainView mMainView;
    private MainModel mMainModel;

    MainPresenter(MainView  mainView){
        mMainView = mainView;
        mMainModel = new MainModel();
    }

    public void iniciarSesion() {
        mMainView.iniciarSesion();
    }

    public void registrarse(){
        mMainView.registrarse();
    }
}
