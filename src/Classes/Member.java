package Classes;

public class Member {
    private String name;
    private String stream;

    Member(String name, String stream) {
        this.name = name;
        this.stream = stream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}


