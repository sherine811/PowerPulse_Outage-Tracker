import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

class PowerOutage {
    int id;
    String address;
    String dateTime;
    String description;
    String status;

    public PowerOutage(int id, String address, String dateTime, String description, String status) {
        this.id = id;
        this.address = address;
        this.dateTime = dateTime;
        this.description = description;
        this.status = status;
    }

    public String toFileString() {
        return id + "|" + address + "|" + dateTime + "|" + description + "|" + status;
    }

    public static PowerOutage fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new PowerOutage(
            Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]
        );
    }

    public String toDisplayString() {
        return "ID: " + id + " | " + address + " | " + dateTime + " | " + status + "\n" + description + "\n";
    }
}

public class PowerPulseGUI {
    private JFrame frame;
    private JTextArea displayArea;
    private List<PowerOutage> outages = new ArrayList<>();
    private final String FILE_NAME = "outages.txt";

    public PowerPulseGUI() {
        loadOutages();
        buildGUI();
    }

    private void buildGUI() {
        frame = new JFrame("PowerPulse – Outage Tracker");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(displayArea);
        frame.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton reportBtn = new JButton("Report Outage");
        JButton viewBtn = new JButton("View All");
        JButton resolveBtn = new JButton("Mark Resolved");

        buttonPanel.add(reportBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(resolveBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        reportBtn.addActionListener(e -> showReportDialog());
        viewBtn.addActionListener(e -> showAllReports());
        resolveBtn.addActionListener(e -> showResolveDialog());

        frame.setVisible(true);
    }

    private void showReportDialog() {
        JTextField address = new JTextField();
        JTextField dateTime = new JTextField();
        JTextField description = new JTextField();
        Object[] fields = {
            "Address:", address,
            "Date & Time:", dateTime,
            "Description:", description
        };
        int option = JOptionPane.showConfirmDialog(frame, fields, "Report Power Outage", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int id = outages.size() + 1;
            PowerOutage o = new PowerOutage(id, address.getText(), dateTime.getText(), description.getText(), "Reported");
            outages.add(o);
            saveOutages();
            JOptionPane.showMessageDialog(frame, "Outage reported with ID: " + id);
        }
    }

    private void showAllReports() {
        StringBuilder sb = new StringBuilder();
        if (outages.isEmpty()) {
            sb.append("No outage reports yet.");
        } else {
            for (PowerOutage o : outages) {
                sb.append(o.toDisplayString()).append("\n");
            }
        }
        displayArea.setText(sb.toString());
    }

    private void showResolveDialog() {
        String input = JOptionPane.showInputDialog(frame, "Enter Outage ID to mark as resolved:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                boolean found = false;
                for (PowerOutage o : outages) {
                    if (o.id == id && o.status.equals("Reported")) {
                        o.status = "Resolved";
                        found = true;
                        saveOutages();
                        JOptionPane.showMessageDialog(frame, "Outage ID " + id + " marked as resolved.");
                        return;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(frame, "Outage not found or already resolved.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID entered.");
            }
        }
    }

    private void saveOutages() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (PowerOutage o : outages) {
                writer.write(o.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to save outage reports.");
        }
    }

    private void loadOutages() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outages.add(PowerOutage.fromFileString(line));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load previous outages.");
        }
    }

    public static void main(String[] args) {
        new PowerPulseGUI();
    }
}
