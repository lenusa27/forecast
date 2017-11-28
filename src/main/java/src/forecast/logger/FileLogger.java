package src.forecast.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger {
	
	private static final String INPUT = "input";
	private static final String OUTPUT = "output";
	
	private static Logger inputLogger = Logger.getLogger(INPUT);
	private static Logger outputLogger = Logger.getLogger(OUTPUT);
	
	static {
		try {
			setupLogger(inputLogger, INPUT + ".txt");
			setupLogger(outputLogger, OUTPUT + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void setupLogger(Logger logger, String fileName) throws IOException {
		FileHandler fileHandler = new FileHandler(fileName, true);
		fileHandler.setFormatter(new LoggerFormatter());
		
		logger.setLevel(Level.INFO);
		logger.addHandler(fileHandler);
	}
	
	public static void input(String info) {
		inputLogger.info(info);
	}
	
	public static void output(String info) {
		outputLogger.info(info);
	}
	
	
	
}
