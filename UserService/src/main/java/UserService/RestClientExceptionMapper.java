package UserService;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class RestClientExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    @Override
    public RuntimeException toThrowable(Response response) {
        // Gère les erreurs HTTP
        if (response.getStatus() == 404) {
            return new RuntimeException("Ressource non trouvée : " + response.readEntity(String.class));
        } else if (response.getStatus() == 500) {
            return new RuntimeException("Erreur interne du serveur : " + response.readEntity(String.class));
        } else {
            return new RuntimeException("Erreur REST : " + response.getStatus());
        }
    }
}
