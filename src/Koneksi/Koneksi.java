package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection Koneksi;

    public static Connection getKoneksi() {

        try {

            if(Koneksi == null){

                String url =
                "jdbc:mysql://localhost/rumah_sakit_unindra";

                String user = "root";
                String pass = "";

                DriverManager.registerDriver(
                        new com.mysql.jdbc.Driver());

                Koneksi =
                        DriverManager.getConnection(
                                url,user,pass);

                System.out.println("Koneksi Berhasil");
            }

        } catch (Exception e) {
            System.out.println("Koneksi Gagal : "+e);
        }

        return Koneksi;
    }
}