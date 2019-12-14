package ir.mctab.java32.projects.scholarshipmanagement.features.dashboard;

import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardUseCaseImpl implements DashboardUseCase {

    public void display() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        try {
            Connection connection = DatabaseConfig.getDatabaseConnection();
            String sql = "select status, count(id) as countStatus from scholarship group by(status)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String status = resultSet.getString("status");
                int count = resultSet.getInt("countStatus");
                result.put(status, count);
            }

            System.out.println("===============================================");
            System.out.println("                  Dashboard");
            System.out.println("===============================================");
            System.out.printf("%-20s\t%-5s\t%-30s", "Status", "Count", "Chart");
            System.out.print("\n-----------------------------------------------");
            for (String statusItem : result.keySet()){
                String chart = "";
                for (int i = 1; i <= result.get(statusItem); i++){
                    chart += "\u25ac";
                }
                System.out.println();
                System.out.printf("%-20s\t%-5d\t%-30s", statusItem + ":" , result.get(statusItem), chart);
            }
            System.out.println("\n===============================================\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
