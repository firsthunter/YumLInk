package com.example.beta.Services;

import com.example.beta.Models.UserNutrition;
import com.example.beta.Utils.My_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserNutrition_Service implements iService<UserNutrition> {
    private static Connection conn;

    public UserNutrition_Service() {
        conn = My_db.getInstance().getConn();
    }

    private double calculateCalorieIntake(int age, double weight, double height, String activityLevel, String gender) {
        double basalMetabolicRate = 0;
        if (gender.equalsIgnoreCase("Homme")) {
            basalMetabolicRate = 10 * weight + 6.25 * height - 5 * age + 5;
        } else if (gender.equalsIgnoreCase("Femme")) {
            basalMetabolicRate = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        double calorieIntake = basalMetabolicRate * getActivityMultiplier(activityLevel);
        System.out.println(getActivityMultiplier(activityLevel));
        return calorieIntake;
    }

    private double getActivityMultiplier(String activityLevel) {
        // Define activity multipliers based on activity level
        switch (activityLevel) {

            case "Active":
                return 1.375;

            case "Lazy":
                return 0.90;
            default:
                return 1.10;
        }
    }


    private double calculateProteinIntake(double x) {
        return x * 0.15; // 15% of total calories
    }

    private double calculateCarbsIntake(double calorieIntake) {
        return calorieIntake * 0.5; // 50% of total calories
    }

    private double calculateFatsIntake(double calorieIntake) {
        return calorieIntake * 0.25; // 25% of total calories
    }

    public Map<String, Double> addUserNutrition(UserNutrition userNutrition) {
       // int userId = (int) session.getAttribute("userId");
        int userId = 1;

        // Create a map to store the nutrition values
        Map<String, Double> nutritionValues = new HashMap<>();


        double weight = userNutrition.getWeight();
        double height = userNutrition.getHeight();
        String activityLevel = userNutrition.getActivityLevel();
        System.out.println(userNutrition);
        // Calculate calorie, protein, carbs, and fats based on user info
        double calorie = calculateCalorieIntake(userNutrition.getAge(), weight, height, activityLevel , userNutrition.getGender());
        double protein = calculateProteinIntake(calorie);
        double carbs = calculateCarbsIntake(calorie);
        double fats = calculateFatsIntake(calorie);
        System.out.println(calorie+"/"+protein +"/" +carbs +"/" + fats);

        // Store the nutrition values in a map
        nutritionValues.put("calorie", calorie);
        nutritionValues.put("protein", protein);
        nutritionValues.put("carbs", carbs);
        nutritionValues.put("fats", fats);


        // Insert user nutrition data into database
        String sql = "INSERT INTO user_nutrition (id, age, weight, height, activity_level, gender ,calorie, protein, carbs, fat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, userNutrition.getAge());
            statement.setDouble(3, weight);
            statement.setDouble(4, height);
            statement.setString(5, activityLevel);
            statement.setString(6, userNutrition.getGender());
            statement.setDouble(7, calorie);
            statement.setDouble(8, protein);
            statement.setDouble(9, carbs);
            statement.setDouble(10, fats);
            statement.executeUpdate();
            System.out.println("User nutrition data added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nutritionValues;
    }

    public void deleteUserNutrition(int userId) {
        // Delete user nutrition data from database
        String sql = "DELETE FROM user_nutrition WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
            System.out.println("User nutrition data deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static UserNutrition getUserNutrition(int userId) {
        String sql = "SELECT * FROM user_nutrition WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int age = resultSet.getInt("age");
                double weight = resultSet.getDouble("weight");
                double height = resultSet.getDouble("height");
                String activityLevel = resultSet.getString("activity_level");
                String gender = resultSet.getString("gender");
                return new UserNutrition(age, weight, height, activityLevel, gender);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateUserNutrition(UserNutrition userNutrition) {
        String sql = "UPDATE user_nutrition SET age = ?, weight = ?, height = ?, activity_level = ?, gender = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userNutrition.getAge());
            statement.setDouble(2, userNutrition.getWeight());
            statement.setDouble(3, userNutrition.getHeight());
            statement.setString(4, userNutrition.getActivityLevel());
            statement.setString(5, userNutrition.getGender());
            statement.setInt(6, 1);
            statement.executeUpdate();
            System.out.println("User nutrition data updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserNutrition displayUserNutrition(int Id) {
        String sql = "SELECT * FROM user_nutrition WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, Id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int age = resultSet.getInt("age");
                double weight = resultSet.getDouble("weight");
                double height = resultSet.getDouble("height");
                String activityLevel = resultSet.getString("activity_level");
                String gender = resultSet.getString("gender");
                UserNutrition un = new UserNutrition(age, weight, height, activityLevel, gender);
                return un;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null ;
}

}