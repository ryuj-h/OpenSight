package org.example.b104.domain.user.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmSimplePasswordCommand {
    private String simplePassword;
}
