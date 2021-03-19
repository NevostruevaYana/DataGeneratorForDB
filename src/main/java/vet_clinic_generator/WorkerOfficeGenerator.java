package vet_clinic_generator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static vet_clinic_generator.Utils.getConnection;

public class WorkerOfficeGenerator {

    public static void generateWorkerOffice(Statement statement, int worker_id, String service) {
        if (!service.equals("nothing")) {
            String[] services = service.split(",");
            for (String serv : services) {
                insertWorkerOffice(statement, worker_id, serv);
            }
        }
    }

    public static void insertWorkerOffice(Statement statement, int worker_id, String service) {
        try {
            statement.execute(String.format("INSERT INTO intersection_worker_office (worker_id, " +
                    "office_id) VALUES ('%d','%s')", worker_id, service));
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        }
    }
}
