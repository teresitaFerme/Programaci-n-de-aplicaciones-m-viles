package es.ucm.fdi.animalcare.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task implements Serializable {
    public static final int FREQUENCY_NONE = 0;
    public static final int FREQUENCY_DAILY = 1;
    public static final int FREQUENCY_WEEKLY = 2;
    public static final int FREQUENCY_MONTHLY = 3;
    public static final int FREQUENCY_YEARLY = 4;

    private Integer mId;
    private Integer mPetId;
    private String mTaskName;
    private Date mScheduleDatetime;
    private Date mTaskDoneDatetime;
    private String mDescription;
    private Integer mFreq;

    public Task(Integer mId, Integer mPetId, String mTaskName, String mScheduleDatetime, String mTaskDoneDatetime, String mDescription, Integer mFreq) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date schedDate = null;
        Date doneDate = null;

        try {
            schedDate = dateFormat.parse(mScheduleDatetime);
            if(mTaskDoneDatetime != null)
                doneDate = dateFormat.parse(mTaskDoneDatetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.mId = mId;
        this.mPetId = mPetId;
        this.mTaskName = mTaskName;
        this.mScheduleDatetime = schedDate;
        this.mTaskDoneDatetime = doneDate;
        this.mDescription = mDescription;
        this.mFreq = mFreq;
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

    public String getmFreqName(){
        String freqName = "";
        switch (mFreq){
            case FREQUENCY_NONE:
                freqName = "Nada";
                break;
            case FREQUENCY_DAILY:
                freqName = "Diario";
                break;
            case FREQUENCY_WEEKLY:
                freqName = "Semanal";
                break;
            case FREQUENCY_MONTHLY:
                freqName = "Mensual";
                break;
            case FREQUENCY_YEARLY:
                freqName = "Anual";
                break;
        }
        return freqName;
    }

    public static String[] getmFreqNames(){
        return new String[]{"Nada", "Diario", "Semanal", "Mensual", "Anual"};
    }

    public Integer getmFreq() {
        return mFreq;
    }

    public void setmFreq(Integer mFreq) {
        this.mFreq = mFreq;
    }
}
