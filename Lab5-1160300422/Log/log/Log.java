package log;

public class Log {
	public String time;
	public String message;

	public Log(String time, String message) {
		this.time = time;
		this.message = message;
	}
	
	public boolean after(String t) {
		return !before(t);
	}
	
	public boolean before(String t) {
		return this.time.compareTo(t)<0;
	}
}
