package com.adrianavecchioli.findit.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;

public class RememberUtils {

	
	private static String DATA_SEPARATOR=";";
	private static String DATA_NONE="";
	public static String EVERY_THING="everything";
	public static Location convertStringToLocation(String data){
		if(DATA_NONE.equalsIgnoreCase(data)){
			return null;
		}
		Location location=null;
		try{
			String items []=data.split(DATA_SEPARATOR);
			location=new Location(LocationManager.GPS_PROVIDER);
			double latitude=Double.parseDouble(items[0]);
			location.setLatitude(latitude);
			
			double longitude=Double.parseDouble(items[1]);
			location.setLongitude(longitude);
			
			double altitude=Double.parseDouble(items[2]);
			location.setAltitude(altitude);
			
			float bearing=Float.parseFloat(items[3]);
			location.setBearing(bearing);	
		}catch(Exception exception){
			exception.printStackTrace();
			location=null;
		}
		return location;
	}
	
	public static String getLocationAsString(Location location){
		if(location==null){
			return DATA_NONE;
		}
		StringBuilder builder=new StringBuilder();
		builder.append(location.getLatitude());
		builder.append(DATA_SEPARATOR);
		builder.append(location.getLongitude());
		builder.append(DATA_SEPARATOR);
		builder.append(location.getAltitude());
		builder.append(DATA_SEPARATOR);
		builder.append(location.getBearing());
		builder.append(DATA_SEPARATOR);
		return builder.toString();
	}
	

	public static Location getDefaultLocation(){
		Location location=new Location("Remember");
		location.setLatitude(48.860611);
		location.setLongitude(2.337644);
		return location;
		
	}
	public static Intent getGeoIntentFromLocation(Location location){
		Intent dir = new Intent(Intent.ACTION_VIEW);
    	dir.setData(Uri.parse("google.navigation:q=" + location.getLatitude() + ", " + location.getLongitude()));
		return dir;
	}
	public static Bitmap getBitmap(String filePath){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inSampleSize=4;
		return BitmapFactory.decodeFile(filePath, options);
	}

}
