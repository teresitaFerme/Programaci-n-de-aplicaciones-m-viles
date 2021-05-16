package es.ucm.fdi.animalcare.data;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String mName;
    private String mUsername;
    private Integer mId;
    private List<Pets> mPetList;

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
