package com.briandenicola.azure.kubernetes;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.*;

import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

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
            final String url = System.getenv("AKS_API_URL");

            final String namespace = "default";

            ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientID)
                .clientSecret(clientSecret)
                .tenantId(tenantID)
                .build();

            String[] scopes = {"6dae42f8-4368-4678-94ff-3960e28e3630"};
            TokenRequestContext request = new TokenRequestContext();
            request.addScopes(scopes);

            String token = clientSecretCredential.getToken(request).toString();
            
            ConfigBuilder builder = new ConfigBuilder()
                .withTrustCerts(true)
                .withMasterUrl(url)
                .withOauthToken(token)
                .withNamespace(namespace);

            Config config = builder.build();

            final KubernetesClient client = new DefaultKubernetesClient(config);
            
            PodList pods = client.pods().inNamespace(namespace).list();
            System.out.println(String.format("Pods listed in %s namespace:", namespace));
            pods.getItems().forEach(obj -> System.out.println(obj.getMetadata().getName()));
                       
            client.close();

        } catch (Exception e) {};
    }

}
