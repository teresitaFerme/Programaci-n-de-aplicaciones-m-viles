package es.ucm.fdi.animalcare.database;

import android.provider.BaseColumns;

public final class AnimalCareDatabase {

    private AnimalCareDatabase() {}

    /* Inner class that defines the table contents */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }

    public static class Pet implements BaseColumns {
        public static final String TABLE_NAME = "pet";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_ID_OWNER = "id_owner";
    }

    public static class Task implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SCHEDULE_DATETIME = "schedule_datetime";
        public static final String COLUMN_NAME_TASKDONE_DATETIME = "taskdone_datetime";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final Integer COLUMN_NAME_FREQUENCY = "frequency";
        public static final String COLUMN_NAME_ID_PET = "id_pet";
    }
}
