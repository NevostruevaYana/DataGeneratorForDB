package vet_clinic_generator;

import java.sql.SQLException;
import java.sql.Statement;

public class OfficeGenerator {

    public static void insertOffice(Statement statement, int number_of_office) {
        try {
            statement.execute(String.format("INSERT INTO office (number_of_office) VALUES ('%d')", number_of_office));
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        }
    }
}
