package web.app.payload.request;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
