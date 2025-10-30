import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class UpdateReservationFrame extends Frame implements ActionListener {

    
    private Label titleLabel, searchLabel, nameLabel, sourceLabel, destLabel, dateLabel, classLabel;
    private TextField searchField, nameField, dateField;
    private Choice sourceChoice, destChoice, classChoice;
    private Button searchButton, updateButton, backButton;
    private Panel detailsPanel;
    private int reservationId = -1; 

    public UpdateReservationFrame() {
        
        setTitle("Update Reservation");
        setSize(700, 650);
        setLayout(null);
        setBackground(new Color(240, 248, 255));
        setLocationRelativeTo(null);

        
        titleLabel = new Label("Update Reservation Details", Label.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 51, 102));
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
        detailsPanel.setLayout(null);
        detailsPanel.setBounds(50, 180, 600, 450);
        detailsPanel.setVisible(false);
        add(detailsPanel);

        
        nameLabel = new Label("Passenger Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setBounds(50, 20, 150, 30);
        detailsPanel.add(nameLabel);
        nameField = new TextField();
        nameField.setBounds(210, 20, 250, 30);
        detailsPanel.add(nameField);

        sourceLabel = new Label("Source:");
        sourceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sourceLabel.setBounds(50, 70, 150, 30);
        detailsPanel.add(sourceLabel);
        sourceChoice = new Choice();
        String[] cities = {"New York", "London", "Paris", "Tokyo", "Dubai", "Singapore"};
        for (String city : cities) sourceChoice.add(city);
        sourceChoice.setBounds(210, 70, 250, 30);
        detailsPanel.add(sourceChoice);

        destLabel = new Label("Destination:");
        destLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        destLabel.setBounds(50, 120, 150, 30);
        detailsPanel.add(destLabel);
        destChoice = new Choice();
        for (String city : cities) destChoice.add(city);
        destChoice.setBounds(210, 120, 250, 30);
        detailsPanel.add(destChoice);

        dateLabel = new Label("Travel Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateLabel.setBounds(50, 170, 200, 30);
        detailsPanel.add(dateLabel);
        dateField = new TextField();
        dateField.setBounds(260, 170, 200, 30);
        detailsPanel.add(dateField);

        classLabel = new Label("Travel Class:");
        classLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        classLabel.setBounds(50, 220, 150, 30);
        detailsPanel.add(classLabel);
        classChoice = new Choice();
        classChoice.add("Economy");
        classChoice.add("Business");
        classChoice.setBounds(210, 220, 250, 30);
        detailsPanel.add(classChoice);

    
        updateButton = new Button("Update Reservation");
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        updateButton.setBackground(new Color(40, 167, 69));
        updateButton.setForeground(Color.WHITE);
        updateButton.setBounds(150, 320, 200, 40);
        updateButton.addActionListener(this);
        detailsPanel.add(updateButton);

        backButton = new Button("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(400, 580, 180, 40);
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
        } else if (e.getSource() == updateButton) {
            updateReservation();
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
                nameField.setText(rs.getString("passenger_name"));
                sourceChoice.select(rs.getString("source"));
                destChoice.select(rs.getString("destination"));
                dateField.setText(rs.getString("travel_date"));
                classChoice.select(rs.getString("travel_class"));
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

    private void updateReservation() {
        if (reservationId == -1) {
            JOptionPane.showMessageDialog(this, "Please search for a reservation first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText();
        String source = sourceChoice.getSelectedItem();
        String destination = destChoice.getSelectedItem();
        String travelDate = dateField.getText();
        String travelClass = classChoice.getSelectedItem();

        if (name.isEmpty() || travelDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "UPDATE reservations SET passenger_name = ?, source = ?, destination = ?, travel_date = ?, travel_class = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, source);
            pstmt.setString(3, destination);
            pstmt.setString(4, travelDate);
            pstmt.setString(5, travelClass);
            pstmt.setInt(6, reservationId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Reservation updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                detailsPanel.setVisible(false);
                searchField.setText("");
                reservationId = -1;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
