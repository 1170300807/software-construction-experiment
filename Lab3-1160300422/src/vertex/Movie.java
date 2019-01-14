package vertex;

import java.util.regex.Pattern;

public class Movie extends Vertex {

	private int date;
	private String country;
	private double IMDb;
	// Abstraction function:
	// a computer has three attributes--date,country,IMDb besides label
	// so class Movie add three fileds to describe it
	//
	// Representation invariant:
	// data must be an integer belonging to [1900,2018]
	// country is valid
	// IMDb belongs to [0,10] and it keeps up to 2 decimal places
	//
	// Safety from rep exposure:
	// All fields are private

	public Movie(String label) {
		super(label);
	}

	/**
	 * observer
	 */
	public int getDate() {
		return this.date;
	}

	public String getCountry() {
		return new String(this.country);
	}

	public double getIMDb() {
		return this.IMDb;
	}

	public void checkRep() {
		assert date >= 1900 && date <= 2018;
		assert IMDb >= 0 && IMDb <= 10;
		String imdb = String.valueOf(IMDb);
		Pattern p = Pattern.compile("[0-9]+(.[0-9]{1,2})?\\b");
		assert p.matcher(imdb).matches();
	}

	@Override
	public void fillVertexInfo(String[] args) {
		this.date = Integer.valueOf(args[0]);
		this.country = new String(args[1]);
		this.IMDb = Double.valueOf(args[2]);
		checkRep();
	}

	@Override
	public String toString() {
		return "<\"" + super.getLabel() + "\", \"Movie\", <" + "\"" + date + "\", \"" + country + "\", \"" + IMDb
				+ "\">>";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Movie v = (Movie) o;
		return super.getLabel().equals(v.getLabel()) && this.date == v.getDate() && this.country.equals(v.getCountry())
				&& this.IMDb == v.getIMDb();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
