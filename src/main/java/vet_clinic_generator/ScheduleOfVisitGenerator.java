package vet_clinic_generator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static vet_clinic_generator.ScheduleOfficeAndServiceGenerator.*;
import static vet_clinic_generator.Utils.*;

public class ScheduleOfVisitGenerator {

    private static ArrayList<String> pets_id = null;
    private static ArrayList<String> clients_id = null;
    private static ArrayList<String> workers_id = null;
    private static ArrayList<String> amount_of_money= null;
    private static ArrayList<String> offices_id_in_wo = null;
    private static ArrayList<String> workers_id_in_wo = null;

    public static void generate()  {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            pets_id = returnValues(statement, "id", "pet");
            clients_id = returnValues(statement, "client_id", "pet");
            workers_id = returnValues(statement, "id", "worker");
            amount_of_money = returnValues(statement, "amount_of_money", "service");
            offices_id_in_wo = returnValues(statement, "office_id", "intersection_worker_office");
            workers_id_in_wo = returnValues(statement, "worker_id", "intersection_worker_office");
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        }
        int i = 0;
        for (String id : pets_id) {
            if (getRand(0,2) == 1) {
                String client_id = clients_id.get(i);
                String worker_id = workers_id.get(getRand(0, workers_id.size()));
                ArrayList<String> offices_id = new ArrayList<>();
                int j = 0;
                for (String id_ : workers_id_in_wo)  {
                    if (id_.equals(worker_id)) {
                        offices_id.add(offices_id_in_wo.get(j));
                    }
                    j++;
                }
                String office_id = offices_id.get(getRand(0, offices_id.size()));
                insertScheduleOfVisit(id, client_id, office_id,
                            worker_id, office_id, getDate("for_schedule"), getRand(10, 22),
                            getRand(1,6), amount_of_money.get(Integer.parseInt(office_id) - 1));
            }
            i++;
        }
    }

    public static void insertScheduleOfVisit(String id, String client_id, String office_id, String worker_id,
                                             String service_id, Date date_of_visit, int time_of_visit_1, int time_f_visit_2, String amount_of_money) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(String.format("INSERT INTO schedule_of_visit (pet_id, client_id, office_id," +
                            " worker_id, service_id, date_and_time_of_visit, amount_of_money) VALUES (" +
                            "'%s','%s','%s','%s','%s','%s %d:%d0:00','%s')", id, client_id, office_id,
                    worker_id, service_id, date_of_visit, time_of_visit_1, time_f_visit_2, amount_of_money));
            insertScheduleOfficeOrService(statement, office_id,
                    "intersection_schedule_service", "(schedule_of_visit_id, service_id)");
            insertScheduleOfficeOrService(statement, office_id,
                    "intersection_schedule_office", "(schedule_of_visit_id, office_id)");
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        }
    }
}
