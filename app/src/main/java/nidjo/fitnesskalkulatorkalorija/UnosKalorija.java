package nidjo.fitnesskalkulatorkalorija;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Dialog;
import android.content.Intent;
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

public class UnosKalorija extends ActionBarActivity implements OnClickListener,
		OnItemSelectedListener {

    //variables initialization

	Spinner spinner1, spinner2, spinner3, spinner4;
	EditText etgrami1, etgrami2, etgrami3, etgrami4, etkcal1, etkcal2, etkcal3,
			etkcal4;
	Button bZbroji, bPonisti, bSpremiubazu;
	TextView tv_ukupno;
	String namirnice, ukupno;
	double xk = 0, yk = 0, zk = 0, mk = 0, n = 0, xg = 0, yg = 0, zg = 0,
			mg = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kcalintake);
		initialize();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.namirnice, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);

		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.namirnice, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);

		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.namirnice, android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(adapter3);

		ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
				this, R.array.namirnice, android.R.layout.simple_spinner_item);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner4.setAdapter(adapter4);

		spinner1.setOnItemSelectedListener(this);
		spinner2.setOnItemSelectedListener(this);
		spinner3.setOnItemSelectedListener(this);
		spinner4.setOnItemSelectedListener(this);
		bZbroji.setOnClickListener(this);
		bPonisti.setOnClickListener(this);
		bSpremiubazu.setOnClickListener(this);

	}

	private void initialize() {
		// TODO Auto-generated method stub
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		spinner4 = (Spinner) findViewById(R.id.spinner4);
		etkcal1 = (EditText) findViewById(R.id.etkcal1);
		etkcal2 = (EditText) findViewById(R.id.etkcal2);
		etkcal3 = (EditText) findViewById(R.id.etkcal3);
		etkcal4 = (EditText) findViewById(R.id.etkcal4);
		etgrami1 = (EditText) findViewById(R.id.etgrami1);
		etgrami2 = (EditText) findViewById(R.id.etgrami2);
		etgrami3 = (EditText) findViewById(R.id.etgrami3);
		etgrami4 = (EditText) findViewById(R.id.etgrami4);
		tv_ukupno = (TextView) findViewById(R.id.tv_ukupno);
		bZbroji = (Button) findViewById(R.id.bZbroji);
		bPonisti = (Button) findViewById(R.id.bPonisti);
		bSpremiubazu = (Button) findViewById(R.id.bSpremiubazu);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

        //Button click events
		switch (v.getId()) {
		case R.id.bZbroji:

			if (etkcal1.getText().length() == 0
					|| etkcal1.getText().length() == 0
					|| etkcal2.getText().length() == 0
					|| etkcal2.getText().length() == 0
					|| etkcal3.getText().length() == 0
					|| etkcal3.getText().length() == 0
					|| etkcal4.getText().length() == 0
					|| etkcal4.getText().length() == 0
					|| etgrami1.getText().length() == 0
					|| etgrami1.getText().length() == 0
					|| etgrami2.getText().length() == 0
					|| etgrami2.getText().length() == 0
					|| etgrami3.getText().length() == 0
					|| etgrami3.getText().length() == 0
					|| etgrami4.getText().length() == 0
					|| etgrami4.getText().length() == 0) {
				Toast.makeText(this, "Nisu popunjena sva polja",
						Toast.LENGTH_SHORT).show();
			} else {
				dodaj();
			}
			break;
		case R.id.bPonisti:

			etkcal1.setText("0");
			etkcal2.setText("0");
			etkcal3.setText("0");
			etkcal4.setText("0");
			etgrami1.setText("0");
			etgrami2.setText("0");
			etgrami3.setText("0");
			etgrami4.setText("0");
			spinner1.setSelection(0);
			spinner2.setSelection(0);
			spinner3.setSelection(0);
			spinner4.setSelection(0);
			tv_ukupno.setText(null);
			
			break;
		case R.id.bSpremiubazu:

			boolean diditwork = true;

			try {
				String ukcal = ukupno.toString();
				String date = new SimpleDateFormat("dd-MM-yyyy HH:mm")
						.format(new Date());

				BazaUnosa entry = new BazaUnosa(this);
				entry.open();

				entry.createEntry(ukcal, date);

				entry.close();
			} catch (Exception e) {
				diditwork = false;
				Dialog d = new Dialog(this);
				d.setTitle("Upss!");
				TextView tv = new TextView(this);
				tv.setText("Nema podatka za pohranu");
				d.setContentView(tv);
				d.show();

			} finally {
				if (diditwork) {
					Toast.makeText(this, "Uspje�no pohranjeno",
							Toast.LENGTH_LONG).show();
				}
			}

			break;

		}

	}

	private void dodaj() {
		// TODO Auto-generated method stub
		xk = Double.parseDouble(etkcal1.getText().toString());
		yk = Double.parseDouble(etkcal2.getText().toString());
		zk = Double.parseDouble(etkcal3.getText().toString());
		mk = Double.parseDouble(etkcal4.getText().toString());
		xg = Double.parseDouble(etgrami1.getText().toString());
		yg = Double.parseDouble(etgrami2.getText().toString());
		zg = Double.parseDouble(etgrami3.getText().toString());
		mg = Double.parseDouble(etgrami4.getText().toString());

		n = (xk * (xg / 100)) + (yk * (yg / 100)) + (zk * (zg / 100))
				+ (mk * (mg / 100));
		n = Math.round(n*100.0)/100.0;
		ukupno = String.valueOf(n);
		tv_ukupno.setText(" " + ukupno);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
        //selecting from dropdown menus
		switch (parent.getId()) {
		case R.id.spinner1:
			namirnice = getResources().getStringArray(R.array.namirnice_kcal)[spinner1
					.getSelectedItemPosition()];
			etkcal1.setText(namirnice, TextView.BufferType.EDITABLE);
			break;
		case R.id.spinner2:
			namirnice = getResources().getStringArray(R.array.namirnice_kcal)[spinner2
					.getSelectedItemPosition()];
			etkcal2.setText(namirnice, TextView.BufferType.EDITABLE);
			break;
		case R.id.spinner3:
			namirnice = getResources().getStringArray(R.array.namirnice_kcal)[spinner3
					.getSelectedItemPosition()];
			etkcal3.setText(namirnice, TextView.BufferType.EDITABLE);
			break;
		case R.id.spinner4:
			namirnice = getResources().getStringArray(R.array.namirnice_kcal)[spinner4
					.getSelectedItemPosition()];
			etkcal4.setText(namirnice, TextView.BufferType.EDITABLE);
			break;

		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

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
			Intent intent = new Intent(UnosKalorija.this, Pregled.class);
			startActivity(intent);
			break;
		case R.id.dnevnik_unosa:
			Intent intent1 = new Intent(UnosKalorija.this, PregledUnosa.class);
			startActivity(intent1);
			break;
		case R.id.stoperica:
			Intent intent2 = new Intent(UnosKalorija.this, Stoperica.class);
			startActivity(intent2);
			break;
		case R.id.aboutApp:
			Intent intent3 = new Intent ("android.intent.action.ABOUT");
			startActivity(intent3);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}