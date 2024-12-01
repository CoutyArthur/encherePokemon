package UserService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@RegisterProvider(RestClientExceptionMapper.class)
@RegisterRestClient(baseUri = "http://localhost:8081") // URL d'AdminService
public interface UtilisateurClient {

    @GET
    @Path("/admin/utilisateurs/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    UtilisateurDTO getUtilisateur(@PathParam("id") Long id);

    @GET
    @Path("/admin/utilisateurs")
    @Produces(MediaType.APPLICATION_JSON)
    List<UtilisateurDTO> getUtilisateurs();

    @PUT
    @Path("/admin/utilisateurs/{id}")
    void updateUtilisateur(@PathParam("id") Long id, UtilisateurDTO utilisateur);
}
