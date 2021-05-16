package es.ucm.fdi.animalcare.data;

import android.content.SharedPreferences;
import android.content.res.Resources;

public class App {
    private Resources resources;
    private boolean darkMode = false;
    private String language = "es";
    private SharedPreferences sharedPreferences;
    private static App app = null;
    private String userName;
    private String pass;

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

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
