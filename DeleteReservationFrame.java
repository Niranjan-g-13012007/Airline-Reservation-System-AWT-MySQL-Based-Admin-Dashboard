import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class DeleteReservationFrame extends Frame implements ActionListener {

    
    private Label titleLabel, searchLabel, detailsLabel;
    private TextField searchField;
    private Button searchButton, deleteButton, backButton;
    private Panel detailsPanel;
    private int reservationId = -1; 

    public DeleteReservationFrame() {
        
        setTitle("Delete Reservation");
        setSize(700, 450);
        setLayout(null);
        setBackground(new Color(255, 240, 240)); 
        setLocationRelativeTo(null);

        
        titleLabel = new Label("Delete Reservation Record", Label.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(153, 0, 0));
        titleLabel.setBounds(150, 50, 400, 40);
        add(titleLabel);

        
        searchLabel = new Label("Enter Flight Number to Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchLabel.setBounds(100, 120, 250, 30);
        add(searchLabel);

        searchField = new TextField();
        searchField.setBounds(360, 120, 150, 30);
        add(searchField);

        searchButton = new Button("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBounds(520, 120, 80, 30);
        searchButton.addActionListener(this);
        add(searchButton);

        
        detailsPanel = new Panel();
        detailsPanel.setLayout(new BorderLayout(10, 10));
        detailsPanel.setBounds(100, 180, 500, 100);
        detailsPanel.setVisible(false);
        add(detailsPanel);

        detailsLabel = new Label("", Label.CENTER);
        detailsLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        detailsPanel.add(detailsLabel, BorderLayout.CENTER);

        
        deleteButton = new Button("Delete Reservation");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBounds(150, 350, 200, 40);
        deleteButton.addActionListener(this);
        add(deleteButton);

        backButton = new Button("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(370, 350, 180, 40);
        backButton.addActionListener(this);
        add(backButton);

        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                new DashboardFrame().setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchReservation();
        } else if (e.getSource() == deleteButton) {
            deleteReservation();
        } else if (e.getSource() == backButton) {
            this.dispose();
            new DashboardFrame().setVisible(true);
        }
    }

    private void searchReservation() {
        String flightNo = searchField.getText();
        if (flightNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a flight number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "SELECT * FROM reservations WHERE flight_no = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, flightNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                reservationId = rs.getInt("id");
                String details = String.format("Passenger: %s, From: %s, To: %s, Date: %s",
                        rs.getString("passenger_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getString("travel_date"));
                detailsLabel.setText(details);
                detailsPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Reservation not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                detailsPanel.setVisible(false);
                reservationId = -1;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteReservation() {
        if (reservationId == -1) {
            JOptionPane.showMessageDialog(this, "Please search for a reservation first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this reservation?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sql = "DELETE FROM reservations WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, reservationId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Reservation deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    detailsPanel.setVisible(false);
                    searchField.setText("");
                    reservationId = -1;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
