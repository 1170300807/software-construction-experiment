package vertex;

public class Director extends Vertex {

	private int age;
	private String Sex;

	public Director(String label) {
		super(label);
	}

	/**
	 * observer
	 */
	public int getAge() {
		return age;
	}

	public String getSex() {
		return new String(Sex);
	}

	public void checkRep() {
		assert this.age > 0;
		assert this.Sex.equals("M") || this.Sex.equals("F");
	}

	@Override
	public void fillVertexInfo(String[] args) {
		this.age = Integer.valueOf(args[0]);
		this.Sex = new String(args[1]);
	}

	@Override
	public String toString() {
		return "<\"" + super.getLabel() + "\", \"Director\", <" + "\"" + age + "\", \"" + Sex + "\">>";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Director v = (Director) o;
		return super.getLabel().equals(v.getLabel()) && this.age == v.getAge() && this.Sex.equals(v.getSex());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
