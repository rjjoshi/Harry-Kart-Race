package se.atg.harrykart.test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {
	public static String readXMLFileToString(String filePath) throws IOException {
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		return new String(fileBytes, StandardCharsets.UTF_8);
	}

	public static String expectedOutput(String... horses) {
		StringBuilder sb = new StringBuilder();
		int position = 0;
		sb.append("{ \"ranking\": [");
		for (String horse : horses) {
			sb.append("{\"position\": " + ++position + ", \"horse\": \"" + horse + "\"}");
			if (position <= horses.length - 1) {
				sb.append(",");
			}
		}
		sb.append("] }");
		return sb.toString();
	}

}
