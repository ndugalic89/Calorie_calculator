package nidjo.fitnesskalkulatorkalorija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Pocetna extends ActionBarActivity implements OnClickListener {

    //variable initialzation
	
	
	Button metoda1, metoda2, st_watch, bpregled, bunos, bPregledunosa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pocetna2);
		
		metoda1 = (Button) findViewById (R.id.metoda1);
		metoda2 = (Button) findViewById (R.id.metoda2);
		bpregled = (Button) findViewById (R.id.bPregled);
		st_watch = (Button) findViewById (R.id.st_watch);
		bunos = (Button) findViewById (R.id.bunos);
		bPregledunosa = (Button) findViewById (R.id.bPregledunosa);
		
		metoda1.setOnClickListener(this);
		metoda2.setOnClickListener(this);
		bpregled.setOnClickListener(this);
		st_watch.setOnClickListener(this);
		bunos.setOnClickListener(this);
		bPregledunosa.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

        //button click events
		switch (v.getId()){
		case R.id.metoda1:
			Intent i1 = new Intent (Pocetna.this, Metoda1.class);
			startActivity(i1);
			break;
		case R.id.metoda2:
			Intent i2 = new Intent (Pocetna.this, Metoda2.class);
			startActivity(i2);
			break;
		case R.id.bunos:
			Intent i5 = new Intent (Pocetna.this, UnosKalorija.class);
			startActivity(i5);
			break;
		case R.id.bPregled:
			Intent i3 = new Intent (Pocetna.this, Pregled.class);
			startActivity(i3);
			break;
		case R.id.bPregledunosa:
			Intent i6 = new Intent (Pocetna.this, PregledUnosa.class);
			startActivity(i6);
			break;
		case R.id.st_watch:
			Intent i4 = new Intent (Pocetna.this, Stoperica.class);
			startActivity(i4);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.izlaz:
			finish();
			break;
		case R.id.aboutApp:
			Intent intent = new Intent ("android.intent.action.ABOUT");
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
