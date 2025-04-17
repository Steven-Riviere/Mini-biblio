import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.time.LocalDate;

public class BibliothequeGUI extends JFrame {
    private Bibliotheque bibliotheque;
    private JTextArea affichageLivres;
    private JTextField titreField, auteurField, anneeField, emprunteurField, rechercheField;
    private final String FICHIER_LIVRES = "livres.txt";

    public BibliothequeGUI() {
        bibliotheque = new Bibliotheque();
        chargerLivres();

        setTitle("Mini Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        panel.add(new JLabel("Titre:"));
        titreField = new JTextField();
        panel.add(titreField);

        panel.add(new JLabel("Auteur:"));
        auteurField = new JTextField();
        panel.add(auteurField);

        panel.add(new JLabel("Année:"));
        anneeField = new JTextField();
        panel.add(anneeField);

        panel.add(new JLabel("Emprunteur:"));
        emprunteurField = new JTextField();
        panel.add(emprunteurField);

        JButton ajouterButton = new JButton("Ajouter Livre");
        panel.add(ajouterButton);

        JButton modifierButton = new JButton("Modifier Livre");
        panel.add(modifierButton);

        JButton supprimerButton = new JButton("Supprimer Livre");
        panel.add(supprimerButton);

        JButton emprunterButton = new JButton("Emprunter");
        panel.add(emprunterButton);

        JButton rendreButton = new JButton("Rendre");
        panel.add(rendreButton);

        contentPanel.add(panel, BorderLayout.NORTH);

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recherchePanel.add(new JLabel("Rechercher un livre:"));
        rechercheField = new JTextField(15);
        recherchePanel.add(rechercheField);
        JButton rechercherButton = new JButton("Rechercher");
        recherchePanel.add(rechercherButton);
        contentPanel.add(recherchePanel, BorderLayout.CENTER);

        affichageLivres = new JTextArea(10, 50);
        affichageLivres.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(affichageLivres);
        contentPanel.add(scrollPane, BorderLayout.SOUTH);

        afficherLivres();
        pack();
        setVisible(true);

        ajouterButton.addActionListener(e -> ajouterLivre());
        modifierButton.addActionListener(e -> modifierLivre());
        supprimerButton.addActionListener(e -> supprimerLivre());
        emprunterButton.addActionListener(e -> emprunterLivre());
        rendreButton.addActionListener(e -> rendreLivre());
        rechercherButton.addActionListener(e -> rechercherLivre());
    }

    private void afficherLivres() {
        affichageLivres.setText("");
        for (Livre livre : bibliotheque.getLivres()) {
            affichageLivres.append(livre.toString() + "\n");
        }
        pack();
    }

    private void ajouterLivre() {
        String titre = titreField.getText();
        String auteur = auteurField.getText();
        int annee;
        try {
            annee = Integer.parseInt(anneeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Année invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        bibliotheque.ajouterLivre(new Livre(titre, auteur, annee));
        sauvegarderLivres();
        afficherLivres();
    }

    private void modifierLivre() {
        String titre = titreField.getText();
        if (bibliotheque.modifierLivre(titre, auteurField.getText(), Integer.parseInt(anneeField.getText()))) {
            sauvegarderLivres();
            afficherLivres();
        } else {
            JOptionPane.showMessageDialog(this, "Livre introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerLivre() {
        if (bibliotheque.supprimerLivre(titreField.getText())) {
            sauvegarderLivres();
            afficherLivres();
        } else {
            JOptionPane.showMessageDialog(this, "Livre introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void emprunterLivre() {
        if (bibliotheque.emprunterLivre(titreField.getText(), emprunteurField.getText())) {
            sauvegarderLivres();
            afficherLivres();
        } else {
            JOptionPane.showMessageDialog(this, "Livre non disponible ou introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rendreLivre() {
        if (bibliotheque.rendreLivre(titreField.getText())) {
            sauvegarderLivres();
            afficherLivres();
        } else {
            JOptionPane.showMessageDialog(this, "Livre non emprunté ou introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechercherLivre() {
        Livre livre = bibliotheque.rechercherLivre(rechercheField.getText());
        affichageLivres.setText(livre != null ? livre.toString() : "Aucun livre trouvé.");
        pack();
    }

    private void sauvegarderLivres() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_LIVRES))) {
            for (Livre livre : bibliotheque.getLivres()) {
                writer.write(livre.getTitre() + "," + livre.getAuteur() + "," + livre.getAnneePublication() + "," + livre.isDisponible() + "," + (livre.getEmprunteur() != null ? livre.getEmprunteur() : "") + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargerLivres() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_LIVRES))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] data = ligne.split(",");
                if (data.length >= 5) {
                    Livre livre = new Livre(data[0], data[1], Integer.parseInt(data[2]));
                    livre.setDisponible(Boolean.parseBoolean(data[3]));
                    if (!data[4].isEmpty()) livre.setEmprunteur(data[4]);
                    bibliotheque.ajouterLivre(livre);
                }
            }
        } catch (IOException e) {
            System.out.println("Aucun fichier existant, démarrage avec une bibliothèque vide.");
        }
    }

    public static void main(String[] args) {
        new BibliothequeGUI();
    }
}
