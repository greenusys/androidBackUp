package com.greenusys.personal.registrationapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by personal on 3/5/2018.
 */

public class MciContract {

    public static final String CONTENT_AUTHORITY = "com.example.personal.registrationapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USER = "user";


    public static final class MciEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USER)
                .build();


        public static final String TABLE_NAME = "user";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_EMAIL = "email";

        public static final String COLUMNN_NUMBER = "number";

        public static final String COLUMN_GENDER = "gender";

        public static final String COLUMNN_CLASS = "class";

        public static final String COLUMN_USER_TYPE = "userType";

        public static final String COLUMN_CLASSES = "classes";


    }
}
