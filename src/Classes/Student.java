package Classes;

import Classes.Member;

import java.util.ArrayList;
import java.util.List;

public class Student extends Member {
    //declaring class variables
    private int studentId;
    private List<String> preferences = new ArrayList<String>();
    private boolean hasProject;
    private int prefGot;
    private double GPA;

    public Student(String name, String stream, int id, List<String> preferences, boolean hasProject, int prefGot, double GPA){
        super(name, stream);
        this.studentId = id;
        this.preferences = preferences;
        this.hasProject = hasProject;
        this.prefGot = prefGot;
        this.GPA = GPA;
    }

    //getters and setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(String preference) { preferences.add(preference); }

    public String getPreference(int number){ return preferences.get(number); }


    public boolean hasProject() {
        return hasProject;
    }

    public void setHasProject(boolean hasProject) {
        this.hasProject = hasProject;
    }

    public int getPrefGotten() {
        return prefGot;
    }

    public void setPrefGotten(int prefGot) {
        this.prefGot = prefGot;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }
}
