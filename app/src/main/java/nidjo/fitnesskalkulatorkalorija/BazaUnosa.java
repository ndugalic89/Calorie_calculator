package nidjo.fitnesskalkulatorkalorija;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaUnosa {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_UKCAL = "kilokalorije";
	public static final String KEY_DATE = "datum";
	
	private static final String DATABASE_NAME = "BazaUnosadb";
	private static final String DATABASE_TABLE = "tablicaUnosa";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper myHelper;
	private final Context myContext;
	private SQLiteDatabase myDatabase;
	private String[] columns = new String[] {KEY_ROWID, KEY_UKCAL, KEY_DATE};
	
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + 
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_UKCAL + " TEXT NOT NULL, "
					+  KEY_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	public BazaUnosa(Context c){
		myContext = c;
	}
	
	public BazaUnosa open() throws SQLException {
		myHelper = new DbHelper(myContext);
		myDatabase = myHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		myHelper.close();
	}
	
	public long createEntry(String ukcal, String date) {
		// TODO Auto-generated method stub
		
		ContentValues cv2 = new ContentValues();
		cv2.put(KEY_UKCAL, ukcal);
		cv2.put(KEY_DATE, date);

		return myDatabase.insert(DATABASE_TABLE, null, cv2);
	}
	
	public void deleteActivity(ActivityRecords aktivnost) {
		myDatabase.delete(DATABASE_TABLE, KEY_ROWID	+ " = " + aktivnost.getId(), null);
	}
	
	public List<ActivityRecords> getData() {
		// TODO Auto-generated method stub
		List<ActivityRecords> aktivnosti = new ArrayList<ActivityRecords>();
		Cursor c = myDatabase.query(DATABASE_TABLE, columns, null, null, null, null, KEY_ROWID + " DESC");
		c.moveToFirst();
		while (!c.isAfterLast()) {
			ActivityRecords aktivnost = cursorToActivity(c);
			aktivnosti.add(aktivnost);
			c.moveToNext();
		}
		
		c.close();
		return aktivnosti;
	}
	
	private ActivityRecords cursorToActivity(Cursor c) {
		ActivityRecords aktivnost = new ActivityRecords();
		aktivnost.setId(c.getInt(0));
		aktivnost.setUkcal(c.getString(1));
		aktivnost.setDate(c.getString(2));
		return aktivnost;
	}

}
