package StudentManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class student_detail {
    private String name;
    private String id;
    private String courses;
    private int age;

    // Database credentials (update if needed)
    public static final String DB_URL = "jdbc.jdbc:mysql://localhost:3306/student";
    public static final String USER = "root";
    public static final String PASS = "root";

    public student_detail(String name, int age, String id, String courses)
    {
        this.name = name;
        this.id = id;
        this.courses = courses;
        this.age = age;
    }

    // Getters and Setters
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getCourses() {
        return courses;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    // Insert Student
    public void ins(student_detail student) {
        String sql = "INSERT INTO st (name, id, age, courses) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getId());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getCourses());

            int i = ps.executeUpdate();
            System.out.println(i > 0 ? "Data inserted successfully." : "Insert failed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Student
    public void del(student_detail student) {
        String sql = "DELETE FROM st WHERE name=? AND id=? AND age=? AND courses=?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getId());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getCourses());

            int i = ps.executeUpdate();
            System.out.println(i > 0 ? "Data deleted successfully." : "Delete failed (student not found).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch Student
    public void fetch(student_detail student) {
        String sql = "SELECT name, id, age, courses FROM st WHERE id=? AND age=? AND name=? AND courses=?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getId());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getName());
            ps.setString(4, student.getCourses());

            ResultSet rs = ps.executeQuery();
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("Name   : " + rs.getString("name"));
                System.out.println("ID     : " + rs.getString("id"));
                System.out.println("Age    : " + rs.getInt("age"));
                System.out.println("Course : " + rs.getString("courses"));
                System.out.println("------------------------");
            }

            if (!found) {
                System.out.println("No matching student found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Student Course
    public void upd(student_detail student) {
        String sql = "UPDATE st SET courses=? WHERE id=?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getCourses());
            ps.setString(2, student.getId());

            int i = ps.executeUpdate();
            System.out.println(i > 0 ? "Data updated successfully." : "Update failed (student not found).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main Method - Demo
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select an operation:\n1. Insert\n2. Fetch\n3. Update\n4. Delete");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Course: ");
        String course = scanner.nextLine();

        student_detail student = new student_detail(name, age, id, course);

        switch (choice) {
            case 1:
                student.ins(student);
                break;
            case 2:
                student.fetch(student);
                break;
            case 3:
                System.out.print("Enter new Course to update: ");
                String newCourse = scanner.nextLine();
                student.setCourses(newCourse);
                student.upd(student);
                break;
            case 4:
                student.del(student);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}