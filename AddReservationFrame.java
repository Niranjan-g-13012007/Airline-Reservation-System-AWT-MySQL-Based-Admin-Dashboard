import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class AddReservationFrame extends Frame implements ActionListener {

    
    private Label titleLabel, nameLabel, sourceLabel, destLabel, dateLabel, classLabel;
    private TextField nameField, dateField;
    private Choice sourceChoice, destChoice, classChoice;
    private Button submitButton, backButton;

    public AddReservationFrame() {
        
        setTitle("Add New Reservation");
        setSize(600, 550);
        setLayout(null);
        setBackground(new Color(240, 248, 255));
        setLocationRelativeTo(null);

        
        titleLabel = new Label("Enter Reservation Details", Label.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBounds(100, 50, 400, 40);
        add(titleLabel);

        
        nameLabel = new Label("Passenger Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setBounds(100, 120, 150, 30);
        add(nameLabel);
        nameField = new TextField();
        nameField.setBounds(260, 120, 250, 30);
        add(nameField);

        
        sourceLabel = new Label("Source:");
        sourceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sourceLabel.setBounds(100, 170, 150, 30);
        add(sourceLabel);
        sourceChoice = new Choice();
        String[] cities = {"New York", "London", "Paris", "Tokyo", "Dubai", "Singapore"};
        for (String city : cities) sourceChoice.add(city);
        sourceChoice.setBounds(260, 170, 250, 30);
        add(sourceChoice);

        
        destLabel = new Label("Destination:");
        destLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        destLabel.setBounds(100, 220, 150, 30);
        add(destLabel);
        destChoice = new Choice();
        for (String city : cities) destChoice.add(city);
        destChoice.setBounds(260, 220, 250, 30);
        add(destChoice);

        
        dateLabel = new Label("Travel Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateLabel.setBounds(100, 270, 200, 30);
        add(dateLabel);
        dateField = new TextField();
        dateField.setBounds(310, 270, 200, 30);
        add(dateField);

        
        classLabel = new Label("Travel Class:");
        classLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        classLabel.setBounds(100, 320, 150, 30);
        add(classLabel);
        classChoice = new Choice();
        classChoice.add("Economy");
        classChoice.add("Business");
        classChoice.setBounds(260, 320, 250, 30);
        add(classChoice);

        
        submitButton = new Button("Add Reservation");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitButton.setBackground(new Color(0, 122, 204));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBounds(150, 420, 180, 40);
        submitButton.addActionListener(this);
        add(submitButton);

        backButton = new Button("Back to Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(108, 117, 125));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(350, 420, 180, 40);
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
        if (e.getSource() == submitButton) {
            addReservation();
        } else if (e.getSource() == backButton) {
            this.dispose();
            new DashboardFrame().setVisible(true);
        }
    }

    private void addReservation() {
        String name = nameField.getText();
        String source = sourceChoice.getSelectedItem();
        String destination = destChoice.getSelectedItem();
        String travelDate = dateField.getText();
        String travelClass = classChoice.getSelectedItem();
        String flightNo = "FL" + (1000 + new Random().nextInt(9000));

        if (name.isEmpty() || travelDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (source.equals(destination)) {
            JOptionPane.showMessageDialog(this, "Source and Destination cannot be the same.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO reservations (passenger_name, source, destination, travel_date, travel_class, flight_no) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, source);
            pstmt.setString(3, destination);
            pstmt.setString(4, travelDate);
            pstmt.setString(5, travelClass);
            pstmt.setString(6, flightNo);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Reservation added successfully!\nFlight Number: " + flightNo, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                nameField.setText("");
                dateField.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
