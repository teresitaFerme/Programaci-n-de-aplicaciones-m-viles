package es.ucm.fdi.animalcare.data;

import android.content.res.Resources;

public class App {
    private Resources resources;
    private boolean darkMode = false;
    private static App app = null;

    public static App getApp(){
        if(app != null){
            return app;
        }
        else {
            app = new App();
            return app;
        }
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public Resources getResources() {
        return resources;
    }

    public boolean getDarkMode(){
        return darkMode;
    }

    public void setDarkMode(boolean bool){
        darkMode = bool;
    }

}
