import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Buffer {
	private volatile static Buffer buffer;
	private String url;
	private StringBuffer content;
	private int step;

	private Buffer(String url) {
		this.url = url;
		content = new StringBuffer();
		step = 0;
	}

	public static Buffer getInstance(String url) {
		if (buffer == null) {
			synchronized (Buffer.class) {
				if (buffer == null) {
					buffer = new Buffer(url);
				}
			}
		}
		return buffer;
	}

	public int getStep() {
		return step;
	}

	public void copyInBuffer() {
		String line;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(this.url));
			try {
				while ((line = br.readLine()) != null) {
					content.append(line + " ");
				}
				step = 1;
			} catch (IOException e) {
			} finally {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		} catch (FileNotFoundException e1) {
			System.out.println("File not found");
		}

	}

	public String findUpperLetters() {
		StringBuffer uppers = new StringBuffer();
		Pattern patternU = Pattern.compile("[A-Z]");
		Matcher matcherU = patternU.matcher(content);
		while (matcherU.find()) {
			uppers.append(matcherU.group());
		}
		step = 2;
		return String.valueOf(uppers);
	}

	public String findLowerLetters() {
		StringBuffer lowers = new StringBuffer();
		Pattern patternL = Pattern.compile("[a-z]");
		Matcher matcherL = patternL.matcher(content);
		while (matcherL.find()) {
			lowers.append(matcherL.group());
		}
		step = 3;
		return String.valueOf(lowers);
	}

	public String findDigitsAndSymbols() {

		StringBuffer nonLetter = new StringBuffer();
		Pattern patternNL = Pattern.compile("[^a-zA-Z\\s]");
		Matcher matcherNL = patternNL.matcher(content);
		while (matcherNL.find()) {
			nonLetter.append(matcherNL.group());
		}
		step = 4;
		return String.valueOf(nonLetter);
	}

	public void writeToFile(String newLine) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(this.url, true);
			fw.append('\n' + newLine + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
			}
		}
	}
}
