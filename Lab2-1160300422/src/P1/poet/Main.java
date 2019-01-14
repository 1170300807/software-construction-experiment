/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
/**
 * Example program using GraphPoet.
 * 
 * <p>
 * PS2 instructions: you are free to change this example class.
 */
public class Main {

	/**
	 * Generate example poetry.
	 * 
	 * @param args
	 *            unused
	 * @throws IOException
	 *             if a poet corpus file cannot be found or read
	 */
	public static void main(String[] args) throws IOException {
		final GraphPoet nimoy = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
		final String input = "Test the system.";
		System.out.println(input + "\n>>>\n" + nimoy.poem(input));

		final GraphPoet poet = new GraphPoet(new File("src/P1/poet/徐志摩.txt"));
		final GraphPoet poet2 = new GraphPoet(new File("src/P1/poet/海子.txt"));
		String in1 = new String("我 要 喝 可 乐\n" + "不 冰 不 行\n" + "我 要 喝 可 乐\n" + "不 胖 不 行");
		String in2 = new String("我\n" + "一 个 人\n" + "来 自 北 方\n" + "不 是 华 北 的 北\n" + "是 东 北 的 北");
		String in3 = new String("我 室 友\n" + "五 个 胖 子\n" + "我 最 瘦");
		String in4 = new String("规 格 严 格 不 让 及 格\n" + "功 夫 到 家 不 让 回 家");
		String in5 = new String("故 事 的 小 黄 花 \n" + "从 出 生 那 年 就 飘 着 \n" + "童 年 的 荡 秋 千 \n" + "随 记 忆 一 直 晃 到 现 在 \n");
		String in6 = new String(" 不 知 道 不 明 了 不 想 要 为 什 么 我 的 心   \n" + " 那 爱 情 的 绮 丽   \n" + " 总 是 在 孤 单 里   \n"
				+ " 再 把 我 的 最 好 的 爱 给 你 ");
		String in7 = new String(" 虽 然 我 语 文 不 好 \n" + " 但 \n" + " 毫 无 疑 问 \n" + " 我 是 一 个 诗 人 ");
		System.out.println(poet2.poem(in1) + "\n");
		System.out.println(poet.poem(in1) + "\n");
		System.out.println(poet2.poem(in2) + "\n");
		System.out.println(poet.poem(in2) + "\n");
		System.out.println(poet2.poem(in3) + "\n");
		System.out.println(poet.poem(in3) + "\n");
		System.out.println(poet2.poem(in4) + "\n");
		System.out.println(poet.poem(in4) + "\n");
		System.out.println(poet2.poem(in5) + "\n");
		System.out.println(poet.poem(in5) + "\n");
		System.out.println(poet2.poem(in6) + "\n");
		System.out.println(poet.poem(in6) + "\n");
		System.out.println(poet2.poem(in7) + "\n");
		System.out.println(poet.poem(in7) + "\n");
	}

}
