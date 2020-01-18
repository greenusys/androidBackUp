package com.greenusys.allen.vidyadashboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chippy on 25-Jul-17.
 */

public class DatabaseHelper_Dash extends SQLiteOpenHelper

{
    // User table name
    public static final String TABLE_USER = "data";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "User.db";
    // User Table Columns names
    private static final String customer_id = "sno";/*
    private static final String worker_id = "worker_id";
    private static final String id = "id";*/
    private static final String name_l = "name";
    private static final String email_l = "email";
    private static final String contact_l = "contact";
    private static final String password_l = "password";
    private static final String address_l = "pictures";
    private static final String location_l = "educationDetails";
    private static final String date_l = "rewards_point";
    private static final String time_l = "wallet";
    private static final String login_status = "login_status";
    // create table sql query
    private static final String customer_master_table = "data";
    private static final String query1 = "CREATE TABLE " + customer_master_table + "(" + customer_id + " INTEGER PRIMARY KEY, " +
            name_l + " VARCHAR(30) NOT NULL, " + email_l + " VARCHAR(40) NOT NULL, " + contact_l + " VARCHAR(40) UNIQUE NOT NULL, " + password_l + " VARCHAR(30) NOT NULL, " +
            address_l + " VARCHAR(50) NOT NULL, " + location_l + " VARCHAR(40) NOT NULL, " + date_l + " VARCHAR(30) NOT NULL, " + time_l + " VARCHAR(30) NOT NULL, " + login_status + " VARCHAR(5) NOT NULL);";
    private static String DB_PATH = "";
    private SQLiteDatabase myDataBase;

    //  private static final String drop1 = "DROP TABLE IF EXISTS " + customer_master_table;
    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper_Dash(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param
     */
    public boolean addUser(Context context , final String iemail) {
        final String semail = iemail;
        final Context con = context;
        final long[] check = new long[1];

        final SQLiteDatabase db = this.getWritableDatabase();

        String url = "https://vvn.city/apps/jain/new_login.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);


                    String id = jsonObject.getString("sno");
                    String name = jsonObject.getString("firstName");
                    String lastName = jsonObject.getString("lastName");
                    String email = jsonObject.getString("email");
                    String contact = jsonObject.getString("phone");
                    String password = jsonObject.getString("pass");
                    String address = jsonObject.getString("picture");
                    String location = jsonObject.getString("educationDetails");
                    String date = jsonObject.getString("reward_point");
                    String time = jsonObject.getString("wallet");
                    int newId = Integer.parseInt(id);
                    String fname = name + " " + lastName;
                    String status = "1";

                    ContentValues values = new ContentValues();

                    values.put(customer_id, newId);
                    values.put(name_l, fname);
                    values.put(email_l, email);
                    values.put(contact_l, contact);
                    values.put(password_l, password);
                    values.put(address_l, address);
                    values.put(location_l, location);
                    values.put(date_l, date);
                    values.put(time_l, time);
                    values.put(login_status,status);
                    long result = db.insert(TABLE_USER, null, values);
                    db.close();
                    check[0] = result;
               /*     mdb.openConnection();
                    long c=mdb.customerRegister(newId,fname,email,contact,password,address,location,date,time);*/


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(con, "Check your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", iemail);
                return params;
            }
        };


        MySingleton.getInstance(con).addToRequestque(stringRequest);


        // Inserting Row


        if (check[0] == -1) {
            return false;
        } else {
            return true;
        }

    }


    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public Edit getUser() {
        // array of columns to fetch
        String[] columns = {
                customer_id,
                name_l,
                location_l,
                contact_l,
                email_l,
                address_l,
                password_l,
                date_l,
                time_l,
                login_status
        };
        // sorting orders
//
        // List<Edit> userList = new ArrayList<Edit>();
        Edit user = new Edit();
        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
     /*   *
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        if(cursor!=null) {


            System.out.println("kaif_id"+cursor);
//            System.out.println("kaif_id"+cursor.getString(cursor.getColumnIndex(customer_id)));

            cursor.moveToFirst();
            // user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(customer_id))));
            user.setName(cursor.getString(cursor.getColumnIndex(name_l)));
            user.setLocation(cursor.getString(cursor.getColumnIndex(location_l)));
            user.setContact(cursor.getString(cursor.getColumnIndex(contact_l)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(email_l)));
            user.setAddress(cursor.getString(cursor.getColumnIndex(address_l)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(password_l)));
            user.setDate(cursor.getString(cursor.getColumnIndex(date_l)));
            user.setTime(cursor.getString(cursor.getColumnIndex(time_l)));
            user.setStatus(cursor.getString(cursor.getColumnIndex(login_status)));

            cursor.close();
            db.close();
        }
        // return user list
        return user;

    }

    /**
     * This method to update user record
     *
     * @param user
     */
   /* public boolean updateUser(Edit user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, user.getTitle());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_TIME, user.getTime());
        values.put(COLUMN_PART, user.getPart());

        // updating row
     long result =    db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }*/

    /**
     * This method is to delete user record
     *
     * @param
     */

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, login_status+ " = ?",
                new String[]{String.valueOf("1")});
        db.close();
    }

}
