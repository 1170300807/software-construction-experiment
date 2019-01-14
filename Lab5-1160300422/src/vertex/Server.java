package vertex;

public class Server extends Vertex {

	String IP;

	// Abstraction function:
	// a computer has an attribute--IP besides label
	// so class Server add a field to describe it
	//
	// Representation invariant:
	// IP must be a string which can be divided into four parts by ".",every part is
	// a integer belongs [0,255]
	//
	// Safety from rep exposure:
	// All fields are private

	public Server(String label) {
		super(label);
	}

	/**
	 * observer
	 * 
	 * @return this.IP
	 */
	public String getIP() {
		return this.IP;
	}

	private void checkRep() {
		String[] splitIP = this.IP.split("\\.");
		assert splitIP.length == 4;
		for (String str : splitIP) {
			try {
				int now = Integer.valueOf(str);
				assert now >= 0 && now <= 255;
			} catch (NumberFormatException e) {
				assert false;
			}
		}
	}

	@Override
	public void fillVertexInfo(String[] args) {
		String[] splitIP = args[0].split("\\.");
		assert splitIP.length == 4;
		for (String str : splitIP) {
			try {
				int now = Integer.valueOf(str);
				assert now >= 0 && now <= 255;
			} catch (NumberFormatException e) {
				assert false;
			}
		}
		this.IP = args[0];
		checkRep();
	}

	@Override
	public String toString() {
		return "<\"" + super.getLabel() + "\", \"Server\", <" + "\"" + IP + "\">>";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Server v = (Server) o;
		return super.getLabel() != null && this.IP != null && super.getLabel().equals(v.getLabel())  && this.IP.equals(v.getIP());
	}

	@Override
	public int hashCode() {
		return super.getLabel().hashCode();
	}
}
