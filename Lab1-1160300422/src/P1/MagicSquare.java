
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MagicSquare {
	/**
	 * generate a magic square.
	 * 
	 * @param n
	 *            the square's length
	 */
	public static boolean generateMagicSquare(int n) {
		if (n % 2 == 0 || n < 0)
			return false;
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)
				row++;
			else {
				if (row == 0)
					row = n - 1;
				else
					row--;
				if (col == (n - 1))
					col = 0;
				else
					col++;
			}
		}
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
			System.out.println();
		}
		try {
			FileWriter writer = new FileWriter("src/P1/txt/6.txt", false);
			for (int a = 0; a < n; a++) {
				for (int b = 0; b < n - 1; b++) {
					writer.write(String.format("%d\t", magic[a][b]));
				}
				writer.write(String.format("%d\n", magic[a][n - 1]));
			}			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Determine whether the string is an integer.
	 * @param  str
	 * 			any string.
	 */
	public static boolean isNumer(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * Determine whether the square is a legal magic square.
	 * 
	 * @param fileName
	 * 					a file.
	 * @return
	 * 			true or false
	 */			
	public boolean isLegalMagicSquare(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			int row = 0;
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			tempString = reader.readLine();//读文件的第一行
			String[] a = tempString.split("\t");
			int N = a.length;//确定第一行有多少数字，生成二维数组存储矩阵
			int[][] square = new int[N][N];
			for (int i = 0; i < N; i++) {
				if (isNumer(a[i])) {//对每个读取到的string，判断它是不是一个整数
					square[row][i] = Integer.valueOf(a[i]);
				} else
					return false;
			}
			while ((tempString = reader.readLine()) != null) {//依次读取文件的接下来部分
				row++;
				String[] b = tempString.split("\t");
				if (b.length != N)//如果这个条件不满足则不是一个矩阵
					return false;
				for (int i = 0; i < N; i++) {
					if (isNumer(b[i])) {
						square[row][i] = Integer.valueOf(b[i]);
					} else
						return false;
				}
			}
			reader.close();
			if (row != N - 1)//如果行数和列数不等则不可能是magic square
				return false;
			if (!isEqualSquare(square, N))//判断是否满足每行每列每个对角线的和都相等
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;//执行到这一步说明说明它是一个magic square
	}
/**
 * Determine the square whether is equal square
 * @param square
 * 				A two dimensional array.
 * @param N
 * 				the length of array.
 * @return
 * 			true or false
 */			
	public boolean isEqualSquare(int[][] square, int N) {
		int row = 0, trow = 0, tcol;
		int tdia = 0, tdiaR = 0;
		for (int i = 0; i < N; i++) {
			row += square[0][i];
		}
		for (int i = 1; i < N; i++) {
			trow = 0;
			tcol = 0;
			for (int j = 0; j < N; j++) {
				trow += square[i][j];
			}
			if (trow != row)
				return false;
			for (int j = 0; j < N; j++) {
				tcol += square[j][i];
			}
			if (tcol != row)
				return false;
		}
		for (int i = 0; i < N; i++) {
			tdia += square[i][i];
			tdiaR += square[i][N - 1 - i];
		}
		if (tdia != row || tdiaR != row) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		MagicSquare s = new MagicSquare();
		for (int i = 1; i <= 5; i++) {
			if (s.isLegalMagicSquare("src/P1/txt/" + i + ".txt")) {
				System.out.println("true");
			} else {
				System.out.println("false");
			}
		}
		if (generateMagicSquare(3)) {
			if (s.isLegalMagicSquare("src/P1/txt/6.txt")) {
				System.out.println("true");
			} else {
				System.out.println("false");
			}
		}
	}
}
