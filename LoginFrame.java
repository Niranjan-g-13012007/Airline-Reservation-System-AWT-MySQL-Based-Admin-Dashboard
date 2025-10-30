import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class LoginFrame extends Frame implements ActionListener {

    
    private Label titleLabel, userLabel, passLabel;
    private TextField userField;
    private TextField passField; 
    private Button loginButton, exitButton;
    private Panel mainPanel;

    
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public LoginFrame() {
        
        setTitle("Admin Login - Airline Reservation System");
        setSize(500, 350);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); 
        setLocationRelativeTo(null); 

        
        Panel titlePanel = new Panel();
        titleLabel = new Label("✈ Admin Login ✈", Label.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 51, 102));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        
        mainPanel = new Panel();
        mainPanel.setLayout(null);
        add(mainPanel, BorderLayout.CENTER);

        
        userLabel = new Label("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userLabel.setBounds(100, 40, 100, 30);
        mainPanel.add(userLabel);

        userField = new TextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userField.setBounds(210, 40, 180, 30);
        mainPanel.add(userField);

        
        passLabel = new Label("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passLabel.setBounds(100, 90, 100, 30);
        mainPanel.add(passLabel);

        passField = new TextField();
        passField.setEchoChar('*'); 
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passField.setBounds(210, 90, 180, 30);
        mainPanel.add(passField);

        
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        loginButton = new Button("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 122, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        exitButton = new Button("Exit");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exitButton.setBackground(new Color(204, 0, 0));
        exitButton.setForeground(Color.WHITE);
        exitButton.setPreferredSize(new Dimension(100, 40));
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userField.getText();
            String password = passField.getText();

            
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                
                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                
                DashboardFrame dashboard = new DashboardFrame();
                dashboard.setVisible(true);

                
                this.dispose();
            } else {
                
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == exitButton) {
            
            System.exit(0);
        }
    }
}
