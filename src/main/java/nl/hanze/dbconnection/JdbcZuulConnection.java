package nl.hanze.dbconnection;

import nl.hanze.zuul.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JdbcZuulConnection {


    public static Room selectCurrentRoomFromDatabase(){
        HashMap<String, Room> exits  = new HashMap<>();
        String roomDescr = selectCurrentRoom("description");
        String roomName = selectCurrentRoom("name");
        for (String[] exitList : selectRoomExits(roomName)) {
            exits.put(exitList[0], new Room(selectRoomDecriptionOnName(exitList[1])));
        }
       return new Room(roomDescr, exits);

    }



    public static void updateCurrentRoom(String description){


    }

    public static List<String[]> selectRooms() {
        String query = "SELECT * FROM rooms";
        return executeSelectQuery(query);
    }

    public static String selectRoomDecriptionOnName(String roomName) {
        String query = "SELECT description FROM rooms Where name = '"+ roomName+"'";
        return executeSelectQueryString(query);
    }

    public static List<String[]> selectRoomExits(String roomName) {
        String query = "SELECT d.direction, (Select name from rooms where id = e.rooms_id1) FROM rooms_has_exit e \n" +
                "JOIN exit_directions d on e.exit_id = d.id\n" +
                "WHERE rooms_id = (SELECT id FROM rooms WHERE name = '"+roomName+"')";

        return executeSelectQuery(query);
    }




    private static String selectCurrentRoom(String field) {
        String query = "Select "+field+" FROM rooms WHERE id = (SELECT rooms_id FROM currentroom)";
        return executeSelectQueryString(query);
    }

//    private static int executeUpdateQuery(String query, String... preparedValues) {
//
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zuul", "hr", "hr");
//             PreparedStatement statement = connection.prepareStatement(query)
//        ) {
//            for(String value:preparedValues){
//
//            }
//
//            return statement.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Something went wrong executing the query " + e);
//        }
//        return null;
//    }



    private static List<String[]> executeSelectQuery(String query, String... preparedValues) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zuul", "hr", "hr");
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            try (ResultSet rs = statement.executeQuery()) {
                return rsToList(rs);
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong executing the query " + e);
        }
        return null;
    }


    private static String executeSelectQueryString(String query, String... preparedValues) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zuul", "hr", "hr");
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()){
                    return rs.getString(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong executing the query " + e);
        }
        return null;
    }


    private static List<String[]> rsToList(ResultSet resultSet) throws SQLException {
        List<String[]> resultRows = new ArrayList<>();
        int columns = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            String[] row = new String[columns];
            for (int i = 0; i < columns; i++) {
                row[i] = resultSet.getString(i + 1);
            }
            resultRows.add(row);
        }
        return resultRows;
    }
}
