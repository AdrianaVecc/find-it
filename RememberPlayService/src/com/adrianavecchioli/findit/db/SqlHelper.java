
package com.adrianavecchioli.findit.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import com.adrianavecchioli.findit.domain.RememberItem;
import com.adrianavecchioli.findit.util.RememberUtils;


/**
 * @author JBromo
 * 
 */
public class SqlHelper extends SQLiteOpenHelper {

        private static SqlHelper helper;
        
        private SQLiteDatabase db;

        public static SqlHelper getInstance(Context context) {
                if (helper == null) {
                        helper = new SqlHelper(context);
                }
                return helper;
        }



		@Override
		public void onCreate(SQLiteDatabase db) {
			  db.execSQL("CREATE TABLE IF NOT EXISTS remember(tag TEXT UNIQUE, image TEXT,location TEXT, added_date TEXT)");
		}


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
     
        private SqlHelper(Context context) {
                super(context, "rememberdata.db", null, 1);
                db = getWritableDatabase();
        }
     
        public RememberItem findRememberItem(String tag){
        	Cursor cursor = db.query("remember", null,"tag = ?", new String[] {tag}, null, null,null,"1");
        	RememberItem item=null;
        	if (cursor != null && cursor.moveToNext()) {
        		 item = getRememberItem(cursor);
        	 }
        	return item;

              
        }
        
        public List<RememberItem> findAllRememberItem(){
        	 Cursor c = db.query("remember", null, null, null, null, null, null);
        	 List<RememberItem> items=new ArrayList<RememberItem>();
             try {
                while(c.moveToNext()){
                	RememberItem item = getRememberItem(c);
                	items.add(item);
                }

             } catch (Exception e) {
             }
             return items;
             
        }


		private RememberItem getRememberItem(Cursor c) {
			String tag= c.getString(0);
			String imageURI=c.getString(1);
			Location location=RememberUtils.convertStringToLocation(c.getString(2));
			long date=Long.parseLong(c.getString(3));
			RememberItem item=new RememberItem(tag,imageURI,location,date);
			return item;
		}
        
        public void saveRememberItem(RememberItem item){
        	RememberItem old=findRememberItem(item.getTag());
        	if(old!=null){
        		deleteRememberItem(old);
        	}
        	ContentValues values = new ContentValues();
            values.put("tag", item.getTag());
            values.put("image", item.getImagePath());
            values.put("location", RememberUtils.getLocationAsString(item.getLocation()));
            values.put("added_date", String.valueOf(item.getAddedDate()));
            db.insert("remember", null, values);
            
        }
        
        public void deleteRememberItem(RememberItem item){
        	long resultCount=db.delete("remember", "tag = ?", new String[] {item.getTag()});
            return resultCount!=0;
        }

        

}