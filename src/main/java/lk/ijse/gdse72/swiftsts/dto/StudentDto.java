package lk.ijse.gdse72.swiftsts.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {
    private String studentId;
    private String studentName;
    private String parentName;
    private String address;
    private String email;
    private String studentGrade;
    private String phoneNo;
    private String userId;
    private double creditBalance;

}
