package project.csci.geocaching;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 1;

    public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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


}
