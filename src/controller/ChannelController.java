package controller;

import db.DbConnection;
import model.Channel;
import model.ChannelFee;
import model.Doctor;
import model.Patient;
import view.tm.ChannelTM;
import view.tm.MedicineTM;
import view.tm.RecentChannelTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChannelController {
    public static boolean saveChannel(Channel channel) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("INSERT INTO channel VALUES(?,?,?,?,?,?,?)");
        stm.setObject(1,channel.getId());
        stm.setObject(2,channel.getPatientId());
        stm.setObject(3,channel.getDoctorId());
        stm.setObject(4,channel.getSpeciality());
        stm.setObject(5,channel.getNurseId());
        stm.setObject(6,channel.getTime());
        stm.setObject(7,channel.getFee());

        return stm.executeUpdate()>0;
    }

    public static List<ChannelTM> getAllChannels() throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM channel");
        ResultSet rs = pstm.executeQuery();

        List<ChannelTM> channelTMS = new ArrayList<>();

        while (rs.next()) {
            channelTMS.add(new ChannelTM(
                    rs.getString("cId"),
                    rs.getString("pId"),
                    rs.getString("doctorId"),
                    rs.getString("speciality"),
                    rs.getString("nurseId"),
                    rs.getString("time"),
                    rs.getDouble("fee")

            ));
        }


        return channelTMS;
    }


    public List<String> getPatientIds() throws SQLException, ClassNotFoundException {
        String id="pId";
        String tableName="patient";
        List<String> ids=getIds(id,tableName);
        return ids;
    }
    public List<String> getDoctorIds() throws SQLException, ClassNotFoundException {
        String id="doctorId";
        String tableName="doctor";
        List<String> ids=getIds(id,tableName);
        return ids;
    }
    public List<String> getNurseIds() throws SQLException, ClassNotFoundException {
        String id="nurseId";
        String tableName="nurse";
        List<String> ids=getIds(id,tableName);
        return ids;
    }
    private List<String> getIds(String id, String tableName) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT "+id+" FROM " +tableName).executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public static boolean clearChannel(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("DELETE FROM channel WHERE cId=?");
        stm.setObject(1, id);
        return stm.executeUpdate() > 0;
    }

    public static boolean saveChannelFee(ChannelFee channelFee) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("INSERT INTO channelFee VALUES(?,?,?,?,?,?)");
        stm.setObject(1,channelFee.getcID());
        stm.setObject(2,channelFee.getPatientName());
        stm.setObject(3,channelFee.getDoctorName());
        stm.setObject(4,channelFee.getDate());
        stm.setObject(5,channelFee.getTime());
        stm.setObject(6,channelFee.getFee());

        return stm.executeUpdate()>0;
    }
    public static int getChannelCount(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM channelFee WHERE date='"+value+"'");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("count(*)");
        }

        return count;
    }

    public static Patient getPatientName(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT name FROM patient WHERE pId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Patient(
                    rst.getString("name")
            );

        } else {
            return null;
        }
    }
    public static Doctor getDoctor(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT name,type FROM doctor WHERE doctorId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Doctor(
                    rst.getString("name"),
                    rst.getString("type")
            );

        } else {
            return null;
        }
    }
    public static List<RecentChannelTM> getRecentChannel() throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM channelFee");
        ResultSet rs = pstm.executeQuery();

        List<RecentChannelTM> recentChannelTMS = new ArrayList<>();

        while (rs.next()) {
            recentChannelTMS.add(new RecentChannelTM(
                    rs.getString("channelId"),
                    rs.getString("patientName"),
                    rs.getString("doctorName"),
                    rs.getDouble("fee"),
                    rs.getString("time")

            ));
        }


        return recentChannelTMS;
    }
    public static int getChanelIncome(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(fee) AS income FROM channelFee WHERE date='"+value+"'");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("income");
        }

        return count;
    }

    public static boolean isExits(String medicineId) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT cId FROM channel");

        ResultSet rst = pstm.executeQuery();

        String id;
        while (rst.next()){
            id = rst.getString("cId");
            if(id.equals(medicineId)){
                return  true;
            }
        }
        return false;
    }
    public static int getChannelCount2(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement statement=DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM channel WHERE doctorId='"+id+"'");
        ResultSet rst=statement.executeQuery();

        int count=0;
        while (rst.next()){
            count=rst.getInt("count(*)");
        }

        return count;
    }
}
