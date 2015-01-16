package com.example.test;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class pageActivity extends ActionBarActivity {
	private WebView wb;
	private Timer timer=new Timer();
	private TimerTask task;
	String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page);
	     Bundle b= getIntent().getExtras();
	     url = b.getString("url");
		task = new TimerTask() {
			@Override
			public void run() {
				Message m = new Message();
				m.what=1;
				handler.sendMessage(m);
				
			}
		};
		
	
		timer.schedule(task, 2000, 2000); 
		
		
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				
				wb = (WebView) findViewById(R.id.webview);
				removeAllCookie();
				//wb.getSettings().setJavaScriptEnabled(true);
				wb.loadUrl(url);
				wb.setWebViewClient(new HelloWebViewClient ()); 
			}
			
		};
	};
	
	 //Web视图  
    private class HelloWebViewClient extends WebViewClient {  
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            view.loadUrl(url);  
            return true;  
        }  
    }  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//清除所有cookie
	  private void removeAllCookie()
	  {
	    CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(wb.getContext());
	    CookieManager cookieManager = CookieManager.getInstance();
	    cookieManager.setAcceptCookie(true);
	    cookieManager.removeSessionCookie();
	    
	    //String testcookie1 = cookieManager.getCookie(urlpath);
	    
	    cookieManager.removeAllCookie();
	    cookieSyncManager.sync();
	    
	    //String testcookie2 = cookieManager.getCookie(urlpath);
	  }
	  @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		removeAllCookie();
		timer.cancel();
	}
}
