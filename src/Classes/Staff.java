package Classes;

import java.util.ArrayList;
import java.util.List;

public class Staff extends Member {
    //declaring class variables
    private List<String> projects = new ArrayList<String>();
//    private List<String> interests = new ArrayList<String>();

    public Staff(String name, String stream, List<String> projects) {
        super(name, stream);
        this.projects = projects;
//        this.interests = interests;
    }

    //getters and setters
    public List<String> getProject() {
        return projects;
    }

    public void setProject(String project) {
        projects.add(project);
    }

//    public List<String> getInterests() {
//        return interests;
//    }
//
//    public void setInterests(String interest) {
//        interests.add(interest);
//    }
}
