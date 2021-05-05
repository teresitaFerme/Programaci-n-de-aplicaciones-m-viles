package es.ucm.fdi.animalcare.data;

import java.util.Date;

enum Frequency {DAILY, MONTHLY, WEEKLY};

public class Task {
    private Integer mId;
    private Integer mPetId;
    private Date mScheduleDatetime;
    private Date mTaskDoneDatetime;
    private String mDescription;
    private Frequency mFreq;

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer getmPetId() {
        return mPetId;
    }

    public void setmPetId(Integer mPetId) {
        this.mPetId = mPetId;
    }

    public Date getmScheduleDatetime() {
        return mScheduleDatetime;
    }

    public void setmScheduleDatetime(Date mScheduleDatetime) {
        this.mScheduleDatetime = mScheduleDatetime;
    }

    public Date getmTaskDoneDatetime() {
        return mTaskDoneDatetime;
    }

    public void setmTaskDoneDatetime(Date mTaskDoneDatetime) {
        this.mTaskDoneDatetime = mTaskDoneDatetime;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Frequency getmFreq() {
        return mFreq;
    }

    public void setmFreq(Frequency mFreq) {
        this.mFreq = mFreq;
    }
}
