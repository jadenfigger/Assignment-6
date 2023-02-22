// Assignment #: Arizona State University Spring 2023 CSE205 #6
//         Name: Your Name
//    StudentID: Your ID
//      Lecture: Your lecture time (e.g., MWF 9:40am)
//  Description:

//Note: when you submit on gradescope, you need to comment out the package line
// package yourPackageName;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Assignment6 extends Application {
    public static final int WIDTH = 600, HEIGHT = 400;

    public void start(Stage stage) {
        
        // Course c1 = new Course("cse", 1, "test");
        // Course c2 = new Course("cse", 2, "test");
        // Course c3 = new Course("cse", 3, "test");
        // Course c4 = new Course("cse", 5, "test");

        // ArrayList<Course> l = new ArrayList<Course>();
        // l.add(c1);
        // l.add(c3);
        // l.add(c3);

        // System.out.println(l.contains(c4));


        StackPane root = new StackPane();
        CoursePane coursePane = new CoursePane();
        root.getChildren().add(coursePane);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("ASU Course Enrollment System");
        stage.setScene(scene);
        stage.show();
    }

    // public static void main(String[] ar // {
    // launch(args);
    // }
}