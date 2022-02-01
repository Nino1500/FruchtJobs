package net.fruchtlabor.fruchtjobs.database;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.logs.MaterialType;
import net.fruchtlabor.fruchtjobs.logs.MaterialsEntityLog;
import net.fruchtlabor.fruchtjobs.logs.MaterialsLog;
import net.fruchtlabor.fruchtjobs.logs.SimpleLocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
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
            connection = DriverManager.getConnection("jdbc:mysql://"+plugin.getConfig().getString("MySQL.host")+":"+plugin.getConfig().getInt("MySQL.port")+"/"+plugin.getConfig().getString("MySQL.name"), plugin.getConfig().getString("MySQL.user"), plugin.getConfig().getString("MySQL.password"));
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

    public void createMaterialsTable(){
        String sql = "CREATE TABLE IF NOT EXISTS materials (material VARCHAR(64) PRIMARY KEY,experience DOUBLE,job VARCHAR(32),type ENUM('BREAK','FISH','KILL'),atlvl integer,enchantment VARCHAR(32),enchantment_lvl integer)";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createMaterialsEntityTable(){
        String sql = "CREATE TABLE IF NOT EXISTS entities (entity VARCHAR(64) PRIMARY KEY,experience DOUBLE,job VARCHAR(32),type ENUM('KILL'),atlvl integer)";
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
            stmt.close();
        }catch (SQLException e){
            e.printStackTrace();
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
    public ArrayList<MaterialsLog> getMaterialLogByJob(Job job) {
        String sql = "SELECT * FROM materials WHERE job = ?";
        ArrayList<MaterialsLog> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(sql);
            statement.setString(1, job.getName());
            rs = statement.executeQuery();
            while (rs.next()) {
                Material material = Material.matchMaterial(rs.getString("material"));
                double exp = rs.getDouble("experience");
                MaterialType materialType = MaterialType.valueOf(rs.getString("type"));
                int atlvl = rs.getInt("atlvl");
                Enchantment enchantment = Enchantment.getByName(rs.getString("enchantment"));
                int ench_lvl = rs.getInt("enchantment_lvl");
                if (enchantment == null){
                    list.add(new MaterialsLog(material, job, exp, materialType, atlvl));
                }else{
                    list.add(new MaterialsLog(material, job, exp, materialType, atlvl, enchantment, ench_lvl));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<MaterialsEntityLog> getEntityLogByJob(Job job){
        String sql = "SELECT * FROM entities WHERE job = ?";
        ArrayList<MaterialsEntityLog> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(sql);
            statement.setString(1, job.getName());
            rs = statement.executeQuery();
            while (rs.next()){
                EntityType entityType = EntityType.valueOf(rs.getString("entity"));
                double exp = rs.getDouble("experience");
                MaterialType type = MaterialType.valueOf(rs.getString("type"));
                int atlvl = rs.getInt("atlvl");
                list.add(new MaterialsEntityLog(entityType, job, exp, type, atlvl));
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void changeMaterialsLog(Material material, double exp){
        String sql = "UPDATE materials SET experience = ? WHERE material = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(sql);
            statement.setDouble(1,exp);
            statement.setString(2,material.name());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeEntityLog(EntityType entityType, double exp){
        String sql = "UPDATE entities SET experience = ? WHERE entity = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connect();
            statement = connection.prepareStatement(sql);
            statement.setDouble(1,exp);
            statement.setString(2,entityType.name());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//        String sql = "CREATE TABLE IF NOT EXISTS materials (material VARCHAR(64),experience DOUBLE,job VARCHAR(32),type ENUM('BREAK','FISH','KILL'),atlvl integer,enchantment VARCHAR(32),enchantment_lvl integer)";
    public void insertMaterialsLog(MaterialsLog materialsLog){
        String sql = "INSERT INTO materials (material, experience, job, type, atlvl, enchantment, enchantment_lvl) VALUES (?,?,?,?,?,?,?)";
        try {
            Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, materialsLog.getMaterial().name());
            pstmt.setDouble(2, materialsLog.getExperience());
            pstmt.setString(3, materialsLog.getJob().getName());
            pstmt.setString(4, materialsLog.getType().name());
            pstmt.setInt(5, materialsLog.getAtLevel());
            if (materialsLog.getEnchantment() == null){
                pstmt.setString(6, "none");
                pstmt.setInt(7, 0);
            }else{
                pstmt.setString(6, materialsLog.getEnchantment().getName());
                pstmt.setInt(7, materialsLog.getEnchant_level());
            }
            pstmt.execute();
            connection.close();
            pstmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertEntitiesLog(MaterialsEntityLog materialsEntityLog){
        String sql = "INSERT INTO entities (entity, experience, job, type, atlvl) VALUES (?,?,?,?,?)";
        try {
            Connection connection = connect();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, materialsEntityLog.getEntityType().name());
            pstmt.setDouble(2, materialsEntityLog.getExperience());
            pstmt.setString(3, materialsEntityLog.getJob().getName());
            pstmt.setString(4, materialsEntityLog.getType().name());
            pstmt.setInt(5, materialsEntityLog.getAtLvl());
            pstmt.execute();
            connection.close();
            pstmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
