package nidjo.fitnesskalkulatorkalorija;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Metoda1 extends ActionBarActivity implements OnClickListener, OnItemSelectedListener {

	//variable initialization
	TextView rezultat;
	EditText kile, minute;
	Button izracunaj, spremi;
	Spinner odabirVjezbi;
	double x = 0, y = 0, z = 0, kalorije, J;
	String met, kilokalorije, dzuli;	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kalkulator);
		initialize();
			
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    SharedPreferences sp = getSharedPreferences("prefs", Activity.MODE_PRIVATE);
	    kile.setText(sp.getString("clever", null));

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vjezbe, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		odabirVjezbi.setAdapter(adapter);

		odabirVjezbi.setOnItemSelectedListener(this);
		izracunaj.setOnClickListener(this);
		spremi.setOnClickListener(this);
		

	}

	private void initialize() {
		// TODO Auto-generated method stub
		rezultat = (TextView) findViewById(R.id.tvRezultat);
		kile = (EditText) findViewById(R.id.etTezina);
		minute = (EditText) findViewById(R.id.etVrijeme);
		izracunaj = (Button) findViewById(R.id.bCalc);
		spremi = (Button) findViewById(R.id.bSave);
		odabirVjezbi = (Spinner) findViewById(R.id.spinner1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.izlaz:
			finish();
			break;
		case R.id.pregled:
			Intent intent = new Intent(Metoda1.this, Pregled.class);
			startActivity(intent);
			break;
		case R.id.dnevnik_unosa:
			Intent intent2 = new Intent(Metoda1.this, PregledUnosa.class);
			startActivity(intent2);
			break;
		case R.id.stoperica:
			Intent intent3 = new Intent(Metoda1.this, Stoperica.class);
			startActivity(intent3);
			break;
		case R.id.aboutApp:
			Intent intent4 = new Intent ("android.intent.action.ABOUT");
			startActivity(intent4);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		SharedPreferences sp = getSharedPreferences("prefs", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("clever", kile.getText().toString());
		editor.commit();

		//Button click events

        switch (v.getId()) {

		case R.id.bSave:
			boolean diditwork = true;

			try{
			String tipVjezbe = odabirVjezbi.getSelectedItem().toString();
			String kilo_kalorije = kilokalorije.toString();
			String j_kalorije = dzuli.toString();
			String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			
			BazaVjezbi entry = new BazaVjezbi(this);
			entry.open();
			
			entry.createEntry(tipVjezbe, kilo_kalorije, j_kalorije, date);
			
			entry.close();
			} catch (Exception e){
				diditwork = false;
				Dialog d = new Dialog(this);
				d.setTitle("Upss!");
				TextView tv = new TextView(this);
				tv.setText("Nema podatka za pohranu");
				d.setContentView(tv);
				d.show();
				
			}finally {
				if(diditwork){
					Toast.makeText(this, "Uspješno pohranjeno", Toast.LENGTH_LONG).show();
				}
			}
			
			break;
			
		case R.id.bCalc:
			
			if(kile.getText().length() == 0 || minute.getText().length() == 0) {
				Toast.makeText(this, "Nisu popunjena sva polja", Toast.LENGTH_SHORT).show();
				} else {
				calculate();  
				}
			
			break;
		}
	}

	private void calculate() {
		// TODO Auto-generated method stub

		//method for calorie calculation and displaying results

		x = Double.parseDouble(kile.getText().toString());

        y = Double.parseDouble(minute.getText().toString());
		
        kalorije = (y * (z * 3.5 * x)/200);
        kalorije = Math.round(kalorije*100.0)/100.0;
        J = kalorije*4.1855;
        J = Math.round(J*100.0)/100.0;
        
        kilokalorije = String.valueOf(kalorije);
        dzuli = String.valueOf(J);
        
        rezultat.setText("Potrošeno: " + kalorije + " kCal");
       
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //selecting type of exercise form a dropdown menu (spinner)

		met = getResources().getStringArray(R.array.mets)[odabirVjezbi.getSelectedItemPosition()];
		z = Double.parseDouble(met);
		TextView text = (TextView) view;
		Toast.makeText(this, "Odabrano: " + text.getText(), Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
