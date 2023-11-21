package com.tekup.carpool_backend.payload.request;


import com.tekup.carpool_backend.validation.password.PasswordMatching;
import com.tekup.carpool_backend.validation.password.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatching(
        password = "newPassword",
        confirmPassword = "confirmationPassword",
        message = "Password and Confirm Password must be matched!"
)
public class ResetPasswordRequest {

    @NotBlank(message = "New password is required")
    @StrongPassword
    private String newPassword;

    @NotBlank(message = "Confirmation password is required")
    private String confirmationPassword;
}