package vertex;

public abstract class Vertex {
	private String label;

	/**
	 * add extra properties for vertex
	 * 
	 * @param args
	 *            extra attributes of vertex
	 */
	public abstract void fillVertexInfo(String[] args);

	/**
	 * constructor
	 * 
	 * @param label
	 */
	public Vertex(String label) {
		this.label = label;
	}

	/**
	 * observer
	 * 
	 * @return the label of this vertex
	 */
	public String getLabel() {
		return this.label;
	}
	
	public void modifyLabel(String l) {
		label = new String(l);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Vertex v = (Vertex) o;
		return this.label != null && this.label.equals(v.getLabel());
	}

	@Override
	public int hashCode() {
		return this.label.hashCode();
	}

}
