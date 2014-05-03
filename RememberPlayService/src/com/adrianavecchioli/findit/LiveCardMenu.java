package com.adrianavecchioli.findit;

import com.adrianavecchioli.findit.db.SqlHelper;
import com.adrianavecchioli.findit.domain.RememberItem;
import com.adrianavecchioli.findit.service.LiveCardService;
import com.adrianavecchioli.findit.util.RememberUtils;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class LiveCardMenu extends Activity {

	
	private RememberItem itemSelected=null;
	
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		RememberItem item=getIntent().getParcelableExtra(LiveCardService.KEY_REMEMBER_ITEM);
		if(item!=null){
			openOptionsMenu();	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.live_card_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuitem) {
		if (itemSelected == null) {
			return super.onOptionsItemSelected(menuitem);
		}
		switch (menuitem.getItemId()) {
		case R.id.menu_getdirections:
			RememberUtils.launchGoogleMap(this, itemSelected);
			return true;
		case R.id.menu_stop:
			RememberUtils.stopLiveCardService(this);
			return true;
		default:
			return super.onOptionsItemSelected(menuitem);
		}
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		finish();
	}
}
