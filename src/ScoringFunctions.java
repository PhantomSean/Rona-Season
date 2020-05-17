import Classes.Project;
import Classes.Solution;
import Classes.Student;
import GUI.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScoringFunctions {
    private static int[] prefs = new int[21];

    static double scoreSolution(List<Solution> solutions, int GPAInput){
        Solve.ui.setDuplicateProjectCheck(false);
        Solve.ui.setDuplicateStudentCheck(false);
        Solve.ui.setStreamCheck(false);
        //analyse(solutions);
        double total=0;
        double score;

        double importance =  GPAImportance(GPAInput);

        for (Solution value : solutions) {
            double score_mult = 0.75;
            if(value.getStudent().getPrefGotten() == 0)
                score = 1;
            else
                score = Math.pow(score_mult, 11 - value.getStudent().getPrefGotten());
            value.setScore(score);
        }
        for(Solution solution : solutions){
            total += solution.getScore();
        }
        total = 0;
        addPenalties(solutions);

        for(Solution solution : solutions){
            total += solution.getScore();
        }

        for(Solution solution : solutions){
            if(solution.getStudent().getGPA() > importance && solution.getStudent().getPrefGotten() > 0) {
                double tmp = solution.getStudent().getPrefGotten();
                total -= ((solution.getStudent().getGPA() * 0.1) * (1 / tmp));
            }
            else if(solution.getStudent().getGPA() > importance && solution.getStudent().getPrefGotten() == 0){
                total += 2;
            }

        }

        return total;
    }


    private static void addPenalties(List<Solution> solutions){
        checkForDuplicates(solutions);
        checkStream(solutions);
    }

    //adds a penalty for each duplicate student or project
    static void checkForDuplicates(List<Solution> solutions){
        int penalty = 100;

        for(int i=0; i<solutions.size(); i++){
            for(int j=i+1; j<solutions.size(); j++){

                if(solutions.get(i).getStudentName().equals(solutions.get(j).getStudentName())) {
                    solutions.get(i).addToScore(penalty);
                    Solve.ui.setDuplicateStudentCheck(true);
                }
                if(solutions.get(i).getProjectTitle().equals(solutions.get(j).getProjectTitle()) &&
                !solutions.get(i).getProjectTitle().equals("Self Specified")) {
                    solutions.get(i).addToScore(penalty);
                    Solve.ui.setDuplicateProjectCheck(true);
                }
            }
        }
    }

    static void checkStream(List<Solution> solutions){
    	int penalty = 100;

        for (Solution solution : solutions) {
            String studentStream = solution.getStudent().getStream();
            String projectStream = solution.getProject().getStream();

            if (!(studentStream.contains(projectStream)
                    || studentStream.equals(projectStream)
                    || projectStream.contains(studentStream))){
                solution.addToScore(penalty);

                //for printing out the violation to the user
                Solve.ui.setStreamCheck(true);

            }
        }
    }

    //user input 0-5 determines how gpa applies to score
    private static double GPAImportance(int userInput){
        double importance;
        switch (userInput) {
            case 1:
                importance = 4.0;
                break;
            case 2:
                importance = 3.67;
                break;
            case 3:
                importance = 3.5;
                break;
            case 4:
                importance = 3.25;
                break;
            case 5:
                importance = 3.08;
                break;
            default:
                importance = 0;
        }
        return importance;
    }


    private static void change(List<Solution> solutions){
        System.out.println("\n\nCHANGING SOLUTION\n\n");
        //making list for solutions which did not get a preference
        List<Solution> notGotPref = new ArrayList<>();
        List<Solution> temp = new ArrayList<>();
        for (Solution solution : solutions) {
            if (solution.getPrefGotten() == 0) {
                notGotPref.add(solution);
            }
        }
        //going through each student that got a preference
        for (int i = 0; i < solutions.size(); i++) {                                                        //Takes studentA that got a preferential project and goes through
            if (solutions.get(i).getPrefGotten() != 0) {                                                    //all the students whos project was not on their preference list
                temp.add(solutions.get(i));                                                                 // but had studentA's project on their list
                for(int j = 0; j < notGotPref.size(); j++){
                    if(checkForPref(solutions.get(j).getProjectTitle(), notGotPref.get(j).getStudent())){
                        temp.add(notGotPref.get(j));
                    }
                }
                if(temp.size() > 1) {                                                                       //studentA's project is randomly assigned
                    Random r = new Random();
                    int random = r.nextInt(temp.size() - 1);
                    if(random != 0) {                                                                       //if the project is not randomly assigned to the student who already had it
                        temp.get(random).getStudent().setPrefGotten(getPrefNumber(temp.get(0).getProjectTitle(), temp.get(random).getStudent()));//student gets a random project in their stream
                        temp.get(random).getProject().setTaken(false);                                                                           //then a random student who had the project on their
                        temp.get(0).getStudent().setPrefGotten(0);                                                                               //list gets it
                        solutions.get(returnNumber(solutions, temp.get(random))).setProject(temp.get(0).getProject());
                        solutions.get(returnNumber(solutions, temp.get(0))).setProject(GenerateSolution.giveRandomProject(temp.get(0).getStudent().getStream()));
                        solutions.get(returnNumber(solutions, temp.get(0))).getProject().setTaken(true);
                        temp.clear();
                    }
                }
            }
        }


    }

    //method for checking if a student had a project in their preferences
    static boolean checkForPref(String project, Student student){
        for(int i = 0; i < 10; i++){
            if(student.getPreference(i).equals(project)){
                return true;
            }
        }
        return false;
    }

    //method for checking what number the student had the preference at
    static int getPrefNumber(String project, Student student){
        for(int i = 0; i < 10; i++){
            if(student.getPreference(i).equals(project)){
                return i;
            }
        }
        return 10;
    }

    //method which returns which number of the list a solution is on
    static int returnNumber(List<Solution> solutions, Solution s){
        for(int i = 0; i < solutions.size(); i++){
            if(solutions.get(i).getProjectTitle().equals(s.getProjectTitle())){
                return i;
            }
        }
        return 0;
    }

    //method for analyzing a solution
    static void analyse(List<Solution> solutions) {
        double gotPrefs = 0;
        for (int i = 0; i < 21; i++) {
            prefs[i] = 0;
        }
        for (Solution value : solutions) {
            prefs[value.getPrefGotten()]++;
        }
        Solve.ui.displayInfoString("-------------------------------------------------------------------------------\n");
        System.out.println("SOLUTION ANALYSIS:");
        System.out.println(prefs[1] + " students got their first preference");
        System.out.println(prefs[2] + " students got their second preference");
        System.out.println(prefs[3] + " students got their third preference");
        System.out.println(prefs[4] + " students got their fourth preference");
        System.out.println(prefs[5] + " students got their fifth preference");
        System.out.println(prefs[0] + " students got no preference" + "\n");

        Solve.ui.displayInfoString("SOLUTION ANALYSIS:");
        Solve.ui.displayInfoString(prefs[1] + " students got their first preference");
        Solve.ui.displayInfoString(prefs[2] + " students got their second preference");
        Solve.ui.displayInfoString(prefs[3] + " students got their third preference");
        Solve.ui.displayInfoString(prefs[4] + " students got their fourth preference");
        Solve.ui.displayInfoString(prefs[5] + " students got their fifth preference");
        Solve.ui.displayInfoString(prefs[0] + " students got no preference" + "\n");

        double size = solutions.size();
        for (int i = 1; i < 6; i++) {
            gotPrefs += prefs[i];
        }
        double topFive = Math.round((gotPrefs / size) * 100.0);
        System.out.println((Math.round((prefs[1] / size) * 100.0)) + "% of students got their first preference");
        System.out.println(topFive + "% of students got one of their top five preferences" + "\n");

        Solve.ui.displayInfoString((Math.round((prefs[1] / size) * 100.0)) + "% of students got their first preference");
        Solve.ui.displayInfoString("This figure should ideally be above 40%\n");
        Solve.ui.displayInfoString(topFive + "% of students got one of their top five preferences");
        Solve.ui.displayInfoString("This figure should ideally be above 60%\n");
        Solve.ui.displayInfoString((Math.round((prefs[0] / size)* 100.0)) + "% of students got no preference");
        Solve.ui.displayInfoString("This figure should be below 5%\n");


        System.out.println("\n\n");
    }


}
