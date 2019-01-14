package lab6;

public class MonkeyGenerator {

	private initialization s = initialization.getInitialization();
	private int N = s.N;
	private int t = s.t;
	private int k = s.k;
	private int MV = s.MV;
	private int order = 0;

	private String randomDirection() {
		String d = Math.random() > 0.5 ? "0" : "1";
		return d;
	}

	private int randomSpeed() {
		int s = (int) (Math.random() * (MV - 1) + 1);
		//int s = Math.random()>0.5? 1:MV;
		return s;
	}

	public void genearate(int time) {
		if (order < s.N) {
			if (order == s.N - N % k) {
				for (int j = 0; j < N % k; j++) {
					Monkey m = Monkey.monkeyFactory(++order, randomDirection(), randomSpeed(), time);
					s.addMonkey(m);
				}
			} else {
				for (int j = 0; j < k; j++) {
					Monkey m = Monkey.monkeyFactory(++order, randomDirection(), randomSpeed(), time);
					s.addMonkey(m);
				}

			}
		}
	}

}
