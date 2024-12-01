import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainApp extends JFrame {
    private JFrame frame;
    private JButton signupButton;
    private JButton loginButton;

    public MainApp() {
        frame = new JFrame("Login/Signup");
        frame.setBounds(300, 900, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Signup button clicked");
                Signup sign = new Signup(MainApp.this);
                sign.setVisible(true);
                frame.dispose();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login button clicked");
                login();
            }
        });

        frame.add(signupButton);
        frame.add(loginButton);

        frame.setVisible(true);
    }

    public void login() {
        Login login = new Login();
        login.setVisible(true);
        frame.dispose();
    }
    public void reopenMainApp() {
        frame.setVisible(true); 
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainApp();
            }
        });
    }
}