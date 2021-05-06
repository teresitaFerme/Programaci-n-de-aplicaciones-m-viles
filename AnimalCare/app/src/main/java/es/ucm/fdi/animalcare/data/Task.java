package es.ucm.fdi.animalcare.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task {

    public enum Frequency {NONE, DAILY, MONTHLY, WEEKLY};


    private Integer mId;
    private Integer mPetId;
    private String mTaskName;
    private Date mScheduleDatetime;
    //private Date mTaskDoneDatetime;
    private String mDescription;
    private Frequency mFreq;

    public Task(Integer mPetId, String mTaskName, String mScheduleDatetime, /*Date mTaskDoneDatetime, */String mDescription) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        Date date = null;

        try {
            date = dateFormat.parse(mScheduleDatetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.mPetId = mPetId;
        this.mTaskName = mTaskName;
        this.mScheduleDatetime = date;
        //this.mTaskDoneDatetime = mTaskDoneDatetime;
        this.mDescription = mDescription;
        //this.mFreq = mFreq;
    }

    public String getmTaskName() {
        return mTaskName;
    }

    public void setmTaskName(String mTaskName) {
        this.mTaskName = mTaskName;
    }

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

/*    public Date getmTaskDoneDatetime() {
        return mTaskDoneDatetime;
    }

    public void setmTaskDoneDatetime(Date mTaskDoneDatetime) {
        this.mTaskDoneDatetime = mTaskDoneDatetime;
    }
*/
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
