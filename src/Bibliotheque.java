import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bibliotheque {
    private List<Livre> livres;

    public Bibliotheque() {
        this.livres = new ArrayList<>();
    }

    // Ajouter un livre
    public void ajouterLivre(Livre livre) {
        livres.add(livre);
    }

    // Modifier un livre
    public boolean modifierLivre(String titre, String nouvelAuteur, int nouvelleAnnee) {
        Livre livre = rechercherLivre(titre);
        if (livre != null) {
            livre.setAuteur(nouvelAuteur);
            livre.setAnneePublication(nouvelleAnnee);
            return true;
        }
        return false;
    }

    // Supprimer un livre
    public boolean supprimerLivre(String titre) {
        Livre livre = rechercherLivre(titre);
        if (livre != null) {
            livres.remove(livre);
            return true;
        }
        return false;
    }

    // Rechercher un livre par titre
    public Livre rechercherLivre(String titre) {
        for (Livre livre : livres) {
            if (livre.getTitre().equalsIgnoreCase(titre)) {
                return livre;
            }
        }
        return null;
    }

    // Emprunter un livre
    public boolean emprunterLivre(String titre, String emprunteur) {
        Livre livre = rechercherLivre(titre);
        if (livre != null && livre.isDisponible()) {
            livre.setEmprunteur(emprunteur);
            livre.setDateEmprunt(LocalDate.now());
            livre.setDisponible(false);
            return true;
        }
        return false;
    }

    // Rendre un livre
    public boolean rendreLivre(String titre) {
        Livre livre = rechercherLivre(titre);
        if (livre != null && !livre.isDisponible()) {
            livre.setEmprunteur(null);
            livre.setDateRetour(LocalDate.now());
            livre.setDisponible(true);
            return true;
        }
        return false;
    }

    public List<Livre> getLivres() {
        return livres;
    }
}
