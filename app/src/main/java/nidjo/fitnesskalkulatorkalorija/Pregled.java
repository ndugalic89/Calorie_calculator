package nidjo.fitnesskalkulatorkalorija;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Pregled extends ActionBarActivity {
	
	private ActivityRecords selected_activity;
	private List<ActivityRecords> tmp_activity;
	private List<RadioButton> all_radiobutton;
	private BazaVjezbi myDatabase;
	
	public void initialize(){
        this.myDatabase = new BazaVjezbi(this);
		this.myDatabase.open();
		this.all_radiobutton = new ArrayList<RadioButton>();
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dnevnik_main);
		initialize();
		show_list_layout();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		
	}
	
	public void btn_del_click(View v){
		if(this.selected_activity != null){
			myDatabase.deleteActivity(this.selected_activity);
			show_mesg(this.selected_activity.getTipvjezbe() + " obrisano.");
			selected_activity = null;
			show_list_layout();
		}else{
			show_mesg("Odaberite unos koji �elite brisati.");
		}
	}

	public class select_item_click implements OnClickListener{
		private RadioButton rdb_select_item;
		private ActivityRecords aktivnost;
		select_item_click(RadioButton rdb_select_item,ActivityRecords activity){
			this.rdb_select_item = rdb_select_item;
			this.aktivnost = activity;
		}
		@Override
		public void onClick(View v) {
			for (int i = 0 ; i< all_radiobutton.size();i++){
				RadioButton rdb_select_item = all_radiobutton.get(i);
				rdb_select_item.setChecked(false);
			}
			selected_activity = this.aktivnost;
			this.rdb_select_item.setChecked(true);
		}
	
	}
	
	
	public void show_mesg(String msg){
		Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
	}

	public void show_list_layout(){

		this.tmp_activity = myDatabase.getData();
		this.all_radiobutton.clear();
		ListView showlist = (ListView) findViewById(R.id.ListAktivnosti);
		showlist.removeAllViewsInLayout();
        showlist.setAdapter(new ActivityItemAdapter(this, android.R.layout.simple_list_item_checked, this.tmp_activity));
	}
	
	public class ActivityItemAdapter extends ArrayAdapter<ActivityRecords> {
		private List<ActivityRecords> activity;
		public ActivityItemAdapter(Context context, int textViewResourceId, List<ActivityRecords> activities) {
			super(context, textViewResourceId, activities);
			this.activity = activities;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ActivityRecords aktivnost = activity.get(position);
			if (aktivnost != null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.inflated_layout, null);
		
				RadioButton rdb_select_item =(RadioButton) view.findViewById(R.id.rdb_select_item);
				LinearLayout layout_item = (LinearLayout) view.findViewById(R.id.layout_item);			
				TextView vjezba = (TextView) view.findViewById(R.id.vjezba);
				TextView kcal = (TextView) view.findViewById(R.id.kilokalorije);
				TextView dzuli = (TextView) view.findViewById(R.id.dzuli);
				TextView datum = (TextView) view.findViewById(R.id.datum);
				
				vjezba.setText(aktivnost.getTipvjezbe());
				kcal.setText("kCal: " + aktivnost.getKilokalorije() );
				dzuli.setText("J: " + aktivnost.getDzuli());
				datum.setText("Datum: " + aktivnost.getDate());
				
				if(selected_activity != null){
					if (selected_activity.getId() == aktivnost.getId()) rdb_select_item.setChecked(true);
				}
				
				all_radiobutton.add(rdb_select_item);
				rdb_select_item.setOnClickListener(new select_item_click(rdb_select_item,aktivnost));
				layout_item.setOnClickListener(new select_item_click(rdb_select_item,aktivnost));
			}
			return view;
		}
	}

	
	@Override
	protected void onResume() {
		myDatabase.open();
		super.onResume();
	}
	@Override
	protected void onPause() {
		myDatabase.close();
		super.onPause();
	}
}
