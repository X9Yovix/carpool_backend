package com.tekup.carpool_backend.payload.response;

import com.tekup.carpool_backend.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String lastName;
    private String firstName;
    private UserRole role;
}
