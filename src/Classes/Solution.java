package Classes;

public class Solution {

    private Student student;
    private Project project;
    private double score;

    public Solution(Student student, Project project, double score) {
        this.student = student;
        this.project = project;
        this.score = score;
    }

    public String getStudentName() { return student.getName(); }

    public int getStudentNumber() { return student.getStudentId(); }

    public  String getProjectTitle() { return project.getTitle(); }

    public String getSupervisor() { return  project.getProposed_by(); }

    public Project getProject() {
        return project;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public  void  setProject(Project project) { this.project = project; }

    public int getPrefGotten() { return student.getPrefGotten(); }

    public double getScore() { return score;}

}
