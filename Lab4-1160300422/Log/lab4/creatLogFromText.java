package lab4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graph.NetworkTopology;

public class creatLogFromText {
	public static List<Log> Logs(String file) {
		List<Log> logs = new ArrayList<Log>();
		NetworkTopology g = new NetworkTopology();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			boolean tag = true;
			StringBuffer str = null;
			StringBuffer time = null;
			while ((tempString = reader.readLine()) != null) {
				tag = !tag;
				if(!tag) {
					str = new StringBuffer(tempString);
					time = new StringBuffer();
					for(int i = 0;i<19;i++) {
						time.append(str.charAt(i));
					}
				}else {
					str.append("\n").append(tempString);
					Log l = new Log(time.toString(),str.toString());
					logs.add(l); 
				}
			}
			return logs;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return logs;
		
	}
}
