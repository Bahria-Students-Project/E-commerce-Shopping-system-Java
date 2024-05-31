package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup extends JFrame implements ActionListener {

    JLabel signup,username,password,email,gender,phone,question,city,address;
    JTextField usernametextfield,emialtextfield, phonenotextfield, answertextfield, addresstextfield, citytextfield;
    JPasswordField passwordtextfield;
    JRadioButton male, female;
    ButtonGroup gendergroup;
    JButton showPasswordButton,Save,Back;

    public Signup(){

        setLayout(null);
        setTitle("SIGN UP");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/blue3.png"));
        Image i2 = i1.getImage();
        double scaleFactor = Math.min(1.0 * 800 / i2.getWidth(null), 1.0 * 500 / i2.getHeight(null));
        Image i3 = i2.getScaledInstance((int)(scaleFactor * i2.getWidth(null)), (int)(scaleFactor * i2.getHeight(null)), Image.SCALE_SMOOTH);
        ImageIcon i4 = new ImageIcon(i3);
        JLabel image = new JLabel(i4);
        image.setBounds(0, 0, 800, 500);
        add(image);


        signup = new JLabel("SIGN UP");
        signup.setFont(new Font("Times New Roman",Font.BOLD,28));
        signup.setBounds(550,10,600,40);
        image.add(signup);


        username = new JLabel("Username");
        username.setFont(new Font("Times New Roman",Font.BOLD,12));
        username.setBounds(400,40,200,30);
        image.add(username);
        usernametextfield = new JTextField();
        usernametextfield.setFont(new Font("Times New Roman", Font.BOLD, 12));
        usernametextfield.setBounds(400,65,350,25);
        image.add(usernametextfield);


        password = new JLabel("Password");
        password.setFont(new Font("Times New Roman",Font.BOLD,12));
        password.setBounds(400,90,200,30);
        image.add(password);
        passwordtextfield = new JPasswordField();
        passwordtextfield.setFont(new Font("Times New Roman", Font.BOLD, 12));
        passwordtextfield.setBounds(400,115,350,25);
        image.add(passwordtextfield);

        showPasswordButton = new JButton();
        showPasswordButton.setBounds(750, 115, 30, 25); // Adjust the position as needed
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/hide.png")); // Replace with your image path
        showPasswordButton.setIcon(icon);
        showPasswordButton.setBackground(Color.black);
        image.add(showPasswordButton);

        showPasswordButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (passwordtextfield.getEchoChar() == (char) 0) {
                passwordtextfield.setEchoChar('*');
                // Optionally, change the button icon to a "hide password" icon
                // showPasswordButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/hide.png")));
            } else {
                passwordtextfield.setEchoChar((char) 0);
                // Optionally, change the button icon back to a "show password" icon
                // showPasswordButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/show.png")));
            }
        }
    });

        email = new JLabel("Email");
        email.setFont(new Font("Times New Roman",Font.BOLD,12));
        email.setBounds(400,140,200,30);
        image.add(email);
        emialtextfield = new JTextField();
        emialtextfield.setFont(new Font("Times New Roman", Font.BOLD, 12));
        emialtextfield.setBounds(400,165,350,25);
        image.add(emialtextfield);


        gender = new JLabel("Gender");
        gender.setFont(new Font("Times New Roman",Font.BOLD,12));
        gender.setBounds(400,190,100,30);
        image.add(gender);
        male = new JRadioButton("Male");
        male.setBounds(400,215,150,25);
        male.setFont(new Font("Times New Roman", Font.BOLD, 12));
        male.setForeground(Color.BLACK);
        male.setBackground(Color.white);
        image.add(male);
        female = new JRadioButton("Female");
        female.setBounds(550,215,200,25);
        female.setFont(new Font("Times New Roman", Font.BOLD, 12));
        female.setForeground(Color.BLACK);
        female.setBackground(Color.white);
        image.add(female);

        gendergroup = new ButtonGroup();
        gendergroup.add(male);
        gendergroup.add(female);


        phone = new JLabel("Phone No");
        phone.setFont(new Font("Times New Roman",Font.BOLD,12));
        phone.setBounds(400,240,200,30);
        image.add(phone);
        phonenotextfield = new JTextField();
        phonenotextfield.setFont(new Font("Times New Roman", Font.BOLD, 12));
        phonenotextfield.setBounds(400,265,350,25);
        image.add(phonenotextfield);


        question = new JLabel("Whats your favourite color?");
        question.setFont(new Font("Times New Roman",Font.BOLD,12));
        question.setBounds(400,290,200,30);
        image.add(question);
        answertextfield = new JTextField();
        answertextfield.setFont(new Font("Times New Roman", Font.BOLD, 12));
        answertextfield.setBounds(400,315,350,25);
        image.add(answertextfield);


        city = new JLabel("City");
        city.setFont(new Font("Times New Roman",Font.BOLD,12));
        city.setBounds(400,340,200,30);
        image.add(city);
        citytextfield = new JTextField();
        citytextfield.setFont(new Font("Times New Roman", Font.BOLD, 12));
        citytextfield.setBounds(400,365,350,25);
        image.add(citytextfield);


        address = new JLabel("Address");
        address.setFont(new Font("Times New Roman",Font.BOLD,12));
        address.setBounds(400,390,200,30);
        image.add(address);
        addresstextfield = new JTextField();
        addresstextfield.setFont(new Font("Times New Roman", Font.BOLD, 12));
        addresstextfield.setBounds(400,415,350,25);
        image.add(addresstextfield);

        Color color = Color.decode("#495057");

        Back = new JButton("Back");
        Back.setBackground(color);
        Back.setForeground(Color.white);
        Back.setFont(new Font("Times New Roman",Font.BOLD,14));
        Back.setBounds(400,450,80,30);
        Back.addActionListener(this);
        image.add(Back);

        Save = new JButton("Save");
        Save.setBackground(color);
        Save.setForeground(Color.white);
        Save.setFont(new Font("Times New Roman",Font.BOLD,14));
        Save.setBounds(670,450,80,30);
        Save.addActionListener(this);
        image.add(Save);

        Back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                Back.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        Save.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                Save.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });



        getContentPane().setBackground(Color.white);

        setResizable(false);
        setSize(810,550);
        setLocation(300,100);
        setVisible(true);
    }

public void actionPerformed(ActionEvent ae){
    if (ae.getSource() == Save){
        String username = usernametextfield.getText();
        String password = passwordtextfield.getText();
        String email = emialtextfield.getText();
        String phone = phonenotextfield.getText();
        String gender = male.isSelected() ? "Male" : "Female";
        String answer = answertextfield.getText();
        String city = citytextfield.getText();
        String address = addresstextfield.getText();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || answer.isEmpty() || city.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
            return;
        }

        // Check if gender is selected
        if(!male.isSelected() && !female.isSelected()){
            JOptionPane.showMessageDialog(null, "Please select a gender");
            return;
        }

        // Check if username, password, answer, city, and address are between 3 and 25 characters
        if(username.length() < 3 || username.length() > 25 || password.length() < 3 || password.length() > 25 || answer.length() < 3 || answer.length() > 25 || city.length() < 3 || city.length() > 25 || address.length() < 3 || address.length() > 25){
            JOptionPane.showMessageDialog(null, "Username, password, favorite color, city, and address should be between 3 and 25 characters");
            return;
        }
        // Check if username contains any integer
        if(username.matches(".*\\d.*")){
            JOptionPane.showMessageDialog(null, "Username should not contain any integer");
            return;
        }

        if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            JOptionPane.showMessageDialog(null, "Please enter a valid email");
            return;
        }


        // Check if phone number is of 11 digits
        if(phone.length() != 11){
            JOptionPane.showMessageDialog(null, "Phone number should be of 11 digits");
            return;
        }

        // Check if any field except password contains special characters
        if(username.matches(".*[^a-zA-Z0-9 ].*") || email.matches(".*[^a-zA-Z0-9@.].*") || phone.matches(".*[^0-9].*") || answer.matches(".*[^a-zA-Z0-9 ].*") || city.matches(".*[^a-zA-Z0-9 ].*") || address.matches(".*[^a-zA-Z0-9 ].*")){
            JOptionPane.showMessageDialog(null, "Fields should not contain special characters");
            return;
        }

        // Database connection parameters
        String url = "jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true";

        try {
            // Establish a connection
            Connection conn = DriverManager.getConnection(url);

            // Create a SQL query
            String sql = "INSERT INTO [user] (uname, upassword, uemail, uphone, ugender, uans, ucity, uaddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Create a statement
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameters
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, gender);
            stmt.setString(6, answer);
            stmt.setString(7, city);
            stmt.setString(8, address);

            // Execute the query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "A new user was inserted successfully!");
                setVisible(false);
                new Login().setVisible(true);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    } else if (ae.getSource() == Back){
        setVisible(false);
        new Login().setVisible(true);
    }
}
    public static void main(String[] args) {
        new Signup().setVisible(true);

    }
}