package vet_clinic_generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import static vet_clinic_generator.Utils.*;

public class EquipmentGenerator {

    public static void generate() {
        try {
            Files.readAllLines(Paths.get("src/data/equipment.txt")).forEach(str -> {
                String[] equipAndServ = str.split("  ");
                String equipment = equipAndServ[0];
                String services = equipAndServ[1];
                String[] services_array = services.split(",");
                for (String service: services_array) {
                    insertEquipment(service, equipment, getRandomData("src/data/manufacturer.txt"),
                                getDate("for_write_off"), passwordAndSN(17, "ABCDEFGHIGKLMNOPQRSTVUWXYZ"));

                }
            });
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public static void insertEquipment(String service, String equipment, String manufacturer, Date date_off_write, String pswd) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(String.format("INSERT INTO equipment (office_id, equipment_name," +
                            " manufacturer, write_off_date, serial_number) VALUES ('%s','%s','%s','%s','%s')",
                    service, equipment, manufacturer, date_off_write, pswd));
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        }
    }

}