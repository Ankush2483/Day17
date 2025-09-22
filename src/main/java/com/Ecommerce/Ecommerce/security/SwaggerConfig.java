package com.Ecommerce.Ecommerce.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig(){
            return new OpenAPI().info(
                    new Info().title("ECommerce App APIs")
                            .description("By Ankush "))
                    .servers(List.of(new Server().url("http://localhost:8081").description("local"),
                    (new Server().url("http://localhost:8082").description("live"))))
                    .tags(List.of(new Tag().name("Auth APIs"),
                            new Tag().name("User APIs")))
                    .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                    .components(new Components().addSecuritySchemes(
                            "bearerAuth",new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                                    .in(SecurityScheme.In.HEADER)
                                    .name("Authorization")
                    ));
    }
}
