/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trash_hunter.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillaume.laurent
 */
public class OutilsJDBC {

    static public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

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

}
