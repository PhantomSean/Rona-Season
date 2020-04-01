package Classes;

import java.util.List;

public class Solution {

    private Student student;
    private Project project;

    public Solution(Student student, Project project) {
        this.student = student;
        this.project = project;
    }

    public Student getStudent() { return student; };

    public  void  setStudent(Student student) { this.student = student; }

    public  Project project() { return project; }

    public  void  setProject(Project project) { this.project = project; }

}
