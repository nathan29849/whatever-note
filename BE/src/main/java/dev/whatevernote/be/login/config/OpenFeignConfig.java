package dev.whatevernote.be.login.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("dev.whatevernote")
public class OpenFeignConfig {

}
