package Classes;

public class Project {
    //declaring class variables
    private String title;
    private String stream;
    private String proposed_by;
    private boolean taken;

    public Project(String title, String stream, String proposed_by, boolean taken) {
        this.title = title;
        this.stream = stream;
        this.proposed_by = proposed_by;
        this.taken = taken;
    }

    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getProposed_by() {
        return proposed_by;
    }

    public void setProposed_by(String proposed_by) {
        this.proposed_by = proposed_by;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}