public class Student extends Member{
    //declaring class variables
    private int studentId;
    private String preferences[];

    //getters and setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String[] getPreferences() {
        return preferences;
    }

    public void setPreferences(String[] preferences) {
        this.preferences = preferences;
    }

    public String getPreference(int number){
        return preferences[number];
    }
}
