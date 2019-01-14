package lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class initialization {
	private List<Monkey> monkeys = new ArrayList<Monkey>();
	private List<Ladder> ladders = new ArrayList<Ladder>();
	private List<Monkey> leftMonkeys = new ArrayList<Monkey>();
	public int acrossNum = 0;
	public int n;// the number of ladders is n
	public int h = 20;// the number of ladder's length is h
	public int t;// generate monkeys every t time
	public int N;// the number of monkeys is N
	public int k;// generate k monkeys once
	public int MV = 5;// the max speed of monkey is MV

	private initialization(int n, int h, int t, int N, int k, int MV) {
		this.n = n;
		this.h = h;
		this.t = t;
		this.N = N;
		this.k = k;
		this.MV = MV;
	}

	private static initialization single = null;

	// 静态工厂方法
	public static initialization getInitialization(int n, int h, int t, int N, int k, int MV) {
		single = new initialization(n, h, t, N, k, MV);
		return single;
	}

	public static initialization getInitialization() {
		return single;
	}

	public void generateLadders() {
		for (int i = 1; i <= n; i++) {
			ladders.add(Ladder.ladderFactory(i));
		}
	}

	public synchronized List<Monkey> getMonkeys() {
		List<Monkey> list = new ArrayList<Monkey>();
		for (Monkey m : monkeys) {
			list.add(m);
		}
		return list;
	}
	
	public synchronized List<Monkey> getLeftMonkeys() {
		List<Monkey> list = new ArrayList<Monkey>();
		for (Monkey m : leftMonkeys) {
			list.add(m);
		}
		return list;
	}

	public List<Ladder> getLadders() {
		List<Ladder> list = new ArrayList<Ladder>();
		for (Ladder l : ladders) {
			list.add(l);
		}
		return list;
	}

	public synchronized void addMonkey(Monkey monkey) {
		monkeys.add(monkey);
		leftMonkeys.add(monkey);
	}
	
	public void deleteMonkey(Monkey monkey) {
		leftMonkeys.remove(monkey);
	}
}
