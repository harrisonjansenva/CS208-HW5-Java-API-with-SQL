package cs208;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;


@RestController
public class RegisteredStudentsController
{

    /**
     * GET /registered_students
     *
     * @return a list of registered students (extracted from a join between
     * registered_students, students and classes tables in the database) as JSON
     */
    @GetMapping(value = "/registered_students", produces = MediaType.APPLICATION_JSON_VALUE)
    ArrayList<RegisteredStudentJoinResult> registered_students()
    {
        ArrayList<RegisteredStudentJoinResult> listOfRegisteredStudentJoinResults = Main.database.listAllRegisteredStudents();

        return listOfRegisteredStudentJoinResults;
    }


    /**
     * POST /add_student_to_class
     * with the following form parameters:
     *      studentId
     *      classId
     *
     * The parameters passed in the body of the POST request will be inserted
     * into the registered_students table in the database.
     */
    // TODO: implement this route
    @PostMapping("/add_student_to_class")
    String register(
            @RequestParam("studentID") int studentID,
            @RequestParam("classID") int classID
    )
    {
        System.out.println("student id: " + studentID);
        System.out.println("id of class to register in: " + classID);

        Student testStudent = Main.database.getStudentWithID(studentID);
        Class testClass = Main.database.getClassWithId(classID);

        if (testStudent == null || testClass == null) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student or class not found");
        }

        try {
            Main.database.addStudentToClass(studentID, classID);
            return "Student with id " + studentID + " registered successfully in class " + classID;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to add student to class " + classID
                    );
        }
    }





    /**
     * DELETE /drop_student_from_class
     * with the following form parameters:
     *      studentId
     *      classId
     *
     * Deletes the student with id = {studentId} from the class with id = {classId}
     * from the registered_students in the database.
     *
     * @throws ResponseStatusException: a 404 status code if the student with id = {studentId} does not exist
     * @throws ResponseStatusException: a 404 status code if the class with id = {classId} does not exist
     */
    // TODO: implement this route

    @DeleteMapping("/drop_student_from_class")
    String remove_from_class(
            @RequestParam("studentID") int studentID,
            @RequestParam("classID") int classID
    )
    {
        System.out.println("student id: " + studentID);
        System.out.println("id of class to remove from: " + classID);
        Student testStudent = Main.database.getStudentWithID(studentID);
        Class testClass = Main.database.getClassWithId(classID);
        if (testStudent == null || testClass == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student or class not found");
        }
        try {
            Main.database.dropStudentFromClass(studentID, classID);
            return "Student with id " + studentID + " removed from the class " + classID;

        } catch (SQLException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to remove student from class " + classID
            );
        }
    }



    /**
     * GET /students_taking_class/{classCode}
     *
     * @return a list of registered students (extracted from a join between
     * registered_students, students and classes tables in the database) as JSON
     * that are taking the class {classCode}
     */
    // TODO: implement this route



    /**
     * GET /classes_in_which_student_is_enrolled/{studentId}
     *
     * @return a list of all classes (extracted from a join between
     * registered_students, students and classes tables in the database) as JSON
     * in which the student with id = {studentId} is enrolled
     *
     * @throws ResponseStatusException: a 404 status code if the student with id = {studentId} does not exist
     */
    // TODO: implement this route

}
