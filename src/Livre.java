import java.time.LocalDate;

public class Livre {
    private String titre;
    private String auteur;
    private int anneePublication;
    private boolean disponible;
    private String emprunteur;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    // Constructeur
    public Livre(String titre, String auteur, int anneePublication) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.disponible = true; // Par défaut, un livre est disponible
        this.emprunteur = null;
        this.dateEmprunt = null;
        this.dateRetour = null;
    }

    // Getters
    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getEmprunteur() {
        return emprunteur;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    // Setters
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setEmprunteur(String emprunteur) {
        this.emprunteur = emprunteur;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    // Méthode pour afficher les informations du livre
    @Override
    public String toString() {
        return "Titre: " + titre + ", Auteur: " + auteur + ", Année: " + anneePublication +
                ", Disponible: " + (disponible ? "Oui" : "Non (Emprunté par " + emprunteur + ")") +
                (dateEmprunt != null ? ", Emprunté le: " + dateEmprunt : "") +
                (dateRetour != null ? ", Retour prévu le: " + dateRetour : "");
    }
}
