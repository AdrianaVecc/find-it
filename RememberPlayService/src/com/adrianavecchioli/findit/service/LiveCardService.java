package com.adrianavecchioli.findit.service;

import android.R;
import android.R.menu;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;

import com.adrianavecchioli.findit.Find;
import com.adrianavecchioli.findit.db.SqlHelper;
import com.adrianavecchioli.findit.domain.RememberItem;
import com.google.android.glass.app.Card;
import com.google.android.glass.app.Card.ImageLayout;
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;

public class LiveCardService extends Service implements Callback {
	private LiveCard mLiveCard;
	public static final String LIVE_CARD_TAG="livecardTag";
	public static final String KEY_REMEMBER_ITEM="remember_item_key";

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		RememberItem item=intent.getExtras().getParcelable(KEY_REMEMBER_ITEM);
		mLiveCard = new LiveCard(this,LIVE_CARD_TAG);
		updateLiveCardWithRememberItem(mLiveCard,item);
		mLiveCard.publish(PublishMode.REVEAL);
		return START_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		return new LocalBinder<Service>(this);
	}
	private void updateLiveCardWithRememberItem(LiveCard mLiveCard,RememberItem item) {
        mLiveCard.setViews(createLiveCardRemoteView(item));
        mLiveCard.setAction(createIntentHandler(item));
	}
	@Override
	public boolean handleMessage(Message msg) {
		if(msg!=null && msg.obj instanceof RememberItem){
			RememberItem item=(RememberItem)msg.obj;
			updateLiveCardWithRememberItem(mLiveCard,item);
			mLiveCard.publish(PublishMode.REVEAL);
		}
		
		return false;
	}
    @Override
    public void onDestroy() {
        if (mLiveCard != null && mLiveCard.isPublished()) {
            mLiveCard.unpublish();
            mLiveCard = null;
        }
        super.onDestroy();
    }
    

	private PendingIntent createIntentHandler(RememberItem item) {
		Intent menuIntent = new Intent(this, Find.class);
        menuIntent.putExtra(Find.LIVE_CARD_TAG, item.getTag());
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
            Intent.FLAG_ACTIVITY_CLEAR_TASK);
       return  PendingIntent.getActivity(
                this, 0, menuIntent, 0);
	}
	private RemoteViews createLiveCardRemoteView(RememberItem item2) {
		RemoteViews mLiveCardView = new RemoteViews(getPackageName(),
            R.layout.remote_layout);
        mLiveCardView.setTextViewText(R.id.text1,item2.getTag());
	}
}
