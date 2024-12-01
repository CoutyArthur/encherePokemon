package UserService;

public class PokemonDTO {
    private Long id;
    private String nom;
    private String description;
    private int valeurReelle;

    // Constructeurs
    public PokemonDTO() {}

    public PokemonDTO(Long id, String nom, String description, int valeurReelle) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.valeurReelle = valeurReelle;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValeurReelle() {
        return valeurReelle;
    }

    public void setValeurReelle(int valeurReelle) {
        this.valeurReelle = valeurReelle;
    }
}
