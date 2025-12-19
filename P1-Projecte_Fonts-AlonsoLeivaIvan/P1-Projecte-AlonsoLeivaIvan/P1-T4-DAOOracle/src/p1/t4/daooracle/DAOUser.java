/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p1.t4.daooracle;
import p1.t4.model.User;
import org.mindrot.jbcrypt.BCrypt;
import p1.t4.idao.IDAOUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static p1.t4.daooracle.OracleConnection.getConnection;
/**
 *
 * @author anton
 */
public class DAOUser implements IDAOUser{
    private final Connection conn;

    public DAOUser(Connection conn) {
        this.conn = conn;
    }

    public User trobarUser(String username) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
        }
        return null;
    }

    public boolean comprobarUser(String username) throws Exception {
        String sql = "SELECT 1 FROM usuarios WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

