import java.util.List;

public class Student extends Member{
    //declaring class variables
    private int studentId;
    private List<Project> preferences;

    //getters and setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public List getPreferences() {
        return preferences;
    }

    public void setPreferences(List preferences) {
        this.preferences = preferences;
    }

    public Project getPreference(int number){
        return preferences.get(number);
    }
}
