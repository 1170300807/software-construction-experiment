package lab6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class simulator {
	public int time = 0;

	public void simulate(int n, int h, int t, int N, int k, int MV) {
		Log log = new Log();
		ExecutorService pool;
		initialization s = initialization.getInitialization(n, h, t, N, k, MV);
		s.generateLadders();
		MonkeyGenerator mg = new MonkeyGenerator();
		pool = Executors.newCachedThreadPool();
		pool.shutdown();
		while (true) {
			if (pool.isTerminated()) {
				pool = Executors.newCachedThreadPool();
				log.L(time);
				if (s.acrossNum == s.N) {
					break;
				} else if (time % s.t == 0) {
					mg.genearate(time);
				}
				for (Monkey monkey : s.getLeftMonkeys()) {
					pool.execute(monkey.new monkeyThread());
				}
				pool.shutdown();
				time++;
			}
		}
	}

	public double F() {
		initialization s = initialization.getInitialization();
		double result = 0;
		int now = 0;
		for (Monkey a : s.getMonkeys()) {
			for (Monkey b : s.getMonkeys()) {
				if (a != b) {
					if ((a.birth - b.birth) * (a.birth + a.hp - b.birth - b.hp) >= 0) {
						now = 1;
					} else {
						now = -1;
					}
					result += now;
				}
			}
		}
		return 1.0 * result / s.N / (s.N - 1);
	}

	public double Th() {
		double Th = 1.0 * initialization.getInitialization().N / time;
		return Th;
	}
}
