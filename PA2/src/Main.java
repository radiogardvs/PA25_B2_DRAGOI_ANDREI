import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Project p1 = new Project("P1", ProjectType.THEORETICAL);
        Project p2 = new Project("P2", ProjectType.PRACTICAL);
        Project p3 = new Project("P3", ProjectType.THEORETICAL);
        Project p4 = new Project("P4", ProjectType.PRACTICAL);

        Student s1 = new Student("S1", Arrays.asList(p1, p2));
        Student s2 = new Student("S2", Arrays.asList(p1, p3));
        Student s3 = new Student("S3", Arrays.asList(p3, p4));
        Student s4 = new Student("S4", Arrays.asList(p1, p4));

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
    }
}
