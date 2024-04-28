package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "appdb";
    private static final int DB_VERSION = 1;

    private static final String USERS_TABLE = "users";
    private static final String USER_ID = "userId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String LOGINS_TABLE = "logins";
    private static final String ID = "id";
    private static final String URL = "url";

    private static final String BIN_TABLE = "bin";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTableQuery = "CREATE TABLE " + USERS_TABLE + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + USERNAME + " TEXT,"
                + PASSWORD + " TEXT)";

        db.execSQL(userTableQuery);

        String loginTableQuery = "CREATE TABLE " + LOGINS_TABLE + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " TEXT,"
                + URL + " TEXT,"
                + USERNAME + " TEXT,"
                + PASSWORD + " TEXT)";

        db.execSQL(loginTableQuery);

        String binTableQuery = "CREATE TABLE " + BIN_TABLE + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID + " TEXT,"
                + URL + " TEXT,"
                + USERNAME + " TEXT,"
                + PASSWORD + " TEXT)";

        db.execSQL(binTableQuery);
    }

    public void addNewUser(String firstName, String lastName, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FIRST_NAME, firstName);
        values.put(LAST_NAME, lastName);
        values.put(USERNAME, username);
        values.put(PASSWORD, password);

        db.insert(USERS_TABLE, null, values);

        db.close();
    }

    public boolean usernameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USERNAME + " = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    @SuppressLint("Range")
    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        String[] columns = {USER_ID, FIRST_NAME, LAST_NAME};

        String selection = USERNAME + " = ? AND " + PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                USERS_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(USER_ID));
            String firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME));

            user = new User(userId, firstName, lastName, username, password);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return user;
    }

    @SuppressLint("Range")
    public User getUserByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        String[] columns = {USER_ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD};

        String selection = USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(
                USERS_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME));
            String username = cursor.getString(cursor.getColumnIndex(USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(PASSWORD));

            user = new User(userId, firstName, lastName, username, password);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return user;
    }

    public void addNewLogin(int userId, String websiteUrl, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_ID, userId);
        values.put(URL, websiteUrl);
        values.put(USERNAME, username);
        values.put(PASSWORD, password);

        db.insert(LOGINS_TABLE, null, values);

        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Login> getLoginsByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Login> logins = new ArrayList<>();

        String[] columns = {ID, URL, USERNAME, PASSWORD};
        String selection = USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(
                LOGINS_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String websiteUrl = cursor.getString(cursor.getColumnIndex(URL));
                String username = cursor.getString(cursor.getColumnIndex(USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(PASSWORD));

                Login login = new Login(id, userId, websiteUrl, username, password);
                logins.add(login);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return logins;
    }

    public void updateLogin(Login login) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(URL, login.getWebsiteUrl());
        values.put(USERNAME, login.getUsername());
        values.put(PASSWORD, login.getPassword());

        String whereClause = USER_ID + " = ? AND " + ID + " = ?";
        String[] whereArgs = {String.valueOf(login.getUserId()), String.valueOf(login.getId())};

        db.update(LOGINS_TABLE, values, whereClause, whereArgs);

        db.close();
    }

    public void deleteLogin(Login login) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = USER_ID + " = ? AND " + ID + " = ?";
        String[] selectionArgs = {String.valueOf(login.getUserId()), String.valueOf(login.getId())};
        db.delete(LOGINS_TABLE, selection, selectionArgs);
        db.close();
    }

    public void addNewBin(Login login) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_ID, login.getUserId());
        values.put(URL, login.getWebsiteUrl());
        values.put(USERNAME, login.getUsername());
        values.put(PASSWORD, login.getPassword());

        db.insert(BIN_TABLE, null, values);

        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Login> getBinsByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Login> logins = new ArrayList<>();

        String[] columns = {ID, URL, USERNAME, PASSWORD};
        String selection = USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(
                BIN_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String websiteUrl = cursor.getString(cursor.getColumnIndex(URL));
                String username = cursor.getString(cursor.getColumnIndex(USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(PASSWORD));

                Login login = new Login(id, userId, websiteUrl, username, password);
                logins.add(login);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return logins;
    }

    public void deleteBin(Login login) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = USER_ID + " = ? AND " + ID + " = ?";
        String[] selectionArgs = {String.valueOf(login.getUserId()), String.valueOf(login.getId())};
        db.delete(BIN_TABLE, selection, selectionArgs);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }
}
