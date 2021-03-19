import vet_clinic_generator.*;

import java.sql.Date;
import java.util.Random;

public class App {
    public static void main(String[] args){

        if (args.length < 4) {
            throw new IllegalArgumentException();
        }

        generateRandomDB(Boolean.parseBoolean(args[0]), Integer.parseInt(args[1]),
                Integer.parseInt(args[2]), Integer.parseInt(args[3]));
    }

    private static void generateRandomDB (boolean first_request, int number_of_client, int number_of_worker, int number_of_drug_request) {
        if (first_request) {
            ServicesGenerator.generate();
            DrugGenerator.generate();
            EquipmentGenerator.generate();
        }
        for (int i = 0; i < number_of_client; i++) {
            ClientGenerator.generate();
        }
        for (int i = 0; i < number_of_worker; i++) {
            WorkerGenerator.generate();
        }
        for (int i = 0; i < number_of_drug_request; i++) {
            DrugRequestGenerator.generate();
        }

        ScheduleOfVisitGenerator.generate();

    }
}