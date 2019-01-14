
/**
 * Person consists of methods that get the person's name.
 */
public class Person {
	private String name;

	public Person(String Name) {
		this.name = Name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
