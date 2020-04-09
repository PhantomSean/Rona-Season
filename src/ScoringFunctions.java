import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScoringFunctions {
    private static int[] prefs = new int[11];

    public static void main(String[] args) throws IOException {
        List<Solution> solutions = GenerateSolution.genSolution();
        change(solutions);
        analyze(solutions);
    }

    private static void change(List<Solution> solutions){
        List<Solution> notGotPref = new ArrayList<>();
        List<Solution> temp = new ArrayList<>();
        for (Solution solution : solutions) {
            if (solution.getPrefGotten() == 0) {
                notGotPref.add(solution);
            }
        }
        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).getPrefGotten() != 0) {
                temp.add(solutions.get(i));
                for(int j = 0; j < notGotPref.size(); j++){
                    if(checkForPref(solutions.get(j).getProjectTitle(), notGotPref.get(j).getStudent())){
                        temp.add(notGotPref.get(j));
                    }
                }
                if(temp.size() > 1) {
                    Random r = new Random();
                    int random = r.nextInt(temp.size() - 1);
                    if(random != 0) {
                        temp.get(random).getStudent().setPrefGotten(getPrefNumber(temp.get(0).getProjectTitle(), temp.get(random).getStudent()));
                        temp.get(random).getProject().setTaken(false);
                        temp.get(0).getStudent().setPrefGotten(0);
                        solutions.get(returnNumber(solutions, temp.get(random))).setProject(temp.get(0).getProject());
                        solutions.get(returnNumber(solutions, temp.get(0))).setProject(GenerateSolution.giveRandomProject(temp.get(0).getStudent().getStream()));
                        solutions.get(returnNumber(solutions, temp.get(0))).getProject().setTaken(true);
                        temp.clear();
                    }
                }
            }
        }


    }

    private static boolean checkForPref(String project, Student student){
        for(int i = 0; i < 10; i++){
            if(student.getPreference(i).equals(project)){
                return true;
            }
        }
        return false;
    }

    private static int getPrefNumber(String project, Student student){
        for(int i = 0; i < 10; i++){
            if(student.getPreference(i).equals(project)){
                return i;
            }
        }
        return 0;
    }

    private static int returnNumber(List<Solution> solutions, Solution s){
        for(int i = 0; i < solutions.size(); i++){
            if(solutions.get(i).getProjectTitle().equals(s.getProjectTitle())){
                return i;
            }
        }
        return 0;
    }


    private static void analyze(List<Solution> solutions){
        int check = 0;
        for(int i = 0; i < 11; i++){
            prefs[i] = 0;
        }
        for(int i = 0; i < solutions.size(); i++){
            prefs[solutions.get(i).getPrefGotten()]++;
        }
        System.out.println(prefs[1] + " students got their first preference");
        System.out.println(prefs[2] + " students got their second preference");
        System.out.println(prefs[3] + " students got their third preference");
        System.out.println(prefs[4] + " students got their fourth preference");
        System.out.println(prefs[5] + " students got their fifth preference");
        System.out.println(prefs[0] + " students got no preference");
        System.out.println(solutions.size());

        for (Solution solution : solutions) {
            System.out.println(solution.getStudentName() + ": " + solution.getProjectTitle());
        }
    }

}
