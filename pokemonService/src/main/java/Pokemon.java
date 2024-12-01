import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Pokemon{
    @Id
    private long id;
    private String nom;
    private String description;
    private int miseAPrix;
    private int valeurReel;
    @ElementCollection
    private ArrayList<String> type = new ArrayList<>();
    private int stat;

    public Pokemon(){
    }


    public long getId() { return id; }
    public String getNom(){ return nom; }
    public String getDescription() { return description; }
    public int getMiseAPrix() { return miseAPrix; }
    public int getValeurReel() { return valeurReel; }
    public ArrayList<String> getType() { return type; }
    public int getStat() {return stat; }

    public void setId(long id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }

    public void setDescription(String description){ this.description = description; }
    public void setMiseAPrix(int miseAPrix){ this.miseAPrix = miseAPrix; }
    public void setValeurReel (int valeurReel){ this.valeurReel = valeurReel; }
    public void setType (ArrayList<String> type){ this.type = type; }
    public void setStat (int stat){ this.stat = stat; }
    public void addType(String type) { this.type.add(type); }

}