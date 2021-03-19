package vet_clinic_generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import java.util.Random;

public class Utils {

    final static String USER = "postgres";
    final static String password = "12345";
    final static String URL = "jdbc:postgresql://localhost:5432/vet_clinic_db";
    static final String DRIVER = "org.postgresql.Driver";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, password);
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        } catch (ClassNotFoundException e) {
            System.out.println("No driver for PostgreSQL");
        }
        return null;
    }

    public static int getRand(int a, int b) {
        return (int) (Math.random() * (b-a)) + a;
    }

    public static String phoneGenerator() {
        return String.format("+7(9%d)%d-%d-%d", getRand(10, 99), getRand(100, 999),
                getRand(10, 99), getRand(10, 99));
    }

    public static String getRandomData(String path) {
        try {
            return Files.readAllLines(Paths.get(path)).get(getRand(0, lineCounter(path)));
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return null;
    }

    public static Date getDate(String str) {
        long begin_date;
        long period;
        int month_or_year = 365;
        switch (str) {
            case ("for_pet") -> {
                begin_date = 1336284441411L;
                period = 8L;
            }
            case ("for_schedule") -> {
                begin_date = 1617230000000L;
                period = 1L;
                month_or_year = 12;
            }
            case ("for_worker") -> {
                begin_date = -94677120000L;
                period = 35L;
            }
            default -> {
                begin_date = 6025276443752L;
                period = 30L;
            }
        }
        Random rnd = new Random();
        long ms = begin_date + (Math.abs(rnd.nextLong()) % (period * month_or_year * 24 * 60 * 60 * 1000));
        return new Date(ms);
    }

    public static ArrayList<Date> getDateRequestAndSupply() {
        ArrayList<Date> dates = new ArrayList();
        Random rnd = new Random();
        long ms = 1614600000000L + (Math.abs(rnd.nextLong()) % ((long) 12 * 24 * 60 * 60 * 1000));
        long ms2 = ms + (Math.abs(rnd.nextLong()) % (3L * 12 * 24 * 60 * 60 * 1000));
        dates.add(new Date(ms));
        dates.add(new Date(ms2));
        return dates;
    }

    public static String passwordAndSN(int amount, String str) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i <= amount; i++) {
            if (Utils.getRand(0, 2) == 1) {
                password.append(str.charAt(new Random().nextInt(str.length() - 1)));
            } else {
                password.append(Utils.getRand(0,10));
            }}
        return password.toString();
    }

    public static int lineCounter(String path) {
        BufferedReader reader;
        int lines = 0;
        try {
            reader = new BufferedReader(new FileReader(path));
            while (reader.readLine() != null) lines++;
            reader.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return lines;
    }

    public static ArrayList<String> returnValues(Statement statement, String row, String table) throws SQLException {
        String sql = "SELECT " + row + " FROM " + table;
        ArrayList<String> data = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Integer getLastId (Statement statement, String row, String table) {
        Integer id = null;
        try  {
            ResultSet resultSet = statement.executeQuery( "SELECT " + row + " FROM " + table);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException throwable) {
            System.out.println("Error while connecting to DB");
        }
        return id;
    }

}
