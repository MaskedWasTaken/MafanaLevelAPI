package me.TahaCheji.mysqlData;

import me.TahaCheji.Levels;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerLevelSQLGetter {
    private final Levels plugin;
    public PlayerLevelSQLGetter(Levels plugin) {
        this.plugin = plugin;
    }
    public void createTable() {
        PreparedStatement ps;
        try {
            ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerlevel "
            + "(NAME VARCHAR(100),UUID VARCHAR(100),LEVEL INT(100),XP INT(100))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if(!exists(uuid)) {
                PreparedStatement ps2 = plugin.mySQL.getConnection().prepareStatement("INSERT IGNORE INTO playerlevel " +
                        "(NAME,UUID) VALUES (?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT * FROM playerlevel WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
