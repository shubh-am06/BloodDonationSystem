import javax.swing.*;
import java.sql.*;

public class RegisterDonor {
    static final String URL = "jdbc:mysql://localhost:3306/blood_db";
    static final String USER = "root";
    static final String PASS = "Dadu@2007";

    static void row(JFrame f, String label, JComponent c, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(30, y, 130, 25);
        c.setBounds(170, y, 180, 25);
        f.add(l); f.add(c);
    }

    static void load(JComboBox<String> box, String sql) {
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {
            while (r.next()) box.addItem(r.getInt(1) + " - " + r.getString(2));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    static int id(JComboBox<String> box) {
        return Integer.parseInt(((String) box.getSelectedItem()).split(" - ")[0]);
    }

    public static void show() {
        JFrame f = new JFrame("Register Donor");
        f.setSize(400, 450);
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        JTextField name = new JTextField(), dob = new JTextField();
        JTextField phone = new JTextField(), last = new JTextField();
        JComboBox<String> gender = new JComboBox<>(new String[]{"M", "F", "O"});
        JComboBox<String> group = new JComboBox<>();
        JComboBox<String> city = new JComboBox<>();
        load(group, "SELECT group_id, group_name FROM blood_group");
        load(city, "SELECT city_id, city_name FROM city");

        row(f, "Name", name, 30);
        row(f, "Gender", gender, 70);
        row(f, "DOB (YYYY-MM-DD)", dob, 110);
        row(f, "Phone", phone, 150);
        row(f, "Blood Group", group, 190);
        row(f, "City", city, 230);
        row(f, "Last Donation", last, 270);

        JButton save = new JButton("Save");
        save.setBounds(140, 330, 100, 35);
        f.add(save);

        save.addActionListener(e -> {
            String sql = "INSERT INTO donor (name, gender, dob, phone, group_id, city_id, last_donation_date) VALUES (?,?,?,?,?,?,?)";
            try (Connection c = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, name.getText());
                p.setString(2, (String) gender.getSelectedItem());
                p.setDate(3, Date.valueOf(dob.getText().trim()));
                p.setString(4, phone.getText());
                p.setInt(5, id(group));
                p.setInt(6, id(city));
                if (last.getText().isBlank()) p.setNull(7, Types.DATE);
                else p.setDate(7, Date.valueOf(last.getText().trim()));
                p.executeUpdate();
                JOptionPane.showMessageDialog(f, "Donor saved!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(f, "Date format YYYY-MM-DD asava");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(f, ex.getMessage());
            }
        });
        f.setVisible(true);
    }
}