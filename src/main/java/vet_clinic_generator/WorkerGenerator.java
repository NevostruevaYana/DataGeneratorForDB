package vet_clinic_generator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import static vet_clinic_generator.Utils.*;
import static vet_clinic_generator.WorkerOfficeGenerator.generateWorkerOffice;

public class WorkerGenerator {

    public static void generate() {
        String full_name = getRandomData("src/data/full_name.txt");
        String[] set_for_login = full_name.split(" ");
        String login = set_for_login[0].toLowerCase() + "." + set_for_login[1].toLowerCase().charAt(0)
                + set_for_login[2].toLowerCase().charAt(0);
        int password = passwordAndSN(8, "abcdefghijklmnoqrstvuwxyz").hashCode();
        String[] positionAndServ = getRandomData("src/data/position.txt").split(" ");
        String position = positionAndServ[0];
        String services = positionAndServ[1];
        insertWorker(full_name, login, password, position, getDate("for_worker"), phoneGenerator(), services);
    }

    public static void insertWorker(String full_name, String login, int password, String position,
                                    Date date_of_birth, String phone, String services) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(String.format("INSERT INTO worker (full_name, login, password, " +
                    "worker_position, date_of_birth, phone) VALUES ('%s','%s','%d','%s','%s','%s')",
                    full_name, login, password, position, date_of_birth, phone));
            generateWorkerOffice(statement, getLastId(statement, "id", "worker"), services);
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        }
    }

}
