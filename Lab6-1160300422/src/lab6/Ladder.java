package lab6;

import java.util.ArrayList;
import java.util.List;

public class Ladder {
	private int order;
	private String direction = "2";
	private int monkeyNum = 0;
	private int h = initialization.getInitialization().h;// the number of pedals
	private Monkey[] monkeys = new Monkey[h + 1];

	/*
	 * all fields are private Thread safety argument: all method which can visit or
	 * modify the ladder was locked
	 */

	public synchronized int getOrder() {
		return order;
	}

	public synchronized String getDirection() {
		return direction;
	}

	public synchronized int getMonkeyNum() {
		return monkeyNum;
	}

	public synchronized void setDirection(String direction) {
		this.direction = direction;
	}

	public Ladder(int order) {
		this.order = order;
	}

	public static Ladder ladderFactory(int order) {

		Ladder ladder = new Ladder(order);
		return ladder;
	}

	public synchronized boolean forward(Monkey monkey) {
		if (monkey.destination > h) {
			monkeys[monkey.position] = null;
			monkeyNum--;
			initialization.getInitialization().deleteMonkey(monkey);
			if (monkeyNum == 0) {
				direction = "2";
			}
		} else if (monkey.destination == 1) {
			monkeys[monkey.destination] = monkey;
			monkeyNum++;
		} else {
			monkeys[monkey.position] = null;
			monkeys[monkey.destination] = monkey;
		}
		return true;
	}

	public synchronized void addMonkey(Monkey monkey) {
		monkeys[1] = monkey;
		monkeyNum++;
	}


	/**
	 * 
	 * @param position
	 * @param destination
	 * @return true while allow forward
	 */
	public boolean allowForward(int position, int destination) {
		if (destination > h) {
			return true;
		}
		boolean allow = true;
		for (int i = position; i < destination; i++) {
			if (monkeys[++i] != null) {
				allow = false;
			}
		}
		return allow;
	}
}
