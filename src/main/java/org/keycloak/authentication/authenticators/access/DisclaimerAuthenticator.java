package org.keycloak.authentication.authenticators.access;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public class DisclaimerAuthenticator implements Authenticator {
    public static final DisclaimerAuthenticator SINGLETON = new DisclaimerAuthenticator();

    @Override
    public void close() {
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        Response challengeResponse = context.form()
                .setExecution(context.getExecution().getId())
                .createForm("disclaimer.ftl");
        context.challenge(challengeResponse);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        if (!formData.containsKey("accept")) {
            context.cancelLogin();
            return;
        }

        context.success();
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

}
