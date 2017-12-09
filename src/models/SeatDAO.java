package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatDAO {

    public static void main(String[] args) {

//        create seats
//        String[] rows = new String[]{"A", "B", "C", "D", "E"};
//        for (int i = 0; i < 5; i++) {
//            for (int j = 1; j < 9; j++) {
//                System.out.println(rows[i] + j);
//                try {
//                    insertSeat(j, rows[i], rows[i] + j);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


    }


    private static Seat getSeatFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        if (resultSet.next()) {
            Seat seat = new Seat();
            seat.setId(resultSet.getInt("id"));
            seat.setColumn(resultSet.getInt("column"));
            seat.setRow(resultSet.getString("row"));
            seat.setName(resultSet.getString("name"));
            return seat;
        } else {
            return null;
        }
    }

    public static ObservableList<Seat> getSeatObservableList() throws SQLException, ClassNotFoundException {
        ResultSet resultSetSeats = SQLiteConnection.executeQuery("SELECT * FROM Seat", null);

        return getSeatList(resultSetSeats);
    }

    private static ObservableList<Seat> getSeatList(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Seat> seatList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Seat seat = new Seat();
            seat.setId(resultSet.getInt("id"));
            seat.setColumn(resultSet.getInt("column"));
            seat.setRow(resultSet.getString("row"));
            seat.setName(resultSet.getString("name"));
            seatList.add(seat);
        }

        return seatList;
    }

    public static void insertSeat(int column, String row, String name) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(column),
                new PreparedStatementArg(row),
                new PreparedStatementArg(name)
        };

        SQLiteConnection.execute(
                "INSERT INTO Seat\n" +
                        "(column, row, name)\n" +
                        "VALUES\n" +
                        "(?, ?, ?);",
                preparedStatementArgs
        );
    }

    public static void deleteSeat(int id) throws SQLException, ClassNotFoundException {
        PreparedStatementArg[] preparedStatementArgs = new PreparedStatementArg[]{
                new PreparedStatementArg(id)
        };

        SQLiteConnection.execute(
                "DELETE FROM Seat\n" +
                        "WHERE id = ?",
                preparedStatementArgs
        );
    }

    public static Seat getSeatByLocation(String column, int row) throws SQLException, ClassNotFoundException {
        ResultSet resultSetSeats = SQLiteConnection.executeQuery("SELECT * FROM Seat", null);

        if (resultSetSeats != null) {
            return getSeatList(resultSetSeats).get(0);

        } else {
            return null;
        }
    }
}
