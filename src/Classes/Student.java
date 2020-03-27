package Classes;

import Classes.Member;

import java.util.ArrayList;
import java.util.List;

public class Student extends Member {
    //declaring class variables
    private int studentId;
    private List<String> preferences = new ArrayList<String>();

    public Student(String name, String stream, int id, List<String> preferences){
        super(name, stream);
        this.studentId = id;
        this.preferences = preferences;
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
}
