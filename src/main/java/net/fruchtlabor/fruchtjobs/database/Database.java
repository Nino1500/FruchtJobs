package net.fruchtlabor.fruchtjobs.database;

import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.logs.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database {
    Plugin plugin;

    public Database(Plugin plugin) {
        this.plugin = plugin;
    }

    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:"+plugin.getConfig().getInt("MySQL.port")+"/"+plugin.getConfig().getString("MySQL.name"), plugin.getConfig().getString("MySQL.user"), plugin.getConfig().getString("MySQL.password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void createTable(String jobname){
        String sql = "CREATE TABLE IF NOT EXISTS " + jobname +
                "(uuid VARCHAR(255) not NULL," +
                "lvl int," +
                "exp double," +
                "plvl int," +
                "PRIMARY KEY (uuid))";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createLogTable(){
        String sql = "CREATE TABLE IF NOT EXISTS log" +
                "(x double," +
                "y double," +
                "z double," +
                "world VARCHAR(128)," +
                "uuid VARCHAR(255)," +
                "material VARCHAR(128)," +
                "rawlocation VARCHAR(255)," +
                "timestmp TIMESTAMP," +
                "PRIMARY KEY (rawlocation))";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertAllPlayers(ArrayList<JobPlayer> list, String jobname){
        try {
            String sql = "INSERT INTO "+jobname+" VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE lvl= ?, exp= ?, plvl= ?";
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (JobPlayer jobPlayer : list) {
                statement.setString(1, jobPlayer.getUuid().toString());
                statement.setInt(2, jobPlayer.getLvl());
                statement.setDouble(3, jobPlayer.getExp());
                statement.setInt(4, jobPlayer.getPlvl());
                statement.setInt(5, jobPlayer.getLvl());
                statement.setDouble(6, jobPlayer.getExp());
                statement.setInt(7, jobPlayer.getPlvl());
                statement.addBatch();
            }
            int[] numUpdates = statement.executeBatch();
            System.out.println("Saved: "+numUpdates.length+" Players! Job: "+jobname);
            connection.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertAllLocations(HashMap<String, ArrayList<SimpleLocation>> list){
        try {
            String sql = "INSERT INTO log VALUES(?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE uuid=?";
            Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Map.Entry<String, ArrayList<SimpleLocation>> entry : list.entrySet()){
                for (SimpleLocation loc : entry.getValue()){
                    statement.setDouble(1, loc.getX());
                    statement.setDouble(2, loc.getY());
                    statement.setDouble(3, loc.getZ());
                    statement.setString(4, loc.getWorld());
                    statement.setString(5, loc.getUuid().toString());
                    statement.setString(6, loc.getMaterial());
                    statement.setString(7, loc.getRawlocation());
                    statement.setTimestamp(8, loc.getTimestamp());
                    statement.setString(9, loc.getUuid().toString());
                    statement.addBatch();
                }
            }
            int[] numUpdates = statement.executeBatch();
            System.out.println("Saved: "+numUpdates.length+" locations to the log!");
            connection.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<SimpleLocation> getAllLocationsOfLog(){

        String sql = "SELECT * FROM log";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<SimpleLocation> list = new ArrayList<>();
        try {
            connection = connect();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()){
                double x = rs.getDouble(1);
                double y = rs.getDouble(2);
                double z = rs.getDouble(3);
                String world = rs.getString(4);
                UUID uuid = UUID.fromString(rs.getString(5));
                String material = rs.getString(6);
                String rawlocation = rs.getString(7);
                Timestamp timestamp = rs.getTimestamp(8);
                SimpleLocation simpleLocation = new SimpleLocation(x, y, z, world, uuid, material, rawlocation, timestamp);
                list.add(simpleLocation);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return list;
    }

    public ArrayList<JobPlayer> getPlayersByJob(String jobname){
        String sql = "SELECT * FROM "+jobname;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<JobPlayer> list = new ArrayList<>();
        try {
            connection = connect();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next())
                list.add(new JobPlayer(jobname, rs.getInt("lvl"), rs.getDouble("exp"), UUID.fromString(rs.getString("uuid")), rs.getInt("plvl")));
            return list;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
