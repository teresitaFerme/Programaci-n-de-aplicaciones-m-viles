package es.ucm.fdi.animalcare.data;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private static String mName;
    private static String mUsername;
    private static Integer mId;
    private static List<Pets> mPetList;
    private static User user = null;


    public static User getInstance(String name, String username, int id){
        if(user == null){
            user = new User(name, username, id);
        }
        return user;
    }

    public static void eraseUser(){
        user = null;
    }

    public User(String name, String username, int id){
        mName = name;
        mUsername = username;
        mId = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public List<Pets> getmPetList() {
        return mPetList;
    }

    public void setmPetList(List<Pets> mPetList) {
        this.mPetList = mPetList;
    }
}
