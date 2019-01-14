package factory.GraphFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class IO2 implements IOStrategy {

	@Override
	public List<String> IGraph(String filePath) {
		List<String> str = new ArrayList<String>();
		InputStream in;
		try {
			in = new FileInputStream(filePath);
			byte[] bytes = new byte[2048];
			int n = -1;
			StringBuffer tempString = new StringBuffer();
			while ((n = in.read(bytes, 0, bytes.length)) != -1) {
				int offset = 0;
				int length;
				int i = 0;
				for (i = 0; i < n; i++) {
					if (bytes[i] == '\n') {
						length = i - offset;
						String s = new String(bytes, offset, length, "GBK");
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
						String s = new String(bytes, offset, length, "GBK");
						str.add(s);
						offset = i + 1;
					}
				}
				if (offset < n) {
					length = n - offset;
					tempString = new StringBuffer(new String(bytes, offset, length, "GBK"));
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
		byte b[] = s.getBytes();
		OutputStream out;
		try {
			out = new FileOutputStream(filePath);
			out.write(b);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
