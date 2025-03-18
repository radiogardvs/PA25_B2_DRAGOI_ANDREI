import java.util.List;

class Student {
    private String name;
    private List<Project> acceptableProjects;

    public Student(String name, List<Project> acceptableProjects) {
        this.name = name;
        this.acceptableProjects = acceptableProjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getAcceptableProjects() {
        return acceptableProjects;
    }

    public void setAcceptableProjects(List<Project> acceptableProjects) {
        this.acceptableProjects = acceptableProjects;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', acceptableProjects=" + acceptableProjects + "}";
    }
}
