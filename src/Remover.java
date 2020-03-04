import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Remover {

	public static final String file_path = "C:\\Users\\Ashish.Kumar\\Downloads\\new_tables.sql";

	public static void main(String[] args) throws IOException {

		Map<String, String> grayedOutColumnWithTable = new HashMap();

		List<String> itemsToBeRemoved = getGrayedOutColumns(grayedOutColumnWithTable);

		removeColumnsFromSqlFile(itemsToBeRemoved);

		for (Map.Entry<String, String> map : grayedOutColumnWithTable.entrySet()) {
			System.out.println("dictionary.{'" + map.getKey() + "':'" + map.getValue() + "'}");

		}

	}

	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static List<String> getGrayedOutColumns(Map<String, String> grayedOutColumnWithTable)
			throws FileNotFoundException, IOException {
		File file1 = new File("C:\\Users\\Ashish.Kumar\\Downloads\\MasterDashboardLayouts v2 (1).xlsx");
		FileInputStream fis = new FileInputStream(file1);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
//		System.out.print(wb.getNumberOfSheets());

		List<String> itemsToBeRemoved = new ArrayList<String>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			// System.out.println(sheetName);
			StringBuilder columnsToBeRemoved = new StringBuilder();
			for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
				Row row = sheet.getRow(j);
				try {
					Cell cell = null;
					if (row != null) {
						cell = row.getCell(0);
					}
//					System.out.println(cellStyle.getFillBackgroundColor());
					XSSFCellStyle cellStyle = null;
					if (cell != null) {
						cellStyle = (XSSFCellStyle) cell.getCellStyle();
					}
					if (cellStyle != null && cellStyle.getFillForegroundXSSFColor() != null) {

						// System.out.println(cellStyle.getFillForegroundXSSFColor().getARGBHex());
						if (cellStyle.getFillForegroundXSSFColor().getARGBHex().equalsIgnoreCase("FFBFBFBF")) {
							itemsToBeRemoved.add(cell.getStringCellValue());
							columnsToBeRemoved.append(cell.getStringCellValue() + ",");
						}
					}
				} catch (IllegalStateException e) {

				}
			}
			grayedOutColumnWithTable.put(sheetName, columnsToBeRemoved.toString());

		}
		wb.close();
		return itemsToBeRemoved;
	}

	/**
	 * @param itemsToBeRemoved
	 * @throws IOException
	 */
	private static void removeColumnsFromSqlFile(List<String> itemsToBeRemoved) throws IOException {
		// pass the path to the file as a parameter
		int coun = 0;
		List<String> orgList = new ArrayList();
		File file = new File(file_path);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (sc.hasNextLine())
			orgList.add(sc.nextLine());

		for (String item : itemsToBeRemoved) {
			coun = 0;
			Iterator<String> it = orgList.iterator();
			while (it.hasNext()) {
				String s = it.next();
				if (s.contains(item)) {
					// newList.add(orgItem);
					it.remove();
					coun++;
				}
				// System.out.println(s + " :" + coun);
			}

		}
		// System.out.println(coun);

		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file_path));
		for (String s : orgList) { // Maybe:
			outputWriter.write(s); // Or: // outputWriter.write(Integer.toString(x[i]);
			outputWriter.newLine();
		}
		outputWriter.flush();
		outputWriter.close();
	}

}
