package AuthService;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(baseUri = "http://localhost:8083/admin/utilisateurs") // Base URL d'AdminService
public interface AdminClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void creerUtilisateur(UtilisateurRequest utilisateurRequest);
}
