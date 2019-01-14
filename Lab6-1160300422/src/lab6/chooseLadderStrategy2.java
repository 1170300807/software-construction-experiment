package lab6;

public class chooseLadderStrategy2 implements chooseLadder {
	/*
	 * return ladder whose monkeyNum is 0,otherwise waiting
	 */
	initialization s = initialization.getInitialization();
	static chooseLadderStrategy2 c = null;

	public static chooseLadderStrategy2 getChoose() {
		if(c == null) {
			c = new chooseLadderStrategy2();
		}
		return c;
	}
	
	
	@Override
	public synchronized Ladder chooseLader(Monkey monkey, String direction) {
		Ladder ladder = null;
		for (Ladder l : s.getLadders()) {
			
				if (l.getMonkeyNum() == 0) {
					ladder = l;
					ladder.setDirection(direction);
					ladder.addMonkey(monkey);
					break;
				}
			}
		return ladder;
	}

}
