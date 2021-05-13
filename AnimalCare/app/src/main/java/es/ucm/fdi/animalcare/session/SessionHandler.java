package es.ucm.fdi.animalcare.session;

public class SessionHandler {
    private static final String sharedPrefName = "preferences";

    public static String getSPname(){
        return sharedPrefName;
    }
}
