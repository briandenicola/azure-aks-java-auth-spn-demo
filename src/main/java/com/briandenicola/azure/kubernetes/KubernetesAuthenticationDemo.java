package com.briandenicola.azure.kubernetes;

import com.azure.identity.*;

public final class KubernetesAuthenticationDemo {
    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            final String clientSecret = System.getenv("AAD_CLIENT_SECRET");
            final String clientID = System.getenv("AAD_CLIENT_ID");
            final String tenantID = System.getenv("AAD_CLIENT_TENANT_ID");

            ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientID)
                .clientSecret(clientSecret)
                .tenantId(tenantID)
                .build();

            //clientSecretCredential.
     
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
