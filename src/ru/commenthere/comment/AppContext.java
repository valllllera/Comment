package ru.commenthere.comment;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ru.commenthere.comment.db.ORMDatabaseHelper;
import ru.commenthere.comment.model.Note;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

public class AppContext {
	public static final String TAG = "AppContext";

	public static final String DEVICE_TYPE = "android";

	public static final String API_VERSION = "v1";

	public static final String API_URL = "http://comment.dnk-b.ru/api/";

	public static final String PHOTOS_URL = "http://comment.dnk-b.ru/photos/";
	public static final String VIDEOS_URL = "http://comment.dnk-b.ru/videos/";

	public static final boolean USE_GZIP = false;

	public static final String EMAIL_KEY = "email_key";
	public static final String USER_ID_KEY = "user_id_key";
	public static final String TOKEN_KEY = "token_key";
	public static final String LATITUDE_KEY = "last_latitude";
	public static final String LONGITUDE_KEY = "last_longitude";
	public static final String EVENTS_LIST_KEY = "events_list";
	public static final String LIST_TYPE_KEY = "events_list_type";

	public static final String TYPE_KEY = "key";
	public static final String NOTE_KEY = "note";	
	public static final String POSITION_KEY = "position";
	public static final String IS_LIKE_KEY = "is_like";

	public static final String URL_KEY = "url";

	public static final int PHOTO_FILE_TYPE = 1;
	public static final int VIDOE_FILE_TYPE = 2;

	public static final int PRIVATE_TYPE = 1;
	public static final int EVENT_TYPE = 2;

	public static final int NOTES_LIST_TYPE = 1;
	public static final int MY_COMMENTS_LIST_TYPE = 2;

	private Context context;
	private SharedPreferences mPrefs;
	private ORMDatabaseHelper ormDatabaseHelper;

	private List<Note> lastReceivedNotesList;

	public AppContext(Context context) {
		this.context = context;
		this.mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public String getUserEmail() {
		return mPrefs.getString(EMAIL_KEY, "");
	}

	public void setUserEmail(String email) {
		Editor editor = mPrefs.edit();
		editor.putString(EMAIL_KEY, email);
		editor.commit();
	}

	public int getUserId() {
		return mPrefs.getInt(USER_ID_KEY, -1);
	}

	public void setUserId(int userId) {
		Editor editor = mPrefs.edit();
		editor.putInt(USER_ID_KEY, userId);
		editor.commit();
	}

	public String getUserToken() {
		return mPrefs.getString(TOKEN_KEY, "");
	}

	public void setUserToken(String token) {
		Editor editor = mPrefs.edit();
		editor.putString(TOKEN_KEY, token);
		editor.commit();
	}

	public boolean isLogged() {
		return !TextUtils.isEmpty(getUserToken());
	}

	public void saveLastLocation(Location location) {
		Editor editor = mPrefs.edit();
		editor.putFloat(LATITUDE_KEY, (float) location.getLatitude());
		editor.putFloat(LONGITUDE_KEY, (float) location.getLongitude());
		editor.commit();
	}

	public float getLastLongitude() {
		return mPrefs.getFloat(LONGITUDE_KEY, 0);
	}

	public float getLastLatitude() {
		return mPrefs.getFloat(LATITUDE_KEY, 0);
	}

	public void setReceivedNotesList(List<Note> list) {
		lastReceivedNotesList = list;
	}

	public List<Note> getLastReceivedNotesList() {
		return lastReceivedNotesList;
	}
	
	public ORMDatabaseHelper getOrmDatabaseHelper() {
		return ormDatabaseHelper;
	}
	
	
	public void setOrmDatabaseHelper(ORMDatabaseHelper ormDatabaseHelper) {
		this.ormDatabaseHelper = ormDatabaseHelper;
	}

	public void releaseOrmDatabaseHelper(){
	       OpenHelperManager.releaseHelper();
	       this.ormDatabaseHelper = null;
	   }
}
