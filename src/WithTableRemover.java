import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WithTableRemover {

	public static void main(String[] args) {
		String tablename= "";
		String item="";
		List<String> orgList = new ArrayList();
		File file = new File("C:\\Users\\Ashish.Kumar\\Downloads\\new.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (sc.hasNextLine())
			orgList.add(sc.nextLine());
		
		for(String s: orgList) {
			String tbl="";
			if(s.contains("CREATE TABLE")) {
				System.out.println(s);
				//tbl=s.split(".")[1];
			}
			System.out.println(tbl);
		}
		
		
	}

}
