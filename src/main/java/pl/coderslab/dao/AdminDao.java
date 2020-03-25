package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Book;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name,last_name,email,password,superadmin,enable) VALUES (?,?,?,?,?,?);";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins where id = ?;";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE	admins SET first_name = ? , last_name = ?, email = ? , password = ?, superadmin = ?, enable = ? WHERE	id = ?;";
    private static final String LOGIN_ADMIN_QUERY = "SELECT * from admins where email = ?;";

//    public Admin login(String passwords, String email) {
//        Admin admin = new Admin();
//        try (Connection connection = DbUtil.getConnection();
//             PreparedStatement statement = connection.prepareStatement(LOGIN_ADMIN_QUERY)
//        ) {
//            statement.setString(1, email);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    if (BCrypt.checkpw(passwords, resultSet.getString("password"))) {
//                        admin.setId(resultSet.getInt("id"));
//                        admin.setFirst_name(resultSet.getString("first_name"));
//                        admin.setLast_name(resultSet.getString("last_name"));
//                        admin.setEmail(resultSet.getString("email"));
//                        String password = resultSet.getString("password");
//                        admin.setPassword(password);
//                        admin.setSuperadmin(resultSet.getInt("superadmin"));
//                        admin.setEnable(resultSet.getInt("enable"));
//                    }
//                    else {
//                        admin=null;
//                    }
//
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return admin;
//    }
    public int adminId(String email){
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOGIN_ADMIN_QUERY)
        ) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public boolean login(String passwords, String email) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(LOGIN_ADMIN_QUERY)
        ) {
            statement.setString(1, email);
            System.out.println(email);
            System.out.println(passwords);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (BCrypt.checkpw(passwords, resultSet.getString("password"))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Admin read(Integer AdminId) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ADMIN_QUERY)
        ) {
            statement.setInt(1, AdminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    admin.setId(resultSet.getInt("id"));
                    admin.setFirst_name(resultSet.getString("first_name"));
                    admin.setLast_name(resultSet.getString("last_name"));
                    admin.setEmail(resultSet.getString("email"));
                    String password = resultSet.getString("password");
                    admin.setPassword(password);
                    admin.setSuperadmin(resultSet.getInt("superadmin"));
                    admin.setEnable(resultSet.getInt("enable"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;

    }

    public List<Admin> findAll() {
        List<Admin> adminList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMINS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setFirst_name(resultSet.getString("first_name"));
                admin.setLast_name(resultSet.getString("last_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
                admin.setSuperadmin(resultSet.getInt("superadmin"));
                admin.setEnable(resultSet.getInt("enable"));
                adminList.add(admin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;

    }

    public Admin create(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_ADMIN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, admin.getFirst_name());
            insertStm.setString(2, admin.getLast_name());
            insertStm.setString(3, admin.getEmail());
            String password = admin.getPassword();
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            insertStm.setString(4, hashed);
            insertStm.setInt(5, admin.getSuperadmin());
            insertStm.setInt(6, admin.getEnable());
            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    admin.setId(generatedKeys.getInt(1));
                    return admin;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer adminId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY)) {
            statement.setInt(1, adminId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_QUERY)) {
            statement.setInt(4, admin.getId());
            statement.setString(1, admin.getLast_name());
            statement.setString(2, admin.getFirst_name());
            statement.setString(3, admin.getEmail());
            String password = admin.getPassword();
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            statement.setString(4, hashed);
            statement.setInt(5, admin.getSuperadmin());
            statement.setInt(6, admin.getEnable());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
