/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trash_hunter.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guillaume.laurent
 */
public class OutilsJDBC {
    static public Timestamp maintenant() {
        return new Timestamp(System.currentTimeMillis());
    }

    static public void afficherResultSet(ResultSet result) throws SQLException {

        ResultSetMetaData metaData = result.getMetaData();

        int columnsNumber = metaData.getColumnCount();

        System.out.print(" ");
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.format("+----------------------");
        }
        System.out.println("+");
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.format(" | %-20.20s", metaData.getColumnName(i));
        }
        System.out.println(" | ");
        System.out.print(" ");
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.format("+----------------------");
        }
        System.out.println("+");

        result.beforeFirst();
        while (result.next()) {

            for (int i = 1; i <= columnsNumber; i++) {
                System.out.format(" | %-20.20s", result.getObject(i));
            }
            System.out.println(" | ");
        }
        result.beforeFirst();

        System.out.print(" ");
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.format("+----------------------");
        }
        System.out.println("+");

    }

    static public void afficherTable(Connection connexion, String nomTable) {
        try {
            PreparedStatement requete = connexion.prepareStatement("SELECT * FROM " + nomTable + ";");
            ResultSet resultat = requete.executeQuery();
            OutilsJDBC.afficherResultSet(resultat);
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    static public String recupererResultSetEnString(ResultSet result) throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int columnsNumber = metaData.getColumnCount();

        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 1; i <= columnsNumber; i++) {
            sb.append("+----------------------");
        }
        sb.append("+\n");
        for (int i = 1; i <= columnsNumber; i++) {
            sb.append(String.format(" | %-20.20s", metaData.getColumnName(i)));
        }
        sb.append(" | \n");
        sb.append(" ");
        for (int i = 1; i <= columnsNumber; i++) {
            sb.append("+----------------------");
        }
        sb.append("+\n");

        result.beforeFirst();
        while (result.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                sb.append(String.format(" | %-20.20s", result.getObject(i)));
            }
            sb.append(" | \n");
        }
        result.beforeFirst();

        sb.append(" ");
        for (int i = 1; i <= columnsNumber; i++) {
            sb.append("+----------------------");
        }
        sb.append("+\n");

        return sb.toString(); // Retourne la chaîne construite
    }
    static public String showTableAsString(Connection connexion, String nomTable) {
        String resultString = "";
        try {
            PreparedStatement requete = connexion.prepareStatement("SELECT pseudo,score,score_max,creation_date,color FROM " + nomTable + ";");
            ResultSet resultat = requete.executeQuery();
            resultString = recupererResultSetEnString(resultat);
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultString; // Retourne la chaîne de résultats
    }
    public static List<Object[]> getDiversData(Connection connexion, String nomTable){
        List<Object[]> divers = new ArrayList<>();
        return(divers);
    }
}
