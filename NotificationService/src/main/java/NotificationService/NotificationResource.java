package NotificationService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    NotificationService notificationService;

    @POST
    @RolesAllowed("ADMIN")
    public Response envoyerNotification(Notification notification) {
        notificationService.envoyerNotification(notification.getUtilisateurId(), notification.getContenu());
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{utilisateurId}")
    @RolesAllowed({"USER", "ADMIN"})
    public List<Notification> getNotifications(@PathParam("utilisateurId") Long utilisateurId) {
        return notificationService.getNotifications(utilisateurId);
    }

    @POST
    @Path("/favoris")
    public Response notifierFavoris(@Valid NotificationRequest notificationRequest) {
        notificationService.notifierFavoris(notificationRequest);
        return Response.ok().build();
    }

    @POST
    @Path("/encheres")
    public Response notifierEnchere(@Valid NotificationRequest notificationRequest) {
        notificationService.notifierEnchere(notificationRequest);
        return Response.ok().build();
    }

}
