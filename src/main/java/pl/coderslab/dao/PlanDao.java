package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {

    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created,admin_id) VALUES (?,?,?,?);";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ?,  description = ?, admmin_id = ? WHERE id = ?;";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";


    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
    private static final String DELETE_RECIPES_PLANS_QUERY = "delete from recipe_plan where plan_id=?;";
    private static final String USER_PLANS_COUNT = "SELECT COUNT(*) FROM plan WHERE admin_id = ?;";
    private static final String USER_LAST_PLAN = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description, recipe.id as recipe_id\n" +
            "FROM `recipe_plan`\n" +
            "         JOIN day_name on day_name.id=day_name_id\n" +
            "         JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "        recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?)\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String USER_PLAN = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description, recipe.id as recipe_id\n" +
            "FROM `recipe_plan`\n" +
            "         JOIN day_name on day_name.id=day_name_id\n" +
            "         JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "        recipe_plan.plan_id = ?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String Last_User_Plan_Name = "SELECT name from plan WHERE admin_id = ? ORDER BY ID desc LIMIT 1;";
    private static final String CREATE_PLAN_RECIPE_QUERY = "INSERT INTO recipe_plan (recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?, ?, ?, ?, ?);";

    /**
     * CREATE method
     */

    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setString(3, plan.getCreated());
            insertStm.setInt(4, plan.getAdminId());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createPlanRecipe(String recipe_id,String meal_name,String display_order,String day_name_id,String plan_id) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_RECIPE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, recipe_id);
            insertStm.setString(2, meal_name);
            insertStm.setString(3, display_order);
            insertStm.setString(4, day_name_id);
            insertStm.setString(5, plan_id);
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * READ method
     */

    public Plan read(Integer planId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                    plan.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;
    }

    /**
     * UPDATE method
     */

    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(4, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setInt(3, plan.getAdminId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * DELETE method
     */

    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();
            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deletefromrecipe_plan(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPES_PLANS_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();
            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Find all plans
     */

    public List<Plan> findAll() {
        List<Plan> plansList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planToAdd.setAdminId(resultSet.getInt("admin_id"));
                plansList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plansList;
    }

    public int countUserPlans(int userID) {

        int numberOfRows = 0;

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_PLANS_COUNT)){
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(1);
            } else {
                throw new Exception("Cannot count plans");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return numberOfRows;
    }

    public ArrayList getLastUserPlan(int userID) {

        ArrayList Rows = new ArrayList();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_LAST_PLAN)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()){
                ArrayList row = new ArrayList();
                for (int i = 1; i < 6 ; i++){
                    row.add(resultSet.getString(i));
                }
                Rows.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Rows;
    }
    public ArrayList getUserPlan(int planID) {

        ArrayList Rows = new ArrayList();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_PLAN)) {
            statement.setInt(1, planID);

            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()){
                ArrayList row = new ArrayList();
                for (int i = 1; i < 6 ; i++){
                    row.add(resultSet.getString(i));
                }
                Rows.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Rows;
    }

    public String getLastUserPlanName(int userID) {
        String name = "";

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(Last_User_Plan_Name)){
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                name = resultSet.getString(1);
            } else {
                throw new Exception("Cannot get name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }
}
