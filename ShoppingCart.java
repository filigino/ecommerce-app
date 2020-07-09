import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class ShoppingCart extends User {
    private final ArrayList<String> content;

    private final File file;
    private Scanner cartScanner;
    private Scanner fileScanner;
    private FileWriter fileWriter;

    public ShoppingCart(String username) {
        super(username);
        content = new ArrayList<String>();
        file = new File("Text Files/Cart_" + username + ".txt");

        try {
            // New shopping cart so empty
            if (file.createNewFile()) {
            }
            // Shopping cart already exists so may have content
            else {
                this.prepScanner();
                while (cartScanner.hasNext()) {
                    content.add(cartScanner.nextLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepScanner() {
        try {
            cartScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found");
        }
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void addItem(Item item, int quantity) {
        boolean isAlreadyInCart = false;
        String[] info = item.getInfo().split(",");

        int i = 0;
        for (String s : content) {
            if (s.split(",")[0].equals(info[0])) {
                isAlreadyInCart = true;
                content.remove(i);
                break;
            }
            i++;
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        
        String newItem = info[0] + "," + info[1] + "," + date + "," + quantity;
        content.add(newItem);

        if (isAlreadyInCart) {
            String newCart = "";

            this.prepScanner();
            while (cartScanner.hasNext()) {
                String nextLine = cartScanner.nextLine();
                if (!newCart.equals("")) {
                    newCart += "\n";
                }
                if (nextLine.split(",")[0].equals(info[0])) {
                    newCart += newItem;
                } else {
                    newCart += nextLine;
                }
            }
            try {
                fileWriter = new FileWriter("Text Files/Cart_"
                    + this.getUsername() + ".txt", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileWriter.write(newCart);
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                fileWriter = new FileWriter("Text Files/Cart_"
                    + this.getUsername() + ".txt", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (file.length() == 0) {
                    fileWriter.write(newItem);
                } else {
                    fileWriter.write("\n" + newItem);
                }
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void signOut() {
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cartScanner.close();
    }
}
