package lab6;

public class chooseLadderStrategy1 implements chooseLadder {

	/*
	 * return ladder whose monkeyNum is least
	 */
	initialization s = initialization.getInitialization();
	static chooseLadderStrategy1 c = null;

	public static chooseLadderStrategy1 getChoose() {
		if (c == null) {
			c = new chooseLadderStrategy1();
		}
		return c;
	}

	@Override
	public synchronized Ladder chooseLader(Monkey monkey, String direction) {
		Ladder ladder = null;
		int min = s.h;
		synchronized (s.getLadders()) {
			for (Ladder l : s.getLadders()) {
				if ((l.getDirection().equals("2") || l.getDirection().equals(direction)) && l.allowForward(0, 1)
						&& l.getMonkeyNum() < min) {
					min = l.getMonkeyNum();
					ladder = l;
				}

			}
			if (ladder != null) {
				ladder.setDirection(direction);
				ladder.addMonkey(monkey);
			}
		}
		return ladder;

	}

}
