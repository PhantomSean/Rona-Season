import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HillClimbing {

    public static void main(String[] args) throws IOException {
        List<Solution> solutions = GenerateSolution.genSolution(new ArrayList<>());
        for (Solution solution : solutions) {
            System.out.println(solution.getStudentName() + "\t" + solution.getPrefGotten());
        }
        change(solutions);
    }

    private static void change(List<Solution> solutions) throws IOException {
        // Get two students who didn't get their preference whose GPA is 3.2 or higher
        List<Student> students = new ArrayList<>();
        int num = 0;
        for (Solution solution: solutions) {
            if (solution.getPrefGotten() == 0 && solution.getStudent().getGPA() >= 3.8 && num < 2) {
                students.add(solution.getStudent());
                num++;
            }
        }

        System.out.println("---------------------------------------------");
        for (Student student : students) {
            System.out.println("Students to change: " + student.getName());
        }
        System.out.println("---------------------------------------------");

        List<Solution> newSolutions = GenerateSolution.genSolution(students);
        for (Solution solution : newSolutions) {
            System.out.println(solution.getStudentName() + "\t" + solution.getPrefGotten());
        }
    }
}
