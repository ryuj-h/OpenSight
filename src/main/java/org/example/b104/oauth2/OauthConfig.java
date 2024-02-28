package org.example.b104.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties
public class OauthConfig {
    private final OauthProperties oauthProperties;
}
