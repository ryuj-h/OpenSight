package org.example.b104.domain.user.service.command;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class FaceLoginCommand {
    private MultipartFile requestImage;
}
