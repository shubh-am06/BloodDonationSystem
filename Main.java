import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Blood Donation & Emergency Matching System");

        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel title = new JLabel("Blood Donation & Emergency Matching System");
        title.setBounds(150, 40, 400, 30);
        frame.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(100, 120, 100, 30);
        frame.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(200, 120, 200, 30);
        frame.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(100, 170, 100, 30);
        frame.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(200, 170, 200, 30);
        frame.add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(220, 230, 100, 35);
        frame.add(loginButton);

        loginButton.addActionListener(e -> {

            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.equals("admin") && password.equals("1234")) {

                frame.dispose();

                showDashboard();

            } else {

                JOptionPane.showMessageDialog(
                    frame,
                    "Invalid Username or Password"
                );
            }
        });

        frame.setVisible(true);
    }


    // Dashboard
    public static void showDashboard() {

        JFrame dashboard = new JFrame(
            "Blood Donation System - Dashboard"
        );

        dashboard.setSize(600, 500);
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboard.setLayout(null);

        JLabel title = new JLabel("Dashboard");
        title.setBounds(250, 30, 150, 30);
        dashboard.add(title);

        JButton donorButton = new JButton("Register Donor");
        donorButton.setBounds(100, 100, 180, 50);
        dashboard.add(donorButton);

        donorButton.addActionListener(e -> RegisterDonor.show());
        JButton searchButton = new JButton("Search Blood");
        searchButton.setBounds(320, 100, 180, 50);
        dashboard.add(searchButton);

        JButton emergencyButton = new JButton("Emergency Request");
        emergencyButton.setBounds(100, 180, 180, 50);
        dashboard.add(emergencyButton);

        JButton listButton = new JButton("Donor List");
        listButton.setBounds(320, 180, 180, 50);
        dashboard.add(listButton);
        listButton.addActionListener(e -> DonorList.show());

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(220, 280, 150, 40);
        dashboard.add(logoutButton);

        logoutButton.addActionListener(e -> {

            dashboard.dispose();

            main(null);
        });

        dashboard.setVisible(true);
    }
}
