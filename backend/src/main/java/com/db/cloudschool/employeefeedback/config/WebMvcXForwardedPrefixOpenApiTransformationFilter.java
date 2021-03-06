package com.db.cloudschool.employeefeedback.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class WebMvcXForwardedPrefixOpenApiTransformationFilter implements WebMvcOpenApiTransformationFilter {
    private static final String X_FORWARDED_PREFIX = "X-Forwarded-Prefix";
    private static final String COMMA = ",";

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI openApi = context.getSpecification();
        context.request().ifPresent(httpServletRequest -> {
            String xForwardedPrefix = httpServletRequest.getHeader(X_FORWARDED_PREFIX);
            if (xForwardedPrefix != null && !xForwardedPrefix.isEmpty()) {
                String[] prefixArr = xForwardedPrefix.split(COMMA);
                if (prefixArr.length > 0) {
                    String prefix = fixup(prefixArr[0]);
                    List<Server> servers = openApi.getServers();
                    for (Server server : servers) {
                        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(server.getUrl());
                        uriComponentsBuilder.path(prefix);
                        UriComponents uriComponents = uriComponentsBuilder.build();
                        server.setUrl(uriComponents.toUriString());
                    }
                }
            }
        });
        return openApi;
    }

    @Override
    public boolean supports(@NonNull DocumentationType delimiter) {
        return delimiter == DocumentationType.OAS_30;
    }

    private String fixup(String path) {
        if(path != null) {
            if (path.isEmpty()
                    || "/".equals(path)
                    || "//".equals(path)) {
                return "/";
            }
            return StringUtils.trimTrailingCharacter(path.replace("//", "/"), '/');
        }
        else return null;
    }
}
