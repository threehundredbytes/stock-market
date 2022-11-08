package ru.dreadblade.stockmarket.userservice.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;

    @Value("${app.auth-server.realm}")
    private String realmName;

    @Value("${app.auth-server.client.frontend.id}")
    private String frontendClientId;

    public void createUser(String username, String password, String roleName) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(username);
        userRepresentation.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        RealmResource realmResource = keycloak
                .realm(realmName);

        Response response = realmResource
                .users()
                .create(userRepresentation);

        int responseStatus = response.getStatus();

        if (responseStatus == HttpStatus.CONFLICT.value()) {
            response.close();

            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        String userId = CreatedResponseUtil.getCreatedId(response);
        response.close();

        ClientRepresentation frontendClient = realmResource.clients()
                .findByClientId(frontendClientId).get(0);

        RoleRepresentation roleRepresentation = realmResource
                .clients().get(frontendClient.getId())
                .roles().get(roleName)
                .toRepresentation();

        realmResource.users().get(userId)
                .roles()
                .clientLevel(frontendClient.getId())
                .add(List.of(roleRepresentation));
    }
}
