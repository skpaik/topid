package io.goribco.apis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.Optional;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Top Profile",
                        email = "contact@topprofile.com",
                        url = "https://topprofile.com/course"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - Top Profile",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "DEV ENV",
                        url = "https://dev.toprofile.com"
                ),
                @Server(
                        description = "QA ENV",
                        url = "https://qa.toprofile.com"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://toprofile.com/course"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
class OpenApiConfig {
    public static final String TEST_HEADER = "test-header";

    @Bean
    public OpenAPI springOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                //.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                /*
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                */
                /*
                .info(new Info()
                        .title("Micro service")
                        .description("APIs for Test Console service")
                        .version("1.0")
                        .license(new License()
                                .name("Dev Team")
                                .url("https://github.com")
                        )
                )
                */
                .externalDocs(new ExternalDocumentation()
                        .description("Test Documentation")
                        .url("https://github.com")
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("test-api")
                .displayName("Test API")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OperationCustomizer customGlobalHeaders() {

        return (Operation operation, HandlerMethod handlerMethod) -> {
            Optional<List<Parameter>> isParameterPresent = Optional.ofNullable(operation.getParameters());
            Boolean isTestHeaderPresent = Boolean.FALSE;

            if (isParameterPresent.isPresent()) {
                isTestHeaderPresent = isParameterPresent.get().stream()
                        .anyMatch(param -> param.getName().equalsIgnoreCase(TEST_HEADER));
            }

            if (Boolean.FALSE.equals(isTestHeaderPresent)) {
                Parameter remoteUser = new Parameter()
                        .in(ParameterIn.HEADER.toString())
                        .schema(new StringSchema())
                        .name(TEST_HEADER)
                        .description("Test Header")
                        .required(true);

                operation.addParametersItem(remoteUser);
            }
            return operation;
        };
    }
}
