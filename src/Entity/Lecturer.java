package Entity;

public class Lecturer extends Individual {

    private String Department;

    public Lecturer() {
    }


    public void addDepartment(String dept) {
        this.Department = dept;
    }

    public String getDepartment() {
        return Department;
    }
}