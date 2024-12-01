import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Pattern;

class Login extends JFrame implements ActionListener {
    private Container c;
    private JLabel title, email, username, password;
    private JTextField tfemail, tfusername;
    private JPasswordField tpassword;
    private JButton login, reset;

    public Login() {
        setTitle("Login Form");
        setBounds(300, 90, 600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Login Form");
        title.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(200, 30);
        c.add(title);

        email = new JLabel("Email");
        email.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
        email.setSize(130, 20);
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
        tfusername.setSize(190, 20);
        tfusername.setLocation(220, 150);
        c.add(tfusername);

        password = new JLabel("Password");
        password.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
        password.setSize(130, 20);
        password.setLocation(100, 200);
        c.add(password);

        tpassword = new JPasswordField();
        tpassword.setFont(new Font("Arial", Font.PLAIN, 15));
        tpassword.setSize(190, 20);
        tpassword.setLocation(220, 200);
        c.add(tpassword);

        login = new JButton("Login");
        login.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
        login.setSize(100, 20);
        login.setLocation(150, 250);
        login.addActionListener(this);
        c.add(login);

        reset = new JButton("Reset");
        reset.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(260, 250);
        reset.addActionListener(this);
        c.add(reset);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            String email = tfemail.getText();
            String username = tfusername.getText();
            String password = new String(tpassword.getPassword());

            String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            String usernamePattern = "^[a-zA-Z0-9_]+$";
            String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

            if (!Pattern.matches(emailPattern, email)) {
                JOptionPane.showMessageDialog(this, "Invalid email.");
                return;
            }

            if (!Pattern.matches(usernamePattern, username)) {
                JOptionPane.showMessageDialog(this, "Invalid username. Only letters, numbers, and underscores are allowed.");
                return;
            }

            if (!Pattern.matches(passwordPattern, password)) {
                JOptionPane.showMessageDialog(this, "Invalid password. Must be at least 8 characters long and contain at least one letter and one number.");
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader("D:\\CODES\\L-S-System\\info.txt"))) {
                String line;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 3 && data[1].equals(username) && data[2].equals(password)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    openUploadWindow();  
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password combination!");
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error: File not found.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: Failed to read file.");
            }
        } else if (e.getSource() == reset) {
            tfemail.setText("");
            tfusername.setText("");
            tpassword.setText("");
        }
    }

    private void openUploadWindow() {
        SwingUtilities.invokeLater(() -> {
            upload fileUploader = new upload();
            fileUploader.setVisible(true);
        });
        dispose();  
    }

    public static void main(String[] args) {
        Login frame = new Login();
        frame.setVisible(true);
    }
}
