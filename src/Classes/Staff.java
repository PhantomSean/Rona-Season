package Classes;

import Classes.Member;

public class Staff extends Member {
    //declaring class variables
    private String project;
    private String interests;

    //getters and setters
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
