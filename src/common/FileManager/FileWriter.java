package common.FileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * 
 * @author Darkguardsman
 * txt righter sourced from http://www.javapractices.com/topic/TopicAction.do?Id=42
 *
 */
public class FileWriter {
	final static String FILE_NAME = "C:\\Temp\\input.txt";
	  final static String OUTPUT_FILE_NAME = "C:\\Temp\\output.txt";
	  final static Charset ENCODING = StandardCharsets.UTF_8;
	  
	  //For smaller files
	  
	 public static List<String> readSmallTextFile(String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    return Files.readAllLines(path, ENCODING);
	  }
	  
	  void writeSmallTextFile(List<String> aLines, String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    Files.write(path, aLines, ENCODING);
	  }
	  private static void log(Object aMsg){
	    System.out.println(String.valueOf(aMsg));
	  }
}
