package com.yiannisftiti.prog6.keepdishesgoing.shared.adapter.out;

import com.yiannisftiti.prog6.keepdishesgoing.shared.domain.UserRoles;
import com.yiannisftiti.prog6.keepdishesgoing.shared.port.out.CreateKeycloakUserPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

//USED GPT FOR THIS, CREDIT GOES TO IT.
@Component
public class KeycloakUserCreatorAdapter implements CreateKeycloakUserPort {

    @Value("${keycloak.admin.url}")
    private String keycloakAdminUrl;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void createUser(String email, String password, UserRoles role) {
        // 1️⃣ Get admin access token
        String tokenUrl = keycloakAdminUrl + "/realms/master/protocol/openid-connect/token";

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String tokenBody = String.format(
                "grant_type=password&client_id=admin-cli&username=%s&password=%s",
                adminUsername, adminPassword
        );

        HttpEntity<String> tokenRequest = new HttpEntity<>(tokenBody, tokenHeaders);
        ResponseEntity<Map> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequest, Map.class);

        String accessToken = (String) tokenResponse.getBody().get("access_token");

        // 2️⃣ Create the user
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);
        userHeaders.setContentType(MediaType.APPLICATION_JSON);

        String createUserUrl = keycloakAdminUrl + "/admin/realms/" + realm + "/users";

        String userJson = """
        {
          "username": "%s",
          "email": "%s",
          "enabled": true,
          "credentials": [{
              "type": "password",
              "value": "%s",
              "temporary": false
          }]
        }
        """.formatted(email, email, password);

        HttpEntity<String> userRequest = new HttpEntity<>(userJson, userHeaders);
        ResponseEntity<Void> createResponse =
                restTemplate.exchange(createUserUrl, HttpMethod.POST, userRequest, Void.class);

        // 3️⃣ Find the created user’s ID (to assign a role)
        String searchUrl = createUserUrl + "?username=" + email;
        ResponseEntity<List> searchResponse = restTemplate.exchange(searchUrl, HttpMethod.GET,
                new HttpEntity<>(userHeaders), List.class);

        if (searchResponse.getBody() == null || searchResponse.getBody().isEmpty()) {
            throw new RuntimeException("User not found after creation in Keycloak");
        }

        Map<String, Object> user = (Map<String, Object>) searchResponse.getBody().get(0);
        String userId = (String) user.get("id");

        // 4️⃣ Get the "owner" role definition
        String rolesUrl = keycloakAdminUrl + "/admin/realms/" + realm + "/roles/" + role.getKeycloakName();
        ResponseEntity<Map> roleResponse = restTemplate.exchange(rolesUrl, HttpMethod.GET,
                new HttpEntity<>(userHeaders), Map.class);

        Map<String, Object> ownerRole = roleResponse.getBody();

        // 5️⃣ Assign "owner" role to the user
        String assignRoleUrl = keycloakAdminUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        HttpEntity<List<Map<String, Object>>> roleAssignRequest = new HttpEntity<>(List.of(ownerRole), userHeaders);

        restTemplate.exchange(assignRoleUrl, HttpMethod.POST, roleAssignRequest, Void.class);
    }
}
