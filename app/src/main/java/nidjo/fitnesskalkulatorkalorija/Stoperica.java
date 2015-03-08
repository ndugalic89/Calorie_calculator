package nidjo.fitnesskalkulatorkalorija;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class Stoperica extends ActionBarActivity implements OnClickListener {
	
	Chronometer stoperica;
	Button start, stop, reset;
	long timeWhenStopped = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stopwatch);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		stoperica = (Chronometer) findViewById (R.id.chronometer1);
		start = (Button) findViewById (R.id.bStart);
		stop = (Button) findViewById (R.id.bStop);
		reset = (Button) findViewById (R.id.bReset);
		
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		reset.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.bStart:
			stoperica.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
			stoperica.start();
			break;
		case R.id.bStop:
			timeWhenStopped = stoperica.getBase() - SystemClock.elapsedRealtime();
			stoperica.stop();
			break;
		case R.id.bReset:
			stoperica.setBase(SystemClock.elapsedRealtime());
			stoperica.stop();
			timeWhenStopped = 0;
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.izlaz:
			finish();
			break;
		case R.id.pregled:
			Intent intent = new Intent(Stoperica.this, Pregled.class);
			startActivity(intent);
			break;
		case R.id.dnevnik_unosa:
			Intent intent2 = new Intent(Stoperica.this, PregledUnosa.class);
			startActivity(intent2);
			break;
		case R.id.stoperica:
			Toast.makeText(getApplicationContext(), "�toperica je ve� otvorena", Toast.LENGTH_SHORT).show();
			break;
		case R.id.aboutApp:
			Intent intent3 = new Intent ("android.intent.action.ABOUT");
			startActivity(intent3);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
