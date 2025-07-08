package io.goribco.apis.model.authmodels.users;

import io.goribco.core.response.BaseReq;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBase extends BaseReq {
    @Indexed(unique = true)
    @NotNull
    private String username;
    @Email
    private String email;
    private String phone;
    private String businessTitle;
    private String firstName;
    private String middleName;
    private String lastName;

    public String getFullName() {
        StringBuilder builder = new StringBuilder();

        if (firstName != null) {
            builder.append(firstName);
        }

        if (middleName != null) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(middleName);
        }

        if (lastName != null) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(lastName);
        }

        return builder.toString();
    }
}
