package lab6;

import org.apache.log4j.Logger;

public class Log {
	static Logger log = Logger.getLogger("monkeys");

	public void L(int time) {
		initialization s = initialization.getInitialization();

		StringBuffer sb = new StringBuffer();
		for (Monkey m : s.getMonkeys()) {
			sb.append("" + time + "\t" + "monkey" + m.order + "\t" + "ladder" + m.ladderOrder() + "\t"
					+ m.direction + "\t" + m.position + "\t" + m.hp + "\n");
		}
		log.info(sb.toString());

	}
}
