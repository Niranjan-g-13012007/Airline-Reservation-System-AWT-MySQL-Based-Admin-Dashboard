import java.awt.*;
import java.awt.event.*;

public class DashboardFrame extends Frame implements ActionListener {

    
    private Button addReservationBtn, viewReservationsBtn, updateReservationBtn, deleteReservationBtn, logoutBtn;
    private Label titleLabel;

    public DashboardFrame() {
        
        setTitle("Admin Dashboard - Airline Reservation System");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); 
        setLocationRelativeTo(null); 

        
        Panel titlePanel = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        titleLabel = new Label("✈ Welcome to the Admin Dashboard ✈");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 51, 102));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new GridLayout(5, 1, 20, 20)); 
        Panel centerPanel = new Panel(new GridBagLayout()); 
        centerPanel.add(mainPanel);
        add(centerPanel, BorderLayout.CENTER);

        
        addReservationBtn = createMenuButton("Add New Reservation");
        viewReservationsBtn = createMenuButton("View All Reservations");
        updateReservationBtn = createMenuButton("Update Reservation");
        deleteReservationBtn = createMenuButton("Delete Reservation");
        logoutBtn = createMenuButton("Logout / Exit");

        
        mainPanel.add(addReservationBtn);
        mainPanel.add(viewReservationsBtn);
        mainPanel.add(updateReservationBtn);
        mainPanel.add(deleteReservationBtn);
        mainPanel.add(logoutBtn);

        
        logoutBtn.setBackground(new Color(204, 0, 0));

        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    
    private Button createMenuButton(String label) {
        Button button = new Button(label);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(new Color(0, 122, 204));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(300, 60));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        this.dispose();

        if (e.getSource() == addReservationBtn) {
            
            new AddReservationFrame().setVisible(true);
        } else if (e.getSource() == viewReservationsBtn) {
            
            new ViewReservationsFrame().setVisible(true);
        } else if (e.getSource() == updateReservationBtn) {
            
            new UpdateReservationFrame().setVisible(true);
        } else if (e.getSource() == deleteReservationBtn) {
            
            new DeleteReservationFrame().setVisible(true);
        } else if (e.getSource() == logoutBtn) {
            
            new LoginFrame().setVisible(true);
        }
    }
}
