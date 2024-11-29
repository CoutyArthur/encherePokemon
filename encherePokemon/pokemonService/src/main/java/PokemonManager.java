import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class PokemonManager {
    @Inject
    EntityManager entityManager;

    @Transactional
    public Pokemon getPokemon(long id){
        return entityManager.find(Pokemon.class, id);
    }

    @Transactional
    public void addPokemon(Pokemon pokemon){
        entityManager.persist(pokemon);
    }



}
