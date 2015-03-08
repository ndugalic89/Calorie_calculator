package nidjo.fitnesskalkulatorkalorija;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Metoda2 extends ActionBarActivity implements OnClickListener,
		OnCheckedChangeListener {

    //variable initialization

	EditText godine, tezina, vrijeme, bpm, vjezba;
	TextView rezultat;
	Button izracunaj, spremi;
	RadioButton muskarac, zena;
	RadioGroup rgSpol;
	String kilokalorije2, dzuli2;
	double HR, T, G, V, mKalorije, zKalorije, kCal2, J2;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kalkulator2);
		initialize();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

		rgSpol.setOnCheckedChangeListener(this);
		izracunaj.setOnClickListener(this);
		spremi.setOnClickListener(this);
		
		SharedPreferences sp2 = getSharedPreferences("prefs2", Activity.MODE_PRIVATE);
	    godine.setText(sp2.getString("clever1", null));
	    tezina.setText(sp2.getString("clever2", null));
	    

	}

	private void initialize() {
		// TODO Auto-generated method stub
		rgSpol = (RadioGroup) findViewById(R.id.odabir);
		muskarac = (RadioButton) findViewById(R.id.muskarac);
		zena = (RadioButton) findViewById(R.id.zena);
		godine = (EditText) findViewById(R.id.etGodine);
		tezina = (EditText) findViewById(R.id.etTezina);
		vrijeme = (EditText) findViewById(R.id.etVrijeme);
		bpm = (EditText) findViewById(R.id.etBpm);
		vjezba = (EditText) findViewById (R.id.etAktinost);
		izracunaj = (Button) findViewById(R.id.izracunaj);
		spremi = (Button) findViewById(R.id.spremi);
		rezultat = (TextView) findViewById(R.id.tvRezultat);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		SharedPreferences sp2 = getSharedPreferences("prefs2", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp2.edit();
		editor.putString("clever1", godine.getText().toString());
		editor.putString("clever2", tezina.getText().toString());
		editor.commit();

        //Button click events

		switch (v.getId()) {
		case R.id.izracunaj:
			if(godine.getText().length() == 0 || tezina.getText().length() == 0 || vrijeme.getText().length() == 0 || bpm.getText().length() == 0 || vjezba.getText().length() == 0 || (muskarac.isChecked() != true && zena.isChecked() != true)) {
				Toast.makeText(this, "Nisu popunjena sva polja ili nije odabran spol", Toast.LENGTH_SHORT).show();
				} else if (muskarac.isChecked() == true) {
					calculateM();
				} else if (zena.isChecked() == true) {
					calculateZ();
				}
			break;
		case R.id.spremi:
			boolean diditwork = true;

			try{
			String tipVjezbe = vjezba.getText().toString();
			String kilo_kalorije = kilokalorije2.toString();
			String j_kalorije = dzuli2.toString();
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
			
		}
	}

	private void calculateZ() {
		// TODO Auto-generated method stub

        //calculating calories if "zena" is checked and displaying result

		G = Double.parseDouble(godine.getText().toString());
		T = Double.parseDouble(tezina.getText().toString());
		V = Double.parseDouble(vrijeme.getText().toString());
		HR = Double.parseDouble(bpm.getText().toString());
		
		zKalorije = ((-20.4022 + (0.4472 * HR) - (0.1263 * T) + (0.074 * G)) / 4.184) * V;
		zKalorije = Math.round(zKalorije * 100.0) / 100.0;

        J2 = zKalorije*4.1855;
        J2 = Math.round(J2*100.0)/100.0;
        
        kilokalorije2 = String.valueOf(zKalorije);
        dzuli2 = String.valueOf(J2);
		
		rezultat.setText("Potrošene kalorije: " + zKalorije + " kCal");
	}

	private void calculateM() {
		// TODO Auto-generated method stub

        //calculating calories if "muskarac" is checked and displaying result
		
		G = Double.parseDouble(godine.getText().toString());
		T = Double.parseDouble(tezina.getText().toString());
		V = Double.parseDouble(vrijeme.getText().toString());
		HR = Double.parseDouble(bpm.getText().toString());
		

		mKalorije = ((-55.0969 + (0.6309 * HR) + (0.1988 * T) + (0.2017 * G)) / 4.184) * V;
		mKalorije = Math.round(mKalorije * 100.0) / 100.0;

        J2 = mKalorije*4.1855;
        J2 = Math.round(J2*100.0)/100.0;
        
        kilokalorije2 = String.valueOf(mKalorije);
        dzuli2 = String.valueOf(J2);
		
		rezultat.setText("Potrošene kalorije: " + mKalorije + " kCal");
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

        //showing that "muskarac" or "zena" is checked

		if (muskarac.isChecked()) {
			Toast.makeText(this, "Odabrano: muškarac", Toast.LENGTH_SHORT).show();
		} else if (zena.isChecked()) {
			Toast.makeText(this, "Odabrano: žena", Toast.LENGTH_SHORT).show();
		}
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
			Intent intent = new Intent(Metoda2.this, Pregled.class);
			startActivity(intent);
			break;
		case R.id.dnevnik_unosa:
			Intent intent2 = new Intent(Metoda2.this, PregledUnosa.class);
			startActivity(intent2);
			break;
		case R.id.stoperica:
			Intent intent4 = new Intent(Metoda2.this, Stoperica.class);
			startActivity(intent4);
			break;
		case R.id.aboutApp:
			Intent intent3 = new Intent ("android.intent.action.ABOUT");
			startActivity(intent3);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
