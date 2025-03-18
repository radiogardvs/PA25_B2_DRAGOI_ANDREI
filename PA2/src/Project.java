import java.util.List;

class Project {
    private String name;
    private ProjectType type;

    public Project(String name, ProjectType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Project{name='" + name + "', type=" + type + "}";
    }
}
