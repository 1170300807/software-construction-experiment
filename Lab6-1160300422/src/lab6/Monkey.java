package lab6;

import org.apache.log4j.Logger;

public class Monkey {
	public int order;
	public String direction;
	public int v;
	public int position = 0;// the monkey is on the position-th pedal,[1,h+1]
	public int destination = 0;// the monkey aim to the destination-th pedal
	private Ladder ladder = null;// the monkey is on the ladder,null denote it is waiting.
	public int birth;// birth time
	public int hp = 0;// hp seconds since born
	private initialization s = initialization.getInitialization();
	private chooseLadder strategy = chooseLadderStrategy2.getChoose();

	public Monkey(int order, String direction, int v, int birth) {
		this.order = order;
		this.direction = direction;
		this.v = v;
		this.birth = birth;
	}

	public static Monkey monkeyFactory(int order, String direction, int v, int birth) {
		Monkey m = new Monkey(order, direction, v, birth);
		return m;
	}

	public int ladderOrder() {
		if (ladder == null) {
			return -1;
		} else {
			return ladder.getOrder();
		}
	}

	public class monkeyThread implements Runnable {
		

		@Override
		public void run() {
			if (position <= s.h) {
								
				hp++;
				if (position == 0) {
					ladder = strategy.chooseLader(Monkey.this, direction);
					if (ladder != null) {
						destination++;
						position = destination;
					}

				} else if (position > 0 && position <= s.h) {
					do {
						destination++;
					} while (destination <= s.h + 1 && ladder.allowForward(position, destination)
							&& destination <= position + v);
					destination--;
					ladder.forward(Monkey.this);
					position = destination;

				}

				if (position == s.h + 1) {
					s.acrossNum++;
				}
			}

		}

	}
}
