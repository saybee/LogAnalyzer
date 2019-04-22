import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CDNGenerateData {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		generate();
	}
   /**
    * generate() - Method creates data for last 12 hours based on the specified requirements
    * @throws IOException
    */
	public static void generate() throws IOException {
		File outputFile = new File(
				"F:/MSSE/Interview/CDNetworks/outputfile.txt");// Enter Full path name with file.txt where you need to generate data
		Writer fos = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputFile), "UTF-8"));
		long current = Instant.now().getEpochSecond();
		int start = (int) current - 86400;// To get data for last 12 hrs from current time
		for (int i = 0; i < 1000; i++) {
			generateIPaddress();
		}
		try {
			while (start <= current) {
				for (int i = 0; i < 1000; i++) {
					String generatedIP = getgenerateIPaddress();
					fos.write(String.valueOf(start) + " " + generatedIP + " "
							+ 0 + " " + generateUsage() + "\n");
					fos.write(String.valueOf(start) + " " + generatedIP + " "
							+ 1 + " " + generateUsage() + "\n");
				}
				start = start + 60;
			}
		} catch (FileNotFoundException e) {
			System.err.println("FileStreamsTest: " + e);
		} catch (IOException e) {
			System.err.println("FileStreamsTest: " + e);
		} finally {
			fos.close();
		}
	}

	public static List<String> generatedIP = new ArrayList<String>();// Maintains 1000 IP addresses
	public static Random r = new Random();
	
    /**
     * generateIPaddress - creates Random IP addresses.
     *                     Can be changed to specified requirement.
     */
	public static void generateIPaddress() {
		generatedIP.add(r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256)
				+ "." + r.nextInt(256));
	}
	
	/**
	 * Returns random IPaddresses from pool of 1000 generated IPs
	 * @return
	 */
	public static String getgenerateIPaddress() {
		return generatedIP.get(r.nextInt(generatedIP.size()));
	}
	
	/**
	 * Return random Usage
	 * @return
	 */
	public static int generateUsage() {
		return r.nextInt(100);
	}

}
