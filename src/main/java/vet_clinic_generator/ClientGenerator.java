package vet_clinic_generator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static vet_clinic_generator.PetGenerator.generatePet;
import static vet_clinic_generator.Utils.*;

public class ClientGenerator {

    public static void generate() {
        String full_name = getRandomData("src/data/full_name.txt");
        String address = String.format("г. Санкт-Петербург, %s, д. %d, кв. %d",
                getRandomData("src/data/address(SP-streets).txt"), getRand(1, 100), getRand(1, 700));
        boolean regular = false;
        if (getRand(0, 2) == 1)
            regular = true;
        insertClient(full_name, address, phoneGenerator(), regular);
    }

    public static void insertClient(String full_name, String address, String phone, boolean regular) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(String.format("INSERT INTO client (full_name, address, phone, regular) VALUES (" +
                    "'%s','%s','%s','%b')", full_name, address, phone, regular));
            generatePet(getLastId(statement, "id", "client"), statement);
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        }
    }

}
