public class Student {
    //declaring class variables
    private String name;
    private int id;
    private String stream;
    private String preferences[];

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String[] getPreferences() {
        return preferences;
    }

    public void setPreferences(String[] preferences) {
        this.preferences = preferences;
    }

    public String getPreference(int number){
        return preferences[number];
    }
}
