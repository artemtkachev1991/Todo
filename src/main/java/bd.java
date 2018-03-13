import java.sql.*;
import java.util.ArrayList;


public class bd {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_jee";
    private static final String USER_NAME = "root";
    private static final String PASS = "1.nrfxtdf";

    public static void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER_NAME, PASS);
    }

    public void deleteTODO(ArrayList<String> list) {
        try (Connection con = getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE from todo_list WHERE id= ?");

            for (int i = 0; i < list.size(); i++) {
                preparedStatement.setString(1, list.get(i));
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTODO(String name) {
        try (Connection con = getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO todo_list (name) values (?)");

            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public ArrayList<todoshka> getTODOList() {
        try (Connection con = getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement("select * from todo_list");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<todoshka> buf = new ArrayList<>();

            while (resultSet.next()) {
                buf.add(new todoshka(resultSet.getString("id"), resultSet.getString("name")));
            }

            return buf;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

}
