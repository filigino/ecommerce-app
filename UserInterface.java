import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private int currentPage;
    private ArrayList<Readable> readables;
    private ArrayList<Audio> audioProducts;
    private int confirmationID;

    private final Scanner inputScanner;
    private String input;
    private int option;
    private Scanner fileScanner;

    private ShoppingCart user;

    public UserInterface() {
        currentPage = 1;
        confirmationID = 1000;
        inputScanner = new Scanner(System.in);
    }

    public int getCurrentPage() {
        switch (currentPage) {
            case(1):
                System.out.println("1. Sign In");
                System.out.println("2. Sign Up");
                System.out.print("\nChoose your option: ");
                input = inputScanner.nextLine();

                option = Integer.parseInt(input);
                if (option == 1) {
                    System.out.print("\nEnter your username: ");
                    String username = inputScanner.nextLine();

                    if (User.checkForUsername(username)) {
                        user = new ShoppingCart(username);
                        this.changeCurrentPage(3);
                    } else {
                        this.changeCurrentPage(4);
                    }
                } else if (option == 2) {
                    this.changeCurrentPage(2);
                }

                break;
            case(2):
                System.out.print("Choose your username: ");
                String username = inputScanner.nextLine();

                if (!User.checkForUsername(username)) {
                    User.signUp(username);
                    System.out.println("\nUsername successfully added");
                } else {
                    System.out.println("\nUsername already exists");
                }

                this.changeCurrentPage(1);
                break;
            case(3):
                System.out.println("Hello " + user.getUsername());
                this.changeCurrentPage(5);
                break;
            case(4):
                System.out.println("No Access");
                this.changeCurrentPage(1);
                break;
            case(5):
                System.out.println("1. View Items By Category");
                System.out.println("2. View Shopping Cart");
                System.out.println("3. Sign Out");
                System.out.print("\nChoose your option: ");
                input = inputScanner.nextLine();

                option = Integer.parseInt(input);
                if (option == 1) {
                    this.changeCurrentPage(6);
                } else if (option == 2) {
                    this.changeCurrentPage(7);
                } else if (option == 3) { // sign out
                    user.signOut();
                    user = null;
                    this.changeCurrentPage(1);
                }

                break;
            case(6):
                System.out.println("1. Readables");
                System.out.println("2. Audio");
                System.out.println("\nPress -1 to return to previous menu");
                System.out.print("\nChoose your option: ");
                input = inputScanner.nextLine();

                option = Integer.parseInt(input);
                if (option == 1) {
                    this.changeCurrentPage(8);
                } else if (option == 2) {
                    this.changeCurrentPage(9);
                } else if (option == -1) {
                    this.changeCurrentPage(5);
                }

                break;
            case(7):
                for (String s : user.getContent()) {
                    System.out.println(s);
                }

                System.out.println("\n0. Check Out");
                System.out.println("\nPress -1 to return to previous menu");
                System.out.print("\nChoose your option: ");
                input = inputScanner.nextLine();

                option = Integer.parseInt(input);
                if (option == 0) {
                    this.changeCurrentPage(10);
                } else if (option == -1) {
                    this.changeCurrentPage(5);
                }

                break;
            case(8):
                System.out.println("Readables:");
                System.out.printf("%s\t%-30s\t%s\t%-9s\t%-8s\t%s\n",
                    "S.No", "Name", "Author", "Price ($)", "Quantity",
                    "Type");
                this.getReadables();
                this.showReadables();

                System.out.println("\nPress -1 to return to previous menu");
                System.out.print("\nChoose your option: ");
                input = inputScanner.nextLine();

                option = Integer.parseInt(input);
                if (option == -1) {
                    this.changeCurrentPage(6);
                } else {
                    System.out.print("Enter quantity: ");
                    input = inputScanner.nextLine();

                    int quantity = Integer.parseInt(input);
                    for (Readable r : readables) {
                        if (r.sNo == option) {
                            for (String s : user.getContent()) {
                                String[] sArr = s.split(",");
                                if (Integer.parseInt(sArr[0]) == option) {
                                    quantity += Integer.parseInt(sArr[3]);
                                }
                            }
                            if (r.quantity >= quantity) {
                                user.addItem(r, quantity);
                                if (quantity > 1) {
                                    System.out.println("\n" + quantity + " "
                                    + r.name + " "
                                    + r.getClass().toString().replace("class ", "")
                                    + "s added to your cart");
                                } else {
                                    System.out.println("\n" + quantity + " "
                                    + r.name + " "
                                    + r.getClass().toString().replace("class ", "")
                                    + " added to your cart");
                                }
                            } else {
                                System.out.println("\nInsufficient stock");
                            }
                            break;
                        }
                    }
                    System.out.print("\nPress -2 to Continue Shopping or Press 0 to Checkout: ");
                    input = inputScanner.nextLine();

                    option = Integer.parseInt(input);
                    if (option == -2) {
                        this.changeCurrentPage(6);
                    } else if (option == 0) {
                        this.changeCurrentPage(10);
                    }
                }

                break;
            case(9):
                System.out.println("Audio:");
                System.out.printf("%s\t%-30s\t%s\t%-9s\t%-8s\t%s\n",
                    "S.No", "Name", "Artist", "Price ($)", "Quantity",
                    "Type");
                this.getAudioProducts();
                this.showAudioProducts();

                System.out.println("\nPress -1 to return to previous menu");
                System.out.print("\nChoose your option: ");
                input = inputScanner.nextLine();

                option = Integer.parseInt(input);
                if (option == -1) {
                    this.changeCurrentPage(6);
                } else {
                    System.out.print("Enter quantity: ");
                    input = inputScanner.nextLine();

                    int quantity = Integer.parseInt(input);
                    for (Audio a : audioProducts) {
                        if (a.sNo == option) {
                            for (String s : user.getContent()) {
                                String[] sArr = s.split(",");
                                if (Integer.parseInt(sArr[0]) == option) {
                                    quantity += Integer.parseInt(sArr[3]);
                                }
                            }
                            if (a.quantity >= quantity) {
                                user.addItem(a, quantity);
                                if (quantity > 1) {
                                    System.out.println("\n" + quantity + " "
                                    + a.name + " "
                                    + a.getClass().toString().replace("class ", "")
                                    + "s added to your cart");
                                } else {
                                    System.out.println("\n" + quantity + " "
                                    + a.name + " "
                                    + a.getClass().toString().replace("class ", "")
                                    + " added to your cart");
                                }
                            } else {
                                System.out.println("\nInsufficient stock");
                            }
                            break;
                        }
                    }
                    System.out.print("\nPress -2 to Continue Shopping or Press 0 to Checkout: ");
                    input = inputScanner.nextLine();

                    option = Integer.parseInt(input);
                    if (option == -2) {
                        this.changeCurrentPage(6);
                    } else if (option == 0) {
                        this.changeCurrentPage(10);
                    }
                }

                break;
            case(10):
                System.out.println("Billing Information:");
                System.out.printf("%-30s\t%8s\t%s\n",
                    "Name", "Quantity", "Price ($)");

                this.getReadables();
                this.getAudioProducts();

                double enviroTax = 0;
                double HST = 0;
                double shipping = 0;
                double total = 0;

                for (String s : user.getContent()) {
                    String[] sArr = s.split(",");
                    int sNo = Integer.parseInt(sArr[0]);
                    String name = sArr[1];
                    int quantity = Integer.parseInt(sArr[3]);

                    int price = 0;
                    for (Readable r : readables) {
                        if (r.sNo == sNo) {
                            price = r.getPrice();
                            if (r.getClass().toString().replace("class ", "").equals("Book")) {
                                enviroTax += price * 0.02 * quantity;
                            }
                            HST += price * quantity * 0.13;

                            shipping += price * quantity * 0.10;
                            total += price * quantity;
                            break;
                        }
                    }
                    if (price == 0) {
                        for (Audio a : audioProducts) {
                            if (a.sNo == sNo) {
                                price = a.getPrice();
                                if (a.getClass().toString().replace("class ", "").equals("CD")) {
                                    enviroTax += price * 0.02 * quantity;
                                }
                                HST += price * quantity * 0.13;
                                
                                shipping += price * quantity * 0.10;
                                total += price * quantity;
                                break;
                            }
                        }
                    }
                    System.out.printf("%-30s\t%-8s\t%s\n",
                        name, quantity, price);
                }
                System.out.printf("\n%-15s\t\t%d%s\t\t\t%.2f\n",
                    "Environment Tax", 2, "%", enviroTax);
                System.out.printf("%-15s\t\t%d%s\t\t\t%.2f\n",
                    "HST", 13, "%", HST);
                System.out.printf("%-15s\t\t%d%s\t\t\t%.2f\n",
                    "Shipping", 10, "%", shipping);

                total += enviroTax + HST + shipping;
                System.out.printf("%-15s\t\t\t\t\t%.2f\n\n",
                    "Total", total);
                
                System.out.println("Are you sure you want to pay? Yes or No");
                input = inputScanner.nextLine().toLowerCase();

                if (input.equals("yes")) {
                    System.out.println("\nConfirmation ID: U" + confirmationID++);
                    System.out.println("Items shipped to: " + user.getUsername());

                    System.out.print("\nPress Enter to keep browsing");
                    input = inputScanner.nextLine();
                } else if (input.equals("no")) {
                }

                this.changeCurrentPage(5);
                break;
        }

        System.out.println();

        return currentPage;
    }

    private void prepFileScanner(String file) {
        if (file.equals("Text Files/Users.txt")) {
            try {
                fileScanner = new Scanner(new File(file)).useDelimiter(",");
            } catch (FileNotFoundException e) {
                System.out.println("User file could not be found");
            }
        } else {
            try {
                fileScanner = new Scanner(new File(file));
            } catch (FileNotFoundException e) {
                System.out.println("File could not be found");
            }
        }
    }

    public int changeCurrentPage(int page) {
        this.currentPage = page;
        return page;
    }

    public void getReadables() {
        readables = new ArrayList<Readable>();

        this.prepFileScanner("Text Files/Books_v01.txt");
        while (fileScanner.hasNext()) {
            readables.add(new Book(fileScanner.nextLine()));
        }
        this.prepFileScanner("Text Files/Ebooks_v01.txt");
        while (fileScanner.hasNext()) {
            readables.add(new eBook(fileScanner.nextLine()));
        }
        fileScanner.close();
    }

    public void getAudioProducts() {
        audioProducts = new ArrayList<Audio>();

        this.prepFileScanner("Text Files/CDs_v01.txt");
        while (fileScanner.hasNext()) {
            audioProducts.add(new CD(fileScanner.nextLine()));
        }
        this.prepFileScanner("Text Files/MP3.txt");
        while (fileScanner.hasNext()) {
            audioProducts.add(new MP3(fileScanner.nextLine()));
        }
        fileScanner.close();
    }

    public void showReadables() {
        for (Readable r : readables) {
            String[] infoArr = r.getInfo().split(",");
            System.out.printf("%s\t%-30s\t%s\t%-9s\t%-8s\t%s\n",
                infoArr[0], infoArr[1], infoArr[2], infoArr[3], infoArr[4],
                r.getClass().toString().replace("class ", ""));
        }
    }

    public void showAudioProducts() {
        for (Audio a : audioProducts) {
            String[] infoArr = a.getInfo().split(",");
            System.out.printf("%s\t%-30s\t%s\t%-9s\t%-8s\t%s\n",
                infoArr[0], infoArr[1], infoArr[2], infoArr[3], infoArr[4],
                a.getClass().toString().replace("class ", ""));
        }
    }
}
