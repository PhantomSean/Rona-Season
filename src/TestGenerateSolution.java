import Classes.Student;

import java.util.ArrayList;
import java.util.List;

class TestGenerateSolution {

    //method that tests the genGPA method
    static String testGenGPA(){
        List<String> preferences = new ArrayList<>();
        Student student = new Student("check", "CS", 0, preferences, false, 0, PopulateClasses.genGPA());
        if(student.getGPA() > 4.2 && student.getGPA() < 1){
            return "error in method genGPA";
        }else{
            return "genGPA method is working";
        }
    }
}
