package lk.ijse.gdse72.swiftsts.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String role;
    private String email;
}
