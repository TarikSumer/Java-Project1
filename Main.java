import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;  

public class Main {
		public static String[] readFile(String path) {
			try {
				int i=0;
				int length=Files.readAllLines(Paths.get(path)).size();
				String[] results = new String[length];
					for (String line : Files.readAllLines(Paths.get(path))) {
						results[i++] = line;
					}
				return results;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

	public static void main(String[] args) throws ParseException {
		String[] productLines = readFile(args[1]);   // properties about products 
				
		String[] shoppingLines = readFile(args[0]);        // all shopping acts
	
		DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");
		double price;  // will be use for determine the cost of the product
		
		for (int i=0;i < shoppingLines.length;i++) {
			double total= 0;
			String[] shoppingAction =  shoppingLines[i].split("\t");     //seperating all shopping act for handling easily
			System.out.println("---"+shoppingAction[0]+"---");	// customer name	
			
			for (int j=3;j < shoppingAction.length;j +=2) {    // dealing with all product details seperately
				for (int k=0;k < productLines.length;k++) {
					String[] product = productLines[k].split("\t");
					
					if (shoppingAction[j].equals(product[0]) && shoppingAction[1].equals(product[1])) { //equality of product and type					
					// controlling date equivalence eventually
						Date purchaseDate = sourceFormat.parse(shoppingAction[2]);				
							Date startDate = sourceFormat.parse(product[2]);
							Date endDate = sourceFormat.parse(product[3]);
 						if (purchaseDate.after(startDate) && purchaseDate.before(endDate)) {
 							price = Double.parseDouble(product[product.length - 1]);  // determining the correct price after date validity check
							System.out.println(shoppingAction[j] + "\t" + price + "\t" + shoppingAction[j + 1]
									+ "\t" + (Double.parseDouble(shoppingAction[j + 1]) * price));
							total += Double.parseDouble(shoppingAction[j + 1]) * price;
  							break;
						}
					}	
				
				}	
				
			}
			System.out.println("Total" + "\t" + total);  // total amount and cost

		}
		
	}
}
