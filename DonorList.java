import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DonorList {
    static DefaultTableModel model;

    static Connection conn() throws SQLException {
        return DriverManager.getConnection(
            RegisterDonor.URL, RegisterDonor.USER, RegisterDonor.PASS);
    }

    static void load() {
        model.setRowCount(0);
        String sql = "SELECT d.donor_id, d.name, d.gender, d.dob, d.phone, "
            + "b.group_name, c.city_name, d.last_donation_date "
            + "FROM donor d JOIN blood_group b ON d.group_id = b.group_id "
            + "JOIN city c ON d.city_id = c.city_id ORDER BY d.donor_id";
        try (Connection con = conn();
             Statement s = con.createStatement();
             ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                model.addRow(new Object[]{r.getInt(1), r.getString(2),
                    r.getString(3), r.getDate(4), r.getString(5),
                    r.getString(6), r.getString(7), r.getDate(8)});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void show() {
        JFrame f = new JFrame("Donor List");
        f.setSize(900, 450);
        f.setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Gender",
            "DOB", "Phone", "Blood Group", "City", "Last Donation"}, 0);
        JTable table = new JTable(model);
        f.add(new JScrollPane(table));

        JButton refresh = new JButton("Refresh");
        JButton delete = new JButton("Delete Selected");
        JPanel bar = new JPanel();
        bar.add(refresh);
        bar.add(delete);
        f.add(bar, java.awt.BorderLayout.SOUTH);

        refresh.addActionListener(e -> load());

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(f, "Aadhi donor select kara");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            int ok = JOptionPane.showConfirmDialog(f,
                "Donor " + id + " delete karaycha?", "Confirm",
                JOptionPane.YES_NO_OPTION);
            if (ok != JOptionPane.YES_OPTION) return;
            try (Connection con = conn();
                 PreparedStatement p = con.prepareStatement(
                     "DELETE FROM donor WHERE donor_id = ?")) {
                p.setInt(1, id);
                p.executeUpdate();
                load();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(f, ex.getMessage());
            }
        });

        load();
        f.setVisible(true);
    }
}