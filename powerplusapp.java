// powerplusapp.java
import java.util.Scanner;

public class powerplusapp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        poweroutagemanager manager = new poweroutagemanager();

        while (true) {
            System.out.println("\n=== PowerPulse Menu ===");
            System.out.println("1. Report Power Outage");
            System.out.println("2. View All Outages");
            System.out.println("3. Search Outages by Area");
            System.out.println("4. Admin: Mark Outage as Resolved");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter address: ");
                    String address = sc.nextLine();
                    System.out.print("Enter date & time: ");
                    String dateTime = sc.nextLine();
                    System.out.print("Enter short description: ");
                    String desc = sc.nextLine();
                    manager.reportOutage(address, dateTime, desc);
                }
                case 2 -> manager.viewAllReports();
                case 3 -> {
                    System.out.print("Enter area keyword: ");
                    String keyword = sc.nextLine();
                    manager.viewByAddress(keyword);
                }
                case 4 -> {
                    System.out.print("Enter outage ID to mark as resolved: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    manager.markAsResolved(id);
                }
                case 5 -> {
                    System.out.println("Thank you for using PowerPulse!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
