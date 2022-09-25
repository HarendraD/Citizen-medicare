package controller;

import db.DbConnection;
import model.Order;
import model.OrderDetails;
import view.tm.OrderDetailsTM;
import view.tm.PharmacistTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportController {
    public static List<Order> getAllOrders() throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT orderId, patientName, subTotal, time, date FROM orders");
        ResultSet resultSet = pstm.executeQuery();

        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            orderList.add(new Order(
                    resultSet.getString("orderId"),
                    resultSet.getString("patientName"),
                    resultSet.getInt("subTotal"),
                    resultSet.getString("time"),
                    resultSet.getString("date")

            ));
        }
        return orderList;
    }
    public static List<Order> searchOrder(String value) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT orderId,patientName,subTotal,time,date FROM orders WHERE orderId LIKE '%"+value+"%' OR patientName LIKE '%"+value+"%'");
        ResultSet resultSet = pstm.executeQuery();

        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            orderList.add(new Order(
                    resultSet.getString("orderId"),
                    resultSet.getString("patientName"),
                    resultSet.getInt("subTotal"),
                    resultSet.getString("time"),
                    resultSet.getString("date")

            ));
        }
        return orderList;
    }

    public static List<OrderDetailsTM> getOrderDetails(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT medicine.mId, medicine.description, orderDetails.qty, orderDetails.totalPrice FROM medicine INNER JOIN orderDetails ON medicine.mId=orderDetails.mId WHERE orderId='"+id+"' ");
        ResultSet resultSet = pstm.executeQuery();

        List<OrderDetailsTM> orderList = new ArrayList<>();
        while (resultSet.next()){
            orderList.add(new OrderDetailsTM(
                    resultSet.getString("mId"),
                    resultSet.getString("description"),
                    resultSet.getInt("qty"),
                    resultSet.getDouble("totalPrice")

            ));
        }
        return orderList;
    }

    public static int getDailyIncome(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT (SUM(channelFee.fee) + SUM(orders.subTotal)) AS income " +
                "FROM channelFee,orders WHERE channelFee.date='"+value+"' AND orders.date='"+value+"'");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("income");
        }

        return count;
    }

    public static int getMonthlyIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT (SUM(channelFee.fee) + SUM(orders.subTotal)) AS income FROM channelFee,orders");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("income");
        }

        return count;
    }
    public static int getYearlyIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT (SUM(channelFee.fee) + SUM(orders.subTotal)) AS income FROM channelFee,orders");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("income");
        }

        return count;
    }
}

