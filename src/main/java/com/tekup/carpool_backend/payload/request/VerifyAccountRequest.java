package com.tekup.carpool_backend.payload.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyAccountRequest {
    private String email;
    private String otp;
}
