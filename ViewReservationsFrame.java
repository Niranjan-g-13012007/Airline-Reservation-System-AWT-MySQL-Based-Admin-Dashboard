import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class ViewReservationsFrame extends Frame implements ActionListener {

    
    private TextArea reservationsArea;
    private Button backButton;
    private Label titleLabel;

    public ViewReservationsFrame() {
        
        setTitle("View All Reservations");
        setSize(900, 600);
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(240, 248, 255));
        setLocationRelativeTo(null);

        
        titleLabel = new Label("All Flight Reservations", Label.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 51, 102));
        add(titleLabel, BorderLayout.NORTH);

        
        reservationsArea = new TextArea();
        reservationsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        reservationsArea.setEditable(false);
        reservationsArea.setBackground(Color.WHITE);
        add(reservationsArea, BorderLayout.CENTER);

        
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        backButton = new Button("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        
        loadReservations();

        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                new DashboardFrame().setVisible(true);
            }
        });
    }

    private void loadReservations() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("%-5s | %-20s | %-15s | %-15s | %-15s | %-10s | %-10s\n", 
                                "ID", "Passenger Name", "Source", "Destination", "Travel Date", "Class", "Flight No"));
        sb.append("------------------------------------------------------------------------------------------------------------------\n");

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM reservations ORDER BY id")) {

            if (!rs.isBeforeFirst()) { 
                reservationsArea.setText("No reservations found.");
                return;
            }

            while (rs.next()) {
                sb.append(String.format("%-5d | %-20s | %-15s | %-15s | %-15s | %-10s | %-10s\n",
                        rs.getInt("id"),
                        rs.getString("passenger_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getString("travel_date"),
                        rs.getString("travel_class"),
                        rs.getString("flight_no")));
            }
            reservationsArea.setText(sb.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            reservationsArea.setText("Failed to load reservations due to a database error.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.dispose();
            new DashboardFrame().setVisible(true);
        }
    }
}
