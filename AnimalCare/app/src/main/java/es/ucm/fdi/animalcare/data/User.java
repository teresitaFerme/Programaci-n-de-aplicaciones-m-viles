package es.ucm.fdi.animalcare.data;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String mUsername;
    private Integer mId;
    private List<Pets> mPetList;

    public User(String username, int id){
        mUsername = username;
        mId = id;
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
