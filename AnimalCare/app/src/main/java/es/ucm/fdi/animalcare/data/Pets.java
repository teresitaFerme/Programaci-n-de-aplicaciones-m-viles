package es.ucm.fdi.animalcare.data;

public class Pets {
    private String mName;
    private String mId;
    private String mType;
    private String mIdOwner;
/*
    Pets(String name, int id, String type, Integer idOwner){
        /*
        mName = name;
        mId = id;
        mType = type;
        mIdOwner = idOwner;

    }
*/
    public String getId() {return mId;}
    public String getName() {return mName;}
    public String getType() {return mType;}
    public String getIdOwner() {return mIdOwner;}

    public void setId(String id) {mId = id;}
    public void setName(String name) {mName = name;}
    public void setType(String type) {mType = type;}
    public void setIdOwner(String idOwner) { mIdOwner = idOwner;}
}

