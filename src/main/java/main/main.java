package main;
import entities.*;
import utils.database;

import java.sql.SQLException;
import services.*;

public class main {

    public static void main(String[] args) throws SQLException {
        database d= database.getInstance();
        produitserv ps=new produitserv();
        ps.ajouterproduit(new produit("jyhed",12.3f,"horchanizsczdczdcdzcs","11"));
        panierserv pss=new panierserv();
        pss.ajouterpanier(new panier(10,12.3f));
        commandserv psss=new commandserv();
        //psss.ajoutercommand(new command(2024-02-29,12));
        System.out.println(psss.affichervommand());
        //ps.updateProduit(new personne(15,"jyhed","horchani"));
    }

}
