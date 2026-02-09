package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DatabaseHelper;
import constants.DBConstants;

public class UserRepository implements IUserRepository {

    private final DatabaseHelper db;

    public UserRepository(Context context) {
        this.db = new DatabaseHelper(context);
    }

    @Override
    public boolean addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_USER_ID, user.getUserId());
        values.put(DBConstants.COL_USERNAME, user.getUsername());
        values.put(DBConstants.COL_EMAIL, user.getEmail());
        values.put(DBConstants.COL_PASSWORD, user.getPassword());
        values.put(DBConstants.COL_BIO, user.getBio());
        values.put(DBConstants.COL_IS_ACTIVE, user.isActive() ? 1 : 0);

        SQLiteDatabase database = db.getWritableDatabase();
        long result = database.insert(DBConstants.TABLE_USERS, null, values);
        database.close();
        return result != -1;
    }

    @Override
    public User getUserByUsername(String username) {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = db.getUser(username);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_USER_ID));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_EMAIL));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_PASSWORD));
            String bio = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_BIO));
            boolean isActive = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_IS_ACTIVE)) == 1;

            cursor.close();
            database.close();
            return new User(id, username, email, password, bio, isActive);
        }
        return null;
    }

    public boolean isUsernameTaken(String username) {
        Cursor cursor = db.getUser(username);
        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
        return exists;
    }

    public boolean saveUser(User user) {
        return addUser(user);
    }

}
