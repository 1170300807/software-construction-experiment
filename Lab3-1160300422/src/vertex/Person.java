package vertex;

public class Person extends Vertex {

	private String sex;
	private int age = -1;
	// Abstraction function:
	// a Person has two attributes--sex and age besides label(name)
	// so class Person add two fields to describe a person
	//
	// Representation invariant:
	// sex belongs {"M","F"}
	// age must be a positive number
	//
	// Safety from rep exposure:
	// All fields are private

	public Person(String label) {
		super(label);
	}

	/**
	 * observer of sex
	 * 
	 * @return this.sex
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * observer of age
	 * 
	 * @return this.age
	 */
	public int getAge() {
		return this.age;
	}

	private void checkRep() {
		assert this.age > 0;
		assert this.sex.equals("M")  || this.sex.equals("F") ;
	}

	@Override
	public void fillVertexInfo(String[] args) {
		
		try {
		this.age = Integer.valueOf(args[1]);
		}catch(NumberFormatException e) {
			System.out.println("invalid input");
			System.exit(0);
		}
		this.sex = args[0];
		checkRep();
	}

	@Override
	public String toString() {
		return "<\"" + super.getLabel() + "\", \"Person\", <" + "\"" + sex + "\", \"" + String.valueOf(age) + "\">>";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Person v = (Person) o;
		return  super.getLabel().equals(v.getLabel())
				&& this.sex.equals(v.getSex()) && this.age == v.getAge();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
