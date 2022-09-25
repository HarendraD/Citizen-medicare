package controller;

import db.DbConnection;
import model.Order;
import model.OrderDetails;
import view.tm.PharmacistTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "O-00"+tempId;
            }else if(tempId<99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }

    public boolean placeOrder(Order order){
        Connection con=null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO orders VALUES(?,?,?,?,?,?)");

            stm.setObject(1, order.getOrderId());
            stm.setObject(2, order.getpId());
            stm.setObject(3, order.getPatientName());
            stm.setObject(4, order.getSubTotal());
            stm.setObject(5, order.getTime());
            stm.setObject(6, order.getDate());

            if (stm.executeUpdate() > 0){
                if (saveOrderDetails(order.getOrderId(), order.getMedicines())){
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }



        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    private boolean saveOrderDetails(String orderId, ArrayList<OrderDetails> orderDetails) throws SQLException, ClassNotFoundException {
        for (OrderDetails tempOrder:orderDetails) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO orderDetails VALUES(?,?,?,?,?)");
                stm.setObject(1,tempOrder.getmId());
                stm.setObject(2,tempOrder.getDescription());
                stm.setObject(3,orderId);
                stm.setObject(4,tempOrder.getQtyNeed());
                stm.setObject(5,tempOrder.getTotalPrice());
                if (stm.executeUpdate() > 0){
                    if (updateQty(tempOrder.getmId(), tempOrder.getQtyNeed())){

                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
        }
        return true;
    }

    private boolean updateQty(String mId, int qtyNeed) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection()
                .prepareStatement
                        ("UPDATE medicine SET qty=(qty-" +qtyNeed+ ") WHERE mId='" +mId+ "'");
        return stm.executeUpdate()>0;
    }

    public static int getIncomeFromMedicine(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(subTotal) AS income FROM orders WHERE date='"+value+"';");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("income");
        }

        return count;
    }

    public static List<Order> getOrders() throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("SELECT orderId, patientName, subTotal, time FROM orders");
        ResultSet resultSet = pstm.executeQuery();

        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            orderList.add(new Order(
                    resultSet.getString("orderId"),
                    resultSet.getString("patientName"),
                    resultSet.getInt("subTotal"),
                    resultSet.getString("time")

            ));
        }
        return orderList;
    }
}
