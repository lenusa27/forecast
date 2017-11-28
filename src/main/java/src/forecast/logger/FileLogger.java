package src.forecast.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.time.LocalDateTime.now;

public class FileLogger {
	
	private static final String INPUT_FILE = "input.txt";
	
	public void input(String content) throws IOException {
		writeTo(INPUT_FILE, content);
	}
	
	public void output(String fileName, String content) throws IOException {
		writeTo(fileName, content);
	}
	
	private void writeTo(String fileName, String content) throws IOException {
		String formatted = format(content);
		Files.write(Paths.get(fileName), formatted.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}
	
	private String format(String info) {
		return now() + " - " + info + "\n";
	}
	
}
