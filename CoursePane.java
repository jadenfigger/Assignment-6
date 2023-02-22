// Assignment #: Arizona State University Spring 2023 CSE205 #6
//         Name: Your Name
//    StudentID: Your ID
//      Lecture: Your lecture time (e.g., MWF 9:40am)
//  Description: //-- is where you should add your own code

//Note: when you submit on gradescope, you need to comment out the package line
//package yourPackageName;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class CoursePane extends HBox {
    // GUI components
    private ArrayList<Course> courseList;
    private VBox checkboxContainer;

    // Step 1.1: declare any necessary instance variables here
    // ----
    VBox leftPane;
    VBox centerPane;
    VBox rightPane;
    Label leftBottomMessage;
    Button addButton;
    Button dropButton;
    Label rightBottomMessage;

    GridPane leftPaneInputGrid;
    Label subjectLbl;
    Label courseNumLbl;
    Label instructorLbl;
    ComboBox<String> subjectComboBox;
    TextField courseNumField;
    TextField instructorField;

    // constructor
    public CoursePane() {
        // step 1.2: initialize instance variables
        // ----
        courseList = new ArrayList<Course>();
        checkboxContainer = new VBox();

        leftPane = new VBox();
        rightPane = new VBox();
        centerPane = new VBox();
        leftBottomMessage = new Label("");
        addButton = new Button("Add =>");
        dropButton = new Button("Drop <=");
        rightBottomMessage = new Label("Total Courses Enrolled: 0");

        leftPaneInputGrid = new GridPane();
        subjectLbl = new Label("Subject:");
        courseNumLbl = new Label("Course Num:");
        instructorLbl = new Label("Instructor:");
        subjectComboBox = new ComboBox<String>();
        courseNumField = new TextField();
        instructorField = new TextField();

        Label labelLeft = new Label("Add Course(s)");
        labelLeft.setTextFill(Color.BLUE);
        labelLeft.setFont(Font.font(null, 14));

        Label labelRight = new Label("Course(s) Enrolled");
        labelRight.setTextFill(Color.BLUE);
        labelRight.setFont(Font.font(null, 14));

        // set up the layout. Note: CoursePane is a HBox and contains
        // leftPane, centerPane and rightPane. Pick proper layout class
        // and use nested sub-pane if necessary, then add each GUI components inside.

        // step 1.3: create and set up the layout of the leftPane, leftPane contains a
        // top label, the center sub-pane
        // and a label show at the bottom
        subjectComboBox.getItems().addAll("ACC", "AME", "BME", "CHM", "CSE", "DAT", "EEE");
        subjectComboBox.setValue("CSE");

        leftPaneInputGrid.add(subjectLbl, 0, 0);
        leftPaneInputGrid.add(courseNumLbl, 0, 1);
        leftPaneInputGrid.add(instructorLbl, 0, 2);
        leftPaneInputGrid.add(subjectComboBox, 1, 0);
        leftPaneInputGrid.add(courseNumField, 1, 1);
        leftPaneInputGrid.add(instructorField, 1, 2);

        leftPane.getChildren().addAll(labelLeft, leftPaneInputGrid, leftBottomMessage);

        // step 1.4: create and set up the layout of the centerPane which holds the two
        // buttons
        // ----
        centerPane.getChildren().addAll(addButton, dropButton);

        // step 1.5: create and set up the layout of the rightPane, rightPane contains a
        // top label,
        // checkboxContainer and a label show at the bottom
        // ----
        rightPane.getChildren().addAll(labelRight, checkboxContainer, rightBottomMessage);

        // CoursePane is a HBox. Add leftPane, centerPane and rightPane inside
        this.getChildren().addAll(leftPane, centerPane, rightPane);
        this.setPadding(new Insets(10, 10, 10, 10));
        // ----

        // Step 3: Register the GUI component with its handler class
        // ----
        addButton.setOnAction(new ButtonHandler());
        dropButton.setOnAction(new ButtonHandler());
        subjectComboBox.setOnAction(new ComboBoxHandler());
        courseNumField.setOnAction(new TextFieldHandler());
        checkboxContainer.setRotationAxis(getRotationAxis());

    } // end of constructor

    // step 2.1: Whenever a new Course is added or one or several courses are
    // dropped/removed, this method will
    // 1) clear the original checkboxContainer;
    // 2) create a CheckBox for each Course object inside the courseList, and also
    // add it inside the checkboxContainer;
    // 3) register the CheckBox with the CheckBoxHandler.
    public void updateCheckBoxContainer() {
        checkboxContainer.getChildren().clear();
        for (int i = 0; i < courseList.size(); i++) {
            CheckBox checkBox = new CheckBox(courseList.get(i).toString());
            checkboxContainer.getChildren().add(checkBox);
            checkBox.setOnAction(new CheckBoxHandler(courseList.get(i)));
        }
        rightBottomMessage.setText("Total Course Enrolled: " + courseList.size());
    }

    // Step 2.2: Create a ButtonHandler class
    private class ButtonHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            // ----

            try {
                if (e.getSource() == addButton) {
                    
                    // Check whether all fields are filled
                    if (courseNumField.getText().equals("") || instructorField.getText().equals("")) {
                        System.out.println("All fields must be filled");
                        leftBottomMessage.setText("All fields must be filled");
                        leftBottomMessage.setTextFill(Color.RED);
                    }
                    
                    // need to check whether the course already exist inside the courseList or not
                    Course newCourse = new Course(subjectComboBox.getValue(),
                            Integer.parseInt(courseNumField.getText()), instructorField.getText());
                    
                    
                    if (!courseList.contains(newCourse)) {
                        courseList.add(newCourse);
                        updateCheckBoxContainer();
                        leftBottomMessage.setText("Course added successfully!");
                        leftBottomMessage.setTextFill(Color.BLUE);
                    } 
                    else // a duplicated one
                    {
                        System.out.println("Duplicate");
                        leftBottomMessage.setText("Course already exists - Not added");
                        leftBottomMessage.setTextFill(Color.RED);
                    }

                }                    
                else if (e.getSource() == dropButton) {
                    int numDropped = 0; // integer variable used to keep track of courses being dropped
                    for (int i = 0; i < checkboxContainer.getChildren().size(); i++) {
                        CheckBox cb = (CheckBox) checkboxContainer.getChildren().get(i);
                        if (cb.isSelected()) {
                            courseList.remove(i);
                            updateCheckBoxContainer();
                            i--;
                            numDropped++;
                        }
                    }
                    if (numDropped == 1) {
                        // message to let user know course was dropped successfully
                        leftBottomMessage.setText("Course dropped sucessfully");
                        leftBottomMessage.setTextFill(Color.BLUE);
                    }
                    else if (numDropped > 1) {
                        // message to let user know the courses were dropped successfully
                        leftBottomMessage.setText("Courses dropped sucessfully");
                        leftBottomMessage.setTextFill(Color.BLUE);
                    }
                    else {
                        // message to let user know to check boxes to drop courses
                        leftBottomMessage.setText("Please select the Course(s) you would like to drop");
                        leftBottomMessage.setTextFill(Color.RED);
                    }
                } else // for all other invalid actions, thrown an exception and it will be caught
                {
                    throw new Exception("Invalid Action!");
                }

                // Clear all the text fields and prepare for the next entry;
                // ----
                subjectComboBox.setValue("CSE");
                courseNumField.setText("");
                instructorField.setText("");

            } // end of try

            catch (NumberFormatException exception) {
                leftBottomMessage.setText("Course Num must be an integer - Not added");
                leftBottomMessage.setTextFill(Color.RED);
            } catch (Exception exception) {
                leftBottomMessage.setText(exception.getMessage());
                leftBottomMessage.setTextFill(Color.RED);
            }
        } // end of handle() method
    } // end of ButtonHandler class

    //// Step 2.3: A ComboBoxHandler
    private class ComboBoxHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            if (e.getSource() == subjectComboBox) {
                leftBottomMessage.setText("");
            }
        }
    }// end of ComboBoxHandler

    // Used to reset the value of leftBottomMessage when the user clicks on the field
    private class TextFieldHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            leftBottomMessage.setText("");
        }
    }// end of ComboBoxHandler

    // Step 2.4: A CheckBoxHandler
    private class CheckBoxHandler implements EventHandler<ActionEvent> {
        // Pass in a Course object so that we know which course is associated with which
        // CheckBox
        private Course oneCourse;

        // constructor
        public CheckBoxHandler(Course oneCourse) {
            this.oneCourse = oneCourse;
        }

        public void handle(ActionEvent e) {
            CheckBox cb = (CheckBox) e.getSource();
            if (cb.isSelected()) {
                cb.setSelected(true);
            } else {
                cb.setSelected(false);
            }
        }
    }// end of CheckBoxHandler

} // end of CoursePane class