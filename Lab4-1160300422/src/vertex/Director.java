package vertex;

public class Director extends Vertex {

	private int age;
	private String Sex;
	// Abstraction function:
	// an Director has two attributes--label,age and sex
	// so class Director extends Vertex and add two fields		
	//
	// Representation invariant:
	// the label should be a word
	// age should be an positive number
	// sex belongs to {"M","F"}
	//
	// Safety from rep exposure:
	// All fields are private
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
		assert Integer.valueOf(args[0]) > 0;
		assert args[1].equals("M") || args[1].equals("F");
		this.age = Integer.valueOf(args[0]);
		this.Sex = new String(args[1]);
		checkRep();
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
