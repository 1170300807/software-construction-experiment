package vertex;

public class Word extends Vertex {

	// Abstraction function:
	// a word only has one attribute--label,
	// so class Word extends Vertex and do nothing
	//
	// Representation invariant:
	// the label should be a word
	//
	// Safety from rep exposure:
	// All fields are private
	public Word(String label) {
		super(label);
	}

	@Override
	public void fillVertexInfo(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override 
	public String toString() {
		return "<\""+super.getLabel()+"\", \"Word\">";
	}
}
