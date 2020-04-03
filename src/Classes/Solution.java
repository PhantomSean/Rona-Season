package Classes;

import java.util.List;

public class Solution {

    private Student student;
    private Project project;

    public Solution(Student student, Project project) {
        this.student = student;
        this.project = project;
    }

    public String getStudentName() { return student.getName(); }

    public int getStudentNumber() { return student.getStudentId(); }

    public  String getProjectTitle() { return project.getTitle(); }

    public String getSupervisor() { return  project.getProposed_by(); }

}
