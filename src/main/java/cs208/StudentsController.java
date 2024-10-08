package cs208;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class StudentsController
{

    /**
     * GET /students
     *
     * @return a list of students (extracted from the students table in the database) as JSON
     */

    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Student> index()
    {
        List<Student> listOfStudents = Main.database.listAllStudents();

        return listOfStudents;
    }




    /**
     * GET /students/{id}
     *
     * @return the student with id = {id} (extracted from the students table in the database) as JSON
     *
     * @throws ResponseStatusException: a 404 status code if the student with id = {id} does not exist
     */
    // TODO: implement this route
    @GetMapping(value = "/students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Student show(@PathVariable("id") int id)
    {
        System.out.println("id = " + id);

        Student studentWithID = Main.database.getStudentWithID(id);
        if (studentWithID == null) {
            System.out.println("student with id " + id + " not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "student with id " + id + " not found"
            );
        }
        return studentWithID;
    }




    /**
     * POST /students
     * with the following form parameters:
     *      firstName
     *      lastName
     *      birthDate (in ISO format: yyyy-mm-dd)
     *
     * The parameters passed in the body of the POST request are used to create a new student.
     * The new student is inserted into the students table in the database.
     *
     * @return the created student (which was inserted into the database), as JSON
     */
    // TODO: implement this route
    @PostMapping("/students")
    Student create(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate
    ) {
        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);
        System.out.println("birthDate = " + birthDate);
        Date newBirthDate = Date.valueOf(birthDate);

        try {
            Student createdStudent = new Student(firstName, lastName, newBirthDate);
            Main.database.addNewStudent(createdStudent);
            return createdStudent;
        }
        catch (Exception e) {
        throw new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Failed to create student"
        );

        }
    }






    /**
     * PUT /students/{id}
     * with the following form parameters:
     *      firstName
     *      lastName
     *      birthDate
     *
     * The parameters passed in the body of the PUT request are used to
     * update the existing student with id = {id} in the students table in the database.
     *
     * @return the updated student as JSON
     *
     * @throws ResponseStatusException: a 404 status code if the student with id = {id} does not exist
     */
    // TODO: implement this route
    @PutMapping(value = "/students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Student update(
            @PathVariable("id") int id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate
    )
    {
        System.out.println("id          = " + id);
        System.out.println("firstName   = " + firstName);
        System.out.println("lastName    = " + lastName);
        System.out.println("birthDate   = " + birthDate);

        try {
            Student updatedStudent = Main.database.getStudentWithID(id);
            if (updatedStudent == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "student with id " + id + " not found"
                );
            }
            updatedStudent.setFirstName(firstName);
            updatedStudent.setLastName(lastName);
            updatedStudent.setBirthDate(Date.valueOf(birthDate));
            Main.database.updateExistingStudent(updatedStudent);
            return updatedStudent;
        }
        catch (SQLException e) {
        throw new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Failed to update student with id" + id + " in the database"
        );
        }
        }





    /**
     * PATCH /students/{id}
     * with the following optional form parameters:
     *      firstName
     *      lastName
     *      birthDate
     *
     * The optional parameters passed in the body of the PATCH request are used to
     * update the existing student with id = {id} in the students table in the database.
     *
     * @return the updated student as JSON
     *
     * @throws ResponseStatusException: a 404 status code if the student with id = {id} does not exist
     */
    // TODO: implement this route




    /**
     * DELETE /students/{id}
     *
     * Deletes the student with id = {id} from the students table in the database.
     *
     * @throws ResponseStatusException: a 404 status code if the student with id = {id} does not exist
     */
    // TODO: implement this route
    @DeleteMapping(value = "/students/{id}")
    String delete(@PathVariable("id") int id)
    {
        System.out.println("id = " + id);
        try
        {
            Student studentToDelete = Main.database.getStudentWithID(id);
            if (studentToDelete == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "student with id " + id + " not found"
                );
            }
            Main.database.deleteExistingStudent(id);
        } catch (SQLException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to delete student with id" + id + " in the database"
            );
        }
        return "student with id " + id + " deleted";
    }

}
