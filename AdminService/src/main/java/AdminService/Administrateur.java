package AdminService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class Administrateur {
    @PersistenceContext
    private EntityManager em;

    public Utilisateur creerUtilisateur(String nom) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setLimcoins(1000);
        em.persist(utilisateur);
        return utilisateur;
    }

    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    public List<Utilisateur> getUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    public Utilisateur findUtilisateurById(Long id) {
        return em.find(Utilisateur.class, id);
    }

    @Transactional
    public void updateUtilisateur(Utilisateur utilisateur) {
        Utilisateur existant = em.find(Utilisateur.class, utilisateur.getId());
        if (existant == null) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + utilisateur.getId() + " n'existe pas.");
        }
        existant.setNom(utilisateur.getNom());
        existant.setLimcoins(utilisateur.getLimcoins());
        em.merge(existant); // Mettre à jour l'utilisateur
    }


}
