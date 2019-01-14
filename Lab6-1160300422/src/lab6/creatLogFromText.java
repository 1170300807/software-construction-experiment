package lab6;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class creatLogFromText {
	public static List<String> Logs(String file) {
		List<String> logs = new ArrayList<String>();
		BufferedReader reader ;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if(!tempString.startsWith("2018-")) {
					logs.add(tempString);
				}
			}
			reader.close();
			return logs;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return logs;
		
	}
}
