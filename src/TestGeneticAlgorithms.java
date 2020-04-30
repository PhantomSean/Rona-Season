import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TestGeneticAlgorithms {
    //method to test the mutate method
    static String testMutate(){
        List<Solution> parent1 = new ArrayList<>();
        List<Solution> parent2 = new ArrayList<>();

        Project one = new Project("One", "CS", "ME", false);
        Project two = new Project("Two", "CS", "ME", false);
        Project three = new Project("Three", "CS", "ME", false);
        Project four = new Project("Four", "CS", "ME", false);
        List<String> empty = new ArrayList<>();

        Student me = new Student("ME", "CS", 0, empty, true, 0, 4.0);

        parent1.add(new Solution(me, one, 0));
        parent2.add(new Solution(me, two, 0));

        HashMap<String, Project> projects = new HashMap<>();

        projects.put(one.getTitle(), one);
        projects.put(two.getTitle(), two);
        projects.put(three.getTitle(), three);
        projects.put(four.getTitle(), four);


        Project test = GeneticAlgorithm.mutate(parent1, parent2, projects);

        if (test.getTitle().equals("Three") || test.getTitle().equals("Four"))
            return "Mutate method working";
        else
            return "Mutate method not working";
    }

    //method to test the getSolutionScore method
    static String testGetSolutionScore(){
        List<String> empty = new ArrayList<>();
        empty.add("testProject");
        Solution sol = new Solution(new Student("test", "CS", 1, empty, true, 1, 4.2), new Project("testProject", "CS","x", true), 0);
        if(Math.abs(GeneticAlgorithm.getSolutionScore(sol) - 0.056313514) < 1e-4){
            return "getSolutionScore method is working";
        }else{
            return "error in method getSolutionScore";
        }
    }
}
