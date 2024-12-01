import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class Signup extends JFrame implements ActionListener {
    private Container c;
    private JLabel title, email, username, password;
    private JTextField tfemail, tfusername;
    private JPasswordField pfpassword;
    private JButton Signup, reset;
    private MainApp mainApp;

    public Signup(MainApp mainApp) {
        this.mainApp = mainApp;

        setTitle("Signup Form");
        setBounds(300, 90, 600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Signup");
        title.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(200, 30);
        c.add(title);

        email = new JLabel("Email");
        email.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
        email.setSize(120, 20);
        email.setLocation(100, 100);
        c.add(email);

        tfemail = new JTextField();
        tfemail.setFont(new Font("Arial", Font.PLAIN, 20));
        tfemail.setSize(200, 20);
        tfemail.setLocation(220, 100);
        c.add(tfemail);

        username = new JLabel("Username");
        username.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
        username.setSize(130, 20);
        username.setLocation(100, 150);
        c.add(username);

        tfusername = new JTextField();
        tfusername.setFont(new Font("Arial", Font.PLAIN, 20));
        tfusername.setSize(200, 20);
        tfusername.setLocation(220, 150);
        c.add(tfusername);

        password = new JLabel("Password");
        password.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
        password.setSize(130, 20);
        password.setLocation(100, 200);
        c.add(password);

        pfpassword = new JPasswordField();
        pfpassword.setFont(new Font("Arial", Font.PLAIN, 15));
        pfpassword.setSize(200, 20);
        pfpassword.setLocation(220, 200);
        c.add(pfpassword);

        Signup = new JButton("SignUp");
        Signup.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
        Signup.setSize(100, 20);
        Signup.setLocation(150, 250);
        Signup.addActionListener(this);
        c.add(Signup);

        reset = new JButton("Clear");
        reset.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(260, 250);
        reset.addActionListener(this);
        c.add(reset);
    }

    public void actionPerformed(ActionEvent z) {
        if (z.getSource() == Signup) {
            String email = tfemail.getText();
            String username = tfusername.getText();
            String password = new String(pfpassword.getPassword());
    
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format");
                return;
            }
    
            if (!isValidUsername(username)) {
                JOptionPane.showMessageDialog(this, "Username can only contain letters, numbers, and underscores");
                return;
            }
    
            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and contain at least one letter and one number");
                return;
            }
    
            try {
                File file = new File("D:\\CODES\\L-S-System\\info.txt");
                boolean isDuplicate = false;
                
                if (file.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 2) {
                            String existingEmail = parts[0].trim();
                            String existingUsername = parts[1].trim();
                            if (email.equals(existingEmail) || username.equals(existingUsername)) {
                                isDuplicate = true;
                                break;
                            }
                        }
                    }
                    br.close();
                }
            
                if (isDuplicate) {
                    JOptionPane.showMessageDialog(this, "Error: Email or Username already exists!");
                } else {
                    FileWriter wr = new FileWriter(file, true);
                    wr.write(email + "," + username + "," + password + "\n");
                    wr.close();
                    JOptionPane.showMessageDialog(this, "You have signed up successfully!!!");
                    dispose();
                    mainApp.reopenMainApp();
                }
            } catch (IOException x) {
                JOptionPane.showMessageDialog(this, "Sign up Error: " + x.getMessage());
            }
        } else if (z.getSource() == reset) {
            tfemail.setText("");
            tfusername.setText("");
            pfpassword.setText("");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidUsername(String username) {
        String usernameRegex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static void main(String[] args) {
        JFrame mainAppFrame = new JFrame("Main Application");
        mainAppFrame.setBounds(300, 100, 500, 500);
        mainAppFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainAppFrame.setVisible(true);

        MainApp mainApp = new MainApp();
        Signup signupFrame = new Signup(mainApp);
        signupFrame.setVisible(true);
    }
}
