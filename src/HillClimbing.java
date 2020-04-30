import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HillClimbing {
    private  static HashMap<String, Project> projects;
    private static List<Student> students;

    public static void main(String[] args) throws IOException {
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");

        List<Solution> solutions = GenerateSolution.genSolution(projects, students, new ArrayList<>());
        ScoringFunctions.main(solutions);

        for(int i = 0; i < 100; i++) {
            solutions = acceptance(solutions, change(solutions));
        }
        ScoringFunctions.main(solutions);


    }

    // Decide whether or not to accept the new solution
    static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions){

        if(ScoringFunctions.scoreSolution(solutions) > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            return solutions;
        }
    }


    // Makes a change to the current solution by swapping the preference of a person that got a randomly assigned project
    static List<Solution> change(List<Solution> solutions) {
        //GPA Importance to do after
        double GPAImportance = 3.3;

        // Get a student who didn't get their preference whose GPA is the same as GPAImportance or higher
        List<Solution> tempSols = new ArrayList<>();
        int num = 0;
        for (Solution solution : solutions) {
            if (solution.getPrefGotten() == 0 && solution.getStudent().getGPA() >= GPAImportance && num < 1) {
                tempSols.add(solution);
                num++;
            }
        }
        if (tempSols.size() != 0){
            for (int j = 0; j < 10; j++) {
                if (tempSols.get(0).getStudent().getGPA() > findStudentByProject(solutions, tempSols.get(0).getStudent().getPreference(j)).getGPA()) {
                    int tmpOne = findSolNumberByStudent(solutions, tempSols.get(0).getStudent());
                    int tmpTwo = findSolNumberByStudent(solutions, findStudentByProject(solutions, tempSols.get(0).getStudent().getPreference(j)));
                    Project tmpProject = findProjectByTitle(solutions, tempSols.get(0).getStudent().getPreference(j));
                    solutions.get(tmpOne).getProject().setTaken(false);                                       //swapping the projects
                    solutions.get(tmpOne).setProject(tmpProject);
                    solutions.get(tmpTwo).setProject(GenerateSolution.giveRandomProject(solutions.get(tmpTwo).getStudent().getStream()));
                    double score_mult = 0.75;
                    solutions.get(tmpOne).setScore(Math.pow(score_mult, 10 - j));                             //assigning new scores
                    solutions.get(tmpTwo).setScore(Math.pow(score_mult, 0));
                    solutions.get(tmpOne).getStudent().setPrefGotten(j);                                      //assigning the new preferences gotten
                    solutions.get(tmpTwo).getStudent().setPrefGotten(0);
                    break;
                }
            }
        }
        tempSols.clear();
        return solutions;
    }

    static Student findStudentByProject(List<Solution> solutions, String project){
        for (Solution solution : solutions) {
            if (project.equals(solution.getProjectTitle())) {
                return solution.getStudent();
            }
        }
        return solutions.get(0).getStudent();
    }

    static int findSolNumberByStudent(List<Solution> solutions, Student student){
        for(int i = 0; i < solutions.size(); i++){
            if(student.getName().equals(solutions.get(i).getStudent().getName())){
                return i;
            }
        }
        return 0;
    }

    static Project findProjectByTitle(List<Solution> solutions, String project){
        for (Solution solution : solutions) {
            if (project.equals(solution.getProjectTitle())) {
                return solution.getProject();
            }
        }
        return solutions.get(0).getProject();
    }

    public static Project findProjectByStudent(List<Solution> solutions, String student){
        for(Solution solution : solutions){
            if(solution.getStudentName().equals(student)){
                return solution.getProject();
            }
        }
        return solutions.get(0).getProject();
    }
}