import jakarta.persistence.*;

@Entity
public class Pokemon{
    @Id
    private long id;
    private String description;
    private int miseAPrix;
    private int valeurReel;

    public Pokemon(){}


    public long getId() { return id; }
    public String getDescription() { return description; }
    public int getMiseAPrix() { return miseAPrix; }
    public int getValeurReel() { return valeurReel; }

    public void setId(long id) { this.id = id; }
    public void setDescription(String description){ this.description = description; }
    public void setMiseAPrix(int miseAPrix){ this.miseAPrix = miseAPrix; }
    public void setValeurReel (int valeurReel){ this.valeurReel = valeurReel; }

}