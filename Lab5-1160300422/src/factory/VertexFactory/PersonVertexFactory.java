package factory.VertexFactory;

import vertex.Person;
import vertex.Vertex;

public class PersonVertexFactory extends VertexFactory {

	@Override
	public Person createVertex(String label, String[] args) {
		Person person = new Person(label);
		person.fillVertexInfo(args);
		return person;
	}

}
