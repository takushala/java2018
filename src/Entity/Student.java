package Entity;

public class Student extends Individual {

    private String Class;

    public Student() {
    }

    public void addClass(String Class) {
        this.Class = Class;
    }

    public String getclass() {
        return Class;
    }
}
