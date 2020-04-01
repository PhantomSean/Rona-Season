package Classes;

import Classes.Member;

import java.util.ArrayList;
import java.util.List;

public class Student extends Member {
    //declaring class variables
    private int studentId;
    private List<String> preferences = new ArrayList<String>();
    private boolean hasProject;

    public Student(String name, String stream, int id, List<String> preferences, boolean hasProject){
        super(name, stream);
        this.studentId = id;
        this.preferences = preferences;
        this.hasProject = hasProject;
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


    public boolean isHasProject() {
        return hasProject;
    }

    public void setHasProject(boolean hasProject) {
        this.hasProject = hasProject;
    }
}
