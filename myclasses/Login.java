package myclasses;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Login extends JFrame implements ActionListener {

    public static String USERNAME;
    protected static boolean loginFlag;
    protected static boolean isAdmin;
    protected static String fullName;
    protected static String oldPassword;
    protected static String phoneNumber;
    protected static String fullUsername;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final ImageIcon on;
    private final ImageIcon off;
    private final JToggleButton EyeBtn;
    private final JButton signup;
    private final JButton exitButton;
    private final JButton loginButton;

    public Login() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setForeground(Color.LIGHT_GRAY);
        contentPane.setBackground(new Color(82, 145, 145));
        contentPane.setBorder(null);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel LoginTxt = new JLabel("LOGIN");
        LoginTxt.setForeground(new Color(215, 210, 203));
        LoginTxt.setFont(new Font("Verdana", Font.BOLD, 22));
        LoginTxt.setBounds(450, 120, 83, 30);
        contentPane.add(LoginTxt);

        JLabel UsernameTxt = new JLabel("Username");
        UsernameTxt.setForeground(new Color(215, 210, 203));
        UsernameTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
        UsernameTxt.setBounds(350, 170, 94, 30);
        contentPane.add(UsernameTxt);

        usernameField = new JTextField();
        usernameField.setOpaque(false);
        usernameField.setForeground(new Color(219, 226, 233));
        usernameField.setFont(new Font("Times New Roman", Font.BOLD, 17));
        usernameField.setBounds(350, 205, 290, 30);
        usernameField.setBorder(BorderFactory.createEmptyBorder());
        Border userBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 255, 255));
        usernameField.setBorder(userBorder);
        contentPane.add(usernameField);

        JLabel PasswordTxt = new JLabel("Password");
        PasswordTxt.setForeground(new Color(215, 210, 203));
        PasswordTxt.setFont(new Font("Verdana", Font.PLAIN, 18));
        PasswordTxt.setBounds(350, 245, 94, 30);
        contentPane.add(PasswordTxt);

        passwordField = new JPasswordField();
        passwordField.setOpaque(false);
        passwordField.setForeground(new Color(219, 226, 233));
        passwordField.setFont(new Font("Verdana", Font.BOLD, 17));
        passwordField.setEchoChar('*');
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        Border passBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 255, 255));
        passwordField.setBorder(passBorder);
        passwordField.setBounds(350, 280, 290, 30);
        contentPane.add(passwordField);

        BufferedImage imgOn = null;
        BufferedImage imgOff = null;
        try {
            imgOn = ImageIO.read(Objects.requireNonNull(Login.class.getResource("../images/show.png")));
            imgOff = ImageIO.read(Objects.requireNonNull(Login.class.getResource("../images/hide.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert imgOn != null : "The 'show' image could not be loaded";
        assert imgOff != null : "The 'hide' image could not be loaded";
        Image imgON = imgOn.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        Image imgOFF = imgOff.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        on = new ImageIcon(imgON);
        off = new ImageIcon(imgOFF);
        EyeBtn = new JToggleButton(off);
        EyeBtn.setOpaque(false);
        EyeBtn.setForeground(Color.DARK_GRAY);
        EyeBtn.setBackground(Color.DARK_GRAY);
        EyeBtn.setBounds(655, 280, 33, 20);
        EyeBtn.setFocusable(false);
        contentPane.add(EyeBtn);

        signup = new JButton("Don't have an account?");
        signup.setForeground(new Color(17, 17, 17));
        signup.setBorder(BorderFactory.createEmptyBorder());
        signup.setFont(new Font("Calibri", Font.BOLD, 14));
        contentPane.setBackground(new Color(82, 145, 145));
        signup.setBounds(520, 320, 189, 30);
        signup.setFocusable(false);
        contentPane.add(signup);

        JLabel LoginLblTxt = new JLabel("Supermarket");
        LoginLblTxt.setForeground(Color.WHITE);
        LoginLblTxt.setFont(new Font("Modern No. 20", Font.PLAIN, 54));
        LoginLblTxt.setBounds(350, 20, 404, 82);
        contentPane.add(LoginLblTxt);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        loginButton.setBounds(410, 380, 153, 40);
        loginButton.setFocusable(false);
        contentPane.add(loginButton);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 10));
        exitButton.setBounds(900, 15, 70, 21);
        exitButton.setFocusable(false);
        contentPane.add(exitButton);

        EyeBtn.addActionListener(this);
        signup.addActionListener(this);
        exitButton.addActionListener(this);
        loginButton.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = usernameField.getText();
        String pass = String.valueOf(passwordField.getPassword());

        boolean userEmpty = user.isEmpty();
        boolean passEmpty = pass.isEmpty();

        if (e.getSource() == EyeBtn) {
            if (EyeBtn.isSelected()) {
                EyeBtn.setIcon(on);
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                EyeBtn.setIcon(off);
                passwordField.setEchoChar('*'); // Hide password
            }
        } else if (e.getSource() == signup) {
            this.setVisible(false);
            new Signup();
        } else if (e.getSource() == exitButton) {
            int yesORno =
                    JOptionPane.showConfirmDialog(
                            null, "Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);

            if (yesORno == 0) {
                System.exit(1);
            }
        } else if (e.getSource() == loginButton) {
            if (userEmpty || passEmpty) {
                JOptionPane.showMessageDialog(
                        null, "Please fill all of the fields.", "Warning!", JOptionPane.WARNING_MESSAGE);
            } else {
                boolean userbool = false;
                isAdmin = false;
                try {
                    File adminFile = new File("./files/admin_login.json");
                    if (!adminFile.exists()) {
                        createDefaultAdminFile(adminFile);
                    }

                    JSONObject adminData = readJsonFile("./files/admin_login.json");
                    if (adminData != null) {
                        if (adminData.getString("username").equals(user) && adminData.getString("password").equals(pass)) {
                            loginFlag = true;
                            isAdmin = true;
                            USERNAME = user;
                            this.setVisible(false);
                            new DashBoard();
                            return;
                        }
                    }

                    JSONObject userData = readJsonFile("./files/user_login.json");
                    if (userData != null) {
                        JSONArray users = userData.getJSONArray("users");
                        for (int i = 0; i < users.length(); i++) {
                            JSONObject userObj = users.getJSONObject(i);
                            if (userObj.getString("username").equals(user) && userObj.getString("password").equals(pass)) {
                                loginFlag = true;
                                userbool = true;
                                USERNAME = user;
                                fullName = userObj.getString("fullName");
                                phoneNumber = userObj.getString("phoneNumber");
                                oldPassword = userObj.getString("password");
                                fullUsername = "User Name : " + user;
                                this.setVisible(false);
                                new UDashBoard();
                                return;
                            }
                        }
                    }

                    if (!userbool && !isAdmin) {
                        JOptionPane.showMessageDialog(
                                null, "Invalid Name or Password!", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            null, "An error occurred while processing login.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    private void createDefaultAdminFile(File file) throws IOException {
        JSONObject admin = new JSONObject();
        admin.put("username", "admin");
        admin.put("password", "admin");

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(admin.toString());
        }
    }

    private JSONObject readJsonFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return new JSONObject(new JSONTokener(reader));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
