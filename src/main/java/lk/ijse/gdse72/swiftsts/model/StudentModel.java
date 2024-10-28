package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.StudentDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentModel {
    public ArrayList <StudentDto> getAllStudents() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Student");
        ArrayList<StudentDto> studentDtos = new ArrayList<>();

        while (rst.next()){
            StudentDto studentDto = new StudentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
            studentDtos.add(studentDto);
        }

        return studentDtos;
    }

    public String getNextStudentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT studentId FROM Student ORDER BY studentId DESC LIMIT 1");

        if (rst.next()){
            String lastiD = rst.getString(1);
            String substring = lastiD.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);

        }
        return "S001";
    }

    public boolean saveStudent(StudentDto studentDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?,?)",
                studentDto.getStudentId(),
                studentDto.getStudentName(),
                studentDto.getParentName(),
                studentDto.getAddress(),
                studentDto.getEmail(),
                studentDto.getStudentGrade(),
                studentDto.getPhoneNo()
        );
    }

    public boolean updateStudent(StudentDto studentDto) throws SQLException {
        return CrudUtil.execute("UPDATE Student SET StudentName=?, ParentName=?, Address=?, Email=?, StudentGrade=?, ContactNo=? WHERE StudentId=?",
                studentDto.getStudentName(),
                studentDto.getParentName(),
                studentDto.getAddress(),
                studentDto.getEmail(),
                studentDto.getStudentGrade(),
                studentDto.getPhoneNo(),
                studentDto.getStudentId()
        );
    }

    public boolean deleteStudent(String studentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Student WHERE StudentId=?", studentId);
    }
}
