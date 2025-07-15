// poweroutage.java
class poweroutage {
    private int id;
    private String address;
    private String dateTime;
    private String description;
    private String status; // Reported or Resolved

    public poweroutage(int id, String address, String dateTime, String description, String status) {
        this.id = id;
        this.address = address;
        this.dateTime = dateTime;
        this.description = description;
        this.status = status;
    }

    public int getId() { return id; }
    public String getAddress() { return address; }
    public String getDateTime() { return dateTime; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return id + "|" + address + "|" + dateTime + "|" + description + "|" + status;
    }

    public static poweroutage fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new poweroutage(
            Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]
        );
    }
}
