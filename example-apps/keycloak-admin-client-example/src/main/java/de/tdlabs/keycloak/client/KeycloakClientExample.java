package de.tdlabs.keycloak.client;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Arrays;

/**
 * Simple example for using the Keycloak Admin Client API.
 * <p>
 * Note that this operations require the {@code manage-users} as well as the {@code view-realm} roles.
 * </p>
 */
public class KeycloakClientExample {

    public static void main(String[] args) {

        String serverUrl = "http://login.acme.local:8081/auth";
        String realmName = "demo";
        String username = "idm-admin";
        String password = "test";
        String clientId = "admin-client";
        String clientSecret = "3c343ed1-2979-4e84-aa12-e3f9dbe39e76";

        Keycloak keycloak = Keycloak.getInstance(serverUrl, realmName, username, password, clientId, clientSecret);

        UserRepresentation user = new UserRepresentation();
        user.setUsername("user1");
        user.setFirstName("First1");
        user.setLastName("Lastname1");
        user.setEmail("tom+user1@localhost");
        user.setEnabled(true);

        Response createUserResponse = keycloak.realm(realmName).users().create(user);
        createUserResponse.close();

        String userId = getCreatedId(createUserResponse);
        System.out.printf("User created with id: %s%n", userId);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setValue("test");
        passwordCred.setType(CredentialRepresentation.PASSWORD);

        keycloak.realm(realmName).users().get(userId).resetPassword(passwordCred);
        System.out.printf("Password for user with id: %s reseted%n", userId);


        RoleRepresentation userRealmRole = keycloak.realm(realmName).roles().get("user").toRepresentation();
        keycloak.realm(realmName).users().get(userId).roles().realmLevel().add(Arrays.asList(userRealmRole));
        System.out.printf("Granted user realm role to user with id: %s%n", userId);
    }

    public static String getCreatedId(Response response) {

        URI location = response.getLocation();

        if (!response.getStatusInfo().equals(Response.Status.CREATED)) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new WebApplicationException("Create method returned status " +
                    statusInfo.getReasonPhrase() + " (Code: " + statusInfo.getStatusCode() + "); expected status: Created (201)", response);
        }

        if (location == null) {
            return null;
        }

        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
