// poweroutagemanager.java
import java.io.*;
import java.util.*;

class poweroutagemanager {
    private List<poweroutage> outages = new ArrayList<>();
    private final String FILE_NAME = "outages.txt";

    public poweroutagemanager() {
        loadFromFile();
    }

    public void reportOutage(String address, String dateTime, String description) {
        int id = outages.size() + 1;
        poweroutage outage = new poweroutage(id, address, dateTime, description, "Reported");
        outages.add(outage);
        saveToFile();
        System.out.println("Outage reported successfully with ID: " + id);
    }

    public void viewAllReports() {
        if (outages.isEmpty()) {
            System.out.println("No outage reports found.");
            return;
        }
        for (poweroutage o : outages) {
            System.out.println("ID: " + o.getId() +
                    ", Address: " + o.getAddress() +
                    ", Time: " + o.getDateTime() +
                    ", Status: " + o.getStatus() +
                    "\nDescription: " + o.getDescription() + "\n");
        }
    }

    public void viewByAddress(String keyword) {
        boolean found = false;
        for (poweroutage o : outages) {
            if (o.getAddress().toLowerCase().contains(keyword.toLowerCase())) {
                found = true;
                System.out.println("ID: " + o.getId() + ", Address: " + o.getAddress() +
                        ", Time: " + o.getDateTime() + ", Status: " + o.getStatus());
            }
        }
        if (!found) System.out.println("No outages found for given area.");
    }

    public void markAsResolved(int id) {
        for (poweroutage o : outages) {
            if (o.getId() == id && o.getStatus().equals("Reported")) {
                o.setStatus("Resolved");
                saveToFile();
                System.out.println("Outage ID " + id + " marked as Resolved.");
                return;
            }
        }
        System.out.println("Outage ID not found or already resolved.");
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (poweroutage o : outages) {
                writer.write(o.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving reports.");
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outages.add(poweroutage.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading reports.");
        }
    }
}
