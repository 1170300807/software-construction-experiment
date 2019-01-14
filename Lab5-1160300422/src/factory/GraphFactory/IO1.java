package factory.GraphFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class IO1 implements IOStrategy {

	@Override
	public List<String> IGraph(String filePath) {
		List<String> str = new ArrayList<String>();
		Reader in;
		try {
			in = new FileReader(filePath);
			char[] bytes = new char[2048];
			int n = -1;
			StringBuffer tempString = new StringBuffer();
			while ((n = in.read(bytes, 0, bytes.length)) != -1) {
				int offset = 0;
				int length;
				int i = 0;
				for (i = 0; i < n; i++) {
					if (bytes[i] == '\n') {
						length = i - offset;
						String s = new String(bytes, offset, length);
						tempString.append(s);
						str.add(tempString.toString());
						offset = i + 1;
						break;
					}

				}
				i++;
				for (; i < n; i++) {
					if (bytes[i] == '\n') {
						length = i - offset;
						String s = new String(bytes, offset, length);
						str.add(s);
						offset = i + 1;
					}
				}
				if (offset < n) {
					length = n - offset;
					tempString = new StringBuffer(new String(bytes, offset, length));
				} else {
					tempString = new StringBuffer();
				}
			}
			// 关闭流
			in.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public void OGraph(String filePath, String s) {
		Writer out;
		try {
			out = new FileWriter(filePath);
			out.write(s);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}