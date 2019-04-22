import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class CDNQuery {

	/**
	 * @param args
	 */
	static Map<String, ArrayList<String>> table = new HashMap<String, ArrayList<String>>();
	static String filename = "F:/MSSE/Interview/CDNetworks/outputfile.txt";// Enter Full Path name where the generated file is located

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String IP, cpu, starttime, endtime;
		boolean startConsole = true;
		Console console = System.console(); // console to get Query Parameters
		System.out.println("Enter Query Parameters ");
		if (console == null) {
			System.err.println("No console!");
			System.exit(1);
		}

		while (startConsole)// waiting for the users input
		{
			String input = console.readLine();
			if ("exit".equals(input) || "EXIT".equals(input)) {
				startConsole = false;
			} else {
				String[] queryParams = input.split(" ");
				IP = queryParams[1];
				cpu = queryParams[2];
				starttime = queryParams[3] + " " + queryParams[4];
				endtime = queryParams[5] + " " + queryParams[6];

				if (!validateData(IP, cpu, starttime, endtime)) { // Check if Input Parameters are Valid
					System.out.println("Invalid Input!");
					continue;
				}
				long st = 0;
				long ed = 0;

				try {
					st = convertDatetoUnix(starttime);
					ed = convertDatetoUnix(endtime);
				} catch (ParseException e) {
					System.out.println("Error " + e.getMessage());
				}

				getDatafromFile(filename, IP, cpu, st, ed);
				try {
					printResult(IP, cpu);
				} catch (ParseException e) {
					System.out.println("Error " + e.getMessage());
				}
			}
		}
	}

	/**
	 * printResult - To print Result in specified format
	 * @param IP 
	 * @param cpu
	 * @throws ParseException
	 */
	public static void printResult(String IP, String cpu) throws ParseException {

		if (table == null || table.size() == 0) {
			System.out.println("No records, enter correct input again!");
			return;
		}
		System.out.println("CPU" + cpu + " usage on " + IP + ":");
		for (Map.Entry<String, ArrayList<String>> map : table.entrySet()) {
			String date = convertUnixtoDate(map.getKey());
			int sum = 0;
			for (String innerlist : map.getValue()) {
				sum = sum + Integer.parseInt(innerlist);
			}
			System.out.print("(" + date + ", " + sum + "%)");
			System.out.print(",");
		}
		System.out.println();
	}

	/**
	 * getDatafromFile - get data based on input parameters
	 * @param filename
	 * @param ip
	 * @param cpu
	 * @param starttime
	 * @param endtime
	 * @throws IOException
	 */
	public static void getDatafromFile(String filename, String ip, String cpu,
			long starttime, long endtime) throws IOException {
		String[] lineData = new String[4];
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		try {
			String line = null;
			int linenumber = 0;

			while ((line = reader.readLine()) != null) {

				lineData = line.split(" ");
				if (Integer.valueOf(lineData[0]) >= starttime
						&& Integer.valueOf(lineData[0]) <= endtime) {
					if (table.containsKey(lineData[0])
							&& ip.equals(lineData[1])
							&& cpu.equals(lineData[2])) {
						ArrayList<String> list = table.get(lineData[0]);
						list.add(lineData[3]);
						table.put(lineData[0], list);
					} else if (ip.equals(lineData[1])
							&& cpu.equals(lineData[2])) {
						ArrayList<String> list = new ArrayList<String>();
						list.add(lineData[3]);
						table.put(lineData[0], list);
					}
				}
				linenumber++;
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
			System.exit(1);
		}
		finally{
			reader.close();
		}
	}

	/**
	 * convertDatetoUnix- Convert given date to UNIX
	 * @param input
	 * @return
	 * @throws ParseException
	 */
	public static long convertDatetoUnix(String input) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.ENGLISH); // Specify your locale
		long unixTime = 0;
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-4")); // Specify your timezone
		try {
			unixTime = dateFormat.parse(input).getTime();
			unixTime = unixTime / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return unixTime;
	}
    
	/**
	 * convertUnixtoDate- Convert Unix date to human readable format
	 * @param input
	 * @return
	 * @throws ParseException
	 */
	public static String convertUnixtoDate(String input) throws ParseException {
		Date date = new Date((long) Integer.parseInt(input) * 1000L); // format of the date
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		String java_date = jdf.format(date);
		return java_date;
	}

	/**
	 * validateData- validate input Query Parameters
	 * @param IP
	 * @param cpu
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static boolean validateData(String IP, String cpu, String starttime,
			String endtime) {
		String[] queryParams = IP.split("\\.");
		if (queryParams.length != 4)
			return false;

		if (!cpu.equals("1") && !cpu.equals("0"))
			return false;

		return validateTimeStamp(starttime) && validateTimeStamp(endtime);

	}

	/**
	 * validateTimeStamp- Validate Query parameters time
	 * @param inputString
	 * @return
	 */
	public static boolean validateTimeStamp(String inputString) {
		SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		try {
			format.parse(inputString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

}
