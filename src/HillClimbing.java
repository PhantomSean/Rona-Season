import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HillClimbing {
    private static double score_mult = 0.75;

    public static void main(String[] args) throws IOException {
        List<Solution> solutions = GenerateSolution.genSolution(new ArrayList<>());
        ScoringFunctions.main(solutions);
        List<Solution> tmp = solutions;

        for(int i = 0; i < 80; i++) {
            solutions = acceptance(solutions, change(solutions));
            //change(solutions);
            //ScoringFunctions.main(solutions);
            //solutions = tmp;
        }
        ScoringFunctions.main(solutions);


    }
    private static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions){
        if(ScoringFunctions.scoreSolution(solutions) > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            return solutions;
        }
    }
    private static List<Solution> change(List<Solution> solutions) throws IOException {
        // Get two students who didn't get their preference whose GPA is 3.2 or higher
        List<Solution> tempSols = new ArrayList<>();
        int num = 0;
        for (Solution solution: solutions) {
            if (solution.getPrefGotten() == 0 && solution.getStudent().getGPA() >= 3.3 && num < 2) {
                tempSols.add(solution);
                num++;
            }
        }

        System.out.println("---------------------------------------------");
        for (Solution solution : tempSols) {
            System.out.println("Students to change: " + solution.getStudent().getName());
        }
        System.out.println("---------------------------------------------");


        for (Solution tempSol : tempSols) {
            for (int j = 0; j < 10; j++) {
                if (tempSol.getStudent().getGPA() > findStudentbyProject(solutions, tempSol.getStudent().getPreference(j)).getGPA()) {
                    int tmpOne = findSolNumberByStudent(solutions, tempSol.getStudent());
                    int tmpTwo = findSolNumberByStudent(solutions, findStudentbyProject(solutions, tempSol.getStudent().getPreference(j)));
                    Project tmpProject = findProjectbyTitle(solutions, tempSol.getStudent().getPreference(j));
                    solutions.get(tmpOne).getProject().setTaken(false);
                    solutions.get(tmpOne).setProject(tmpProject);
                    solutions.get(tmpTwo).setProject(GenerateSolution.giveRandomProject(solutions.get(tmpTwo).getStudent().getStream()));
                    solutions.get(tmpOne).setScore(Math.pow(score_mult, 10-j));
                    solutions.get(tmpTwo).setScore(Math.pow(score_mult, 0));
                    solutions.get(tmpOne).getStudent().setPrefGotten(j);
                    solutions.get(tmpTwo).getStudent().setPrefGotten(0);
                    break;
                }
            }
        }
        tempSols.clear();
        return solutions;
    }

    private static Student findStudentbyProject(List<Solution> solutions, String project){
        for(int i = 0; i < solutions.size(); i++){
            if(project.equals(solutions.get(i).getProjectTitle())){
                return solutions.get(i).getStudent();
            }
        }
        return solutions.get(0).getStudent();
    }

    private static int findSolNumberByStudent(List<Solution> solutions, Student student){
        for(int i = 0; i < solutions.size(); i++){
            if(student.getName().equals(solutions.get(i).getStudent().getName())){
                return i;
            }
        }
        return 0;
    }

    private static Project findProjectbyTitle(List<Solution> solutions, String project){
        for(int i = 0; i < solutions.size(); i++){
            if(project.equals(solutions.get(i).getProjectTitle())){
                return solutions.get(i).getProject();
            }
        }
        return solutions.get(0).getProject();
    }

}
