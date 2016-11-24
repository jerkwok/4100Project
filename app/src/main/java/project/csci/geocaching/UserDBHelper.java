package project.csci.geocaching;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 1;

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Users(userId INTEGER PRIMARY KEY,name TEXT, password TEXT, caches INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    public void addEntry(String username, String password){
        String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", username);
        values.put("password", hashedPass);
        values.put("caches", 0);
        db.insert("Users", null, values);

    }

    public User getUser(int userId){
        User returnUser = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM Users WHERE userId = ?",
                new String[]{Integer.toString(userId)});

        if (cursor.moveToFirst()){
            returnUser = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(3));
        }

        cursor.close();
        return returnUser;
    }


    public boolean validatePass(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean match = false;
        Cursor cursor = db.rawQuery("SELECT  * FROM Users WHERE name = ?",
                new String[]{username});

        if (cursor.moveToFirst()){
            if(BCrypt.checkpw(password,cursor.getString(2))){
                match = true;
            }
        }

        cursor.close();
        return match;
    }

    public boolean checkUserDupes(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean match = false;
        Cursor cursor = db.rawQuery("SELECT  * FROM Users WHERE name = ?",
                new String[]{username});

        if (cursor.getCount() > 0){
                match = true;
        }

        cursor.close();
        return match;
    }

    public int getUserCaches(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        int caches = 0;
        Cursor cursor = db.rawQuery("SELECT  * FROM Users WHERE name = ?",
                new String[]{username});

        if (cursor.moveToFirst()){
            if (cursor.getCount() > 0){
                caches = cursor.getInt(3);
            }
        }

        Log.d("CACHES", Integer.toString(caches));
        cursor.close();
        return caches;
    }
}
