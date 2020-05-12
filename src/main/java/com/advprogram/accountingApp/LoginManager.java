package main.java.com.advprogram.accountingApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

/** Manages control flow for logins */
public class LoginManager extends Node {
    private final String[] urls = {"src/main/resources/files/professorInfo.txt",
            "src/main/resources/files/studentInfo.txt", "src/main/resources/files/courseInfo.txt"};
    protected ArrayList<Professor> professorList = new ArrayList<>();
    protected ArrayList<Student> studentList = new ArrayList<>();
    protected ArrayList<Course> courseList = new ArrayList<>();
    private final Scene scene;

    public ArrayList<Professor> getProfessorList() { return professorList; }

    public ArrayList<Student> getStudentList() { return studentList; }

    public ArrayList<Course> getCourseList() { return courseList; }

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    public void createArrayLists() throws FileNotFoundException {
        Scanner professorSc = new Scanner(new File(urls[0]));
        Scanner studentSc = new Scanner(new File(urls[1]));
        Scanner courseSc = new Scanner(new File(urls[2]));
        String line;
        while (professorSc.hasNextLine()) {
            line = professorSc.nextLine();
            Professor professor = new Professor();
            professor.setName(line.split("@")[0]);
            professor.setId(line.split("@")[1]);
            professor.setPass(line.split("@")[2]);
            professorList.add(professor);
        }
        while (studentSc.hasNextLine()) {
            line = studentSc.nextLine();
            Student student = new Student();
            student.setName(line.split("@")[0]);
            student.setId(line.split("@")[1]);
            student.setPass(line.split("@")[2]);
            student.setSex(line.split("@")[3]);
            studentList.add(student);
        }
        while (courseSc.hasNextLine()) {
            line = courseSc.nextLine();
            Course course = new Course();
            course.setCourseName(line.split("@")[0]);
            course.setCourseId(line.split("@")[1]);
            courseList.add(course);
        }
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void authenticatedAccountant(String sessionID) {
        showAccountantView(sessionID);
    }

    public void authenticatedProfessor(String sessionID) { showProfessorView(sessionID); }

    public void authenticatedStudent(String sessionID) { showStudentView(sessionID); }

    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("loginView.fxml")
            );
            scene.setRoot(loader.load());
            LoginController controller =
                    loader.<LoginController>getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAccountantView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("accountantView.fxml")
            );
            scene.setRoot(loader.load());
            AccountantController controller =
                    loader.<ManagerController>getController();
            controller.initSessionID(this, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showProfessorView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("professorView.fxml")
            );
            scene.setRoot(loader.load());
            ProfessorController controller =
                    loader.getController();
            controller.initSessionID(this, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showStudentView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("studentView.fxml")
            );
            scene.setRoot(loader.load());
            StudentController controller =
                    loader.getController();
            controller.initSessionID(this, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}