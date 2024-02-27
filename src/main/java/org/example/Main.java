package org.example;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
/*        MyDB bd = MyDB.getInstance();
        DéfisS ds=new DéfisS();

        try {
            LocalDate date = LocalDate.of(2024, 12, 25); // Exemple de date spécifiée
            LocalTime heure = LocalTime.of(12, 0); // Exemple d'heure spécifiée
            //ds.ajouter(new Défis(24, "fwdf", "refge", date, heure ));
            System.out.println("Evenement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
        try {
            LocalDate date = LocalDate.of(2024, 11, 25); // Exemple de date spécifiée
            LocalTime heure = LocalTime.of(8, 0); // Exemple d'heure spécifiée
            ds.ajouter(new Défis( "dfbd", "rfvger", date, heure ));
            System.out.println("Evenement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
        try {
            LocalDate date = LocalDate.of(2024, 11, 25); // Exemple de date spécifiée
            LocalTime heure = LocalTime.of(8, 0); // Exemple d'heure spécifiée
            ds.modifier(new Défis(2,"frvs","fvrv",date,heure));
            System.out.println("Evenement modifiée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            // Créer un objet Evenement avec les nouvelles valeurs
            Défis df = new Défis();
            df.setId_d(1);  // Remplacez 1 par l'ID de l'événement que vous souhaitez modifier
            df.setNom_d("Nouveau nom");
            df.setDis_d("jhydgbvjwhd");
            df.setDelai(LocalDate.of(2024, 12, 31));
            df.setHeure(LocalTime.of(18, 30));


            // Appeler la méthode modifier pour mettre à jour l'événement
            ds.modifier(df);

            System.out.println("Evenement modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'événement : " + e.getMessage());
        }
        try {
            ds.supprimer(1);
            System.out.println("Evenement supprimée!!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(ds.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
*/}
}