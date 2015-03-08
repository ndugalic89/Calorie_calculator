package nidjo.fitnesskalkulatorkalorija;



public class ActivityRecords {

	private int id;
	private String tipVjezbe;
	private String kilokalorije;
	private String dzuli;
	private String date;
	private String ukcal;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipvjezbe() {
		return this.tipVjezbe;
	}

	public void setTipvjezbe(String tipVjezbe) {
		this.tipVjezbe = tipVjezbe;
	}
	public String getKilokalorije() {
		return this.kilokalorije;
	}

	public void setKilokalorije(String kilokalorije) {
		this.kilokalorije = kilokalorije;
	}
	public String getDzuli() {
		return this.dzuli;
	}

	public void setDzuli(String dzuli) {
		this.dzuli = dzuli;
	}
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return this.tipVjezbe;
	}
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUkcal() {
		// TODO Auto-generated method stub
		return this.ukcal;
	}

	public void setUkcal(String ukcal) {
		this.ukcal = ukcal;
	}

}
