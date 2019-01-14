package factory.GraphFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IO3 implements IOStrategy {

	@Override
	public List<String> IGraph(String filePath) {
		List<String> str = new ArrayList<String>();
		String tempString = null;
		try {
			File file = new File(filePath);
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				tempString = sc.nextLine();
				str.add(tempString);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public void OGraph(String filePath, String s) {
		try {
			File file = new File(filePath);
			PrintWriter pw = new PrintWriter(file);
			pw.write(s);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
