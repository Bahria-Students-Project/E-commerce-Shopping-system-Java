package user;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import connection.MyConnection;
import java.sql.ResultSet;


public class UserAccount extends JFrame implements ActionListener {

    JTextField idtextfield,usernametextfield,emialtextfield, phonenotextfield, answertextfield, addresstextfield, citytextfield;
    JPasswordField passwordtextfield;
    JComboBox genderComboBox;
    JButton showPasswordButton,update,delete,back;
    static int userId;

    UserAccount() {
        initComponents();
    }


    public UserAccount(int userId) {
        this.userId = userId;
        initComponents();
        loadUserData(this.userId);
    }

    private void initComponents() {

        idtextfield = new JTextField();
        usernametextfield = new JTextField();
        passwordtextfield = new JPasswordField();
        emialtextfield = new JTextField();
        phonenotextfield = new JTextField();
        genderComboBox = new JComboBox();
        answertextfield = new JTextField();
        citytextfield = new JTextField();
        addresstextfield = new JTextField();


        setLayout(null);
        setTitle("USER ACCOUNT");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/lightgrey.png"));
        Image i2 = i1.getImage().getScaledInstance(850, 450, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,850,450);
        add(image);


        JLabel id = new JLabel("ID");
        id.setFont(new Font("Montserrat",Font.BOLD,16));
        id.setBounds(50,20,100,30);
        image.add(id);
        idtextfield = new JTextField();
        idtextfield.setEditable(false);
        idtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        idtextfield.setBounds(50,45,350,30);
        image.add(idtextfield);


        JLabel username = new JLabel("Username");
        username.setFont(new Font("Montserrat",Font.BOLD,16));
        username.setBounds(50,80,200,30);
        image.add(username);
        usernametextfield = new JTextField();
        usernametextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        usernametextfield.setBounds(50,105,350,30);
        image.add(usernametextfield);


        JLabel password = new JLabel("Password");
        password.setFont(new Font("Montserrat",Font.BOLD,16));
        password.setBounds(50,140,200,30);
        image.add(password);
        passwordtextfield = new JPasswordField();
        passwordtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        passwordtextfield.setBounds(50,165,350,30);
        image.add(passwordtextfield);

        showPasswordButton = new JButton();
        showPasswordButton.setBounds(400, 165, 30, 30); // Adjust the position as needed
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

        JLabel email = new JLabel("Email");
        email.setFont(new Font("Montserrat",Font.BOLD,16));
        email.setBounds(50,200,200,30);
        image.add(email);
        emialtextfield = new JTextField();
        emialtextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        emialtextfield.setBounds(50,225,350,30);
        image.add(emialtextfield);


        JLabel gender = new JLabel("Gender");
        gender.setFont(new Font("Montserrat",Font.BOLD,16));
        gender.setBounds(450,80,200,30);
        image.add(gender);

        String[] genders = {"Male", "Female"};
        genderComboBox = new JComboBox(genders);
        genderComboBox.setFont(new Font("Montserrat", Font.BOLD, 14));
        genderComboBox.setBounds(450,105,350,30);
        genderComboBox.setBackground(Color.white);
        image.add(genderComboBox);


        JLabel phone = new JLabel("Phone No");
        phone.setFont(new Font("Montserrat",Font.BOLD,16));
        phone.setBounds(450,20,200,30);
        image.add(phone);
        phonenotextfield = new JTextField();
        phonenotextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        phonenotextfield.setBounds(450,45,350,30);
        image.add(phonenotextfield);


        JLabel question = new JLabel("Security Question");
        question.setFont(new Font("Montserrat",Font.BOLD,16));
        question.setBounds(250,260,200,30);
        image.add(question);
        answertextfield = new JTextField();
        answertextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        answertextfield.setBounds(250,285,350,30);
        image.add(answertextfield);


        JLabel city = new JLabel("City");
        city.setFont(new Font("Montserrat",Font.BOLD,16));
        city.setBounds(450,140,200,30);
        image.add(city);
        citytextfield = new JTextField();
        citytextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        citytextfield.setBounds(450,165,350,30);
        image.add(citytextfield);


        JLabel address = new JLabel("Address");
        address.setFont(new Font("Montserrat",Font.BOLD,16));
        address.setBounds(450,200,200,30);
        image.add(address);
        addresstextfield = new JTextField();
        addresstextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        addresstextfield.setBounds(450,225,350,30);
        image.add(addresstextfield);

        Color color = Color.decode("#495057");

        back = new JButton("Back");
        back.setBackground(color);
        back.setForeground(Color.white);
        back.setFont(new Font("Montserrat",Font.BOLD,20));
        back.setBounds(150,350,150,40);
        back.addActionListener(this);
        image.add(back);
        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                back.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        delete = new JButton("Delete");
        delete.setBackground(color);
        delete.setForeground(Color.white);
        delete.setFont(new Font("Montserrat",Font.BOLD,20));
        delete.setBounds(350,350,150,40);
        delete.addActionListener(this);
        image.add(delete);
        delete.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                delete.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        update = new JButton("Update");
        update.setBackground(color);
        update.setForeground(Color.white);
        update.setFont(new Font("Montserrat",Font.BOLD,20));
        update.setBounds(550,350,150,40);
        update.addActionListener(this);
        image.add(update);
        update.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                update.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });


        getContentPane().setBackground(Color.white);


        setResizable(false);
        setSize(850,450);
        setLocation(250,100);
        setVisible(true);
    }


    public void loadUserData(int userId) {
        try {
            Connection conn = MyConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM [user] WHERE uid = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idtextfield.setText(String.valueOf(rs.getInt("uid")));
                usernametextfield.setText(rs.getString("uname"));
                passwordtextfield.setText(rs.getString("upassword"));
                emialtextfield.setText(rs.getString("uemail"));
                phonenotextfield.setText(rs.getString("uphone"));
                genderComboBox.setSelectedItem(rs.getString("ugender"));
                answertextfield.setText(rs.getString("uans"));
                citytextfield.setText(rs.getString("ucity"));
                addresstextfield.setText(rs.getString("uaddress"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserData(int userId) {
    try {
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("UPDATE [user] SET uname = ?, upassword = ?, uemail = ?, uphone = ?, ugender = ?, uans = ?, ucity = ?, uaddress = ? WHERE uid = ?");
        ps.setString(1, usernametextfield.getText());
        ps.setString(2, new String(passwordtextfield.getPassword()));
        ps.setString(3, emialtextfield.getText());
        ps.setString(4, phonenotextfield.getText());
        ps.setString(5, (String) genderComboBox.getSelectedItem());
        ps.setString(6, answertextfield.getText());
        ps.setString(7, citytextfield.getText());
        ps.setString(8, addresstextfield.getText());
        ps.setInt(9, userId);
        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Updated successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void deleteUserData(int userId) {
    try {
        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM [user] WHERE uid = ?");
        ps.setInt(1, userId);
        int rowsDeleted = ps.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Deleted successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void actionPerformed(ActionEvent ae){
    if(ae.getSource() == update){
        String id = idtextfield.getText();
        String username = usernametextfield.getText();
        String password = new String(passwordtextfield.getPassword());
        String email = emialtextfield.getText();
        String phone = phonenotextfield.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String answer = answertextfield.getText();
        String city = citytextfield.getText();
        String address = addresstextfield.getText();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || answer.isEmpty() || city.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
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

        // If all checks pass, call the updateUserData method
        updateUserData(userId);
    } else if (ae.getSource() == delete){
        String id = idtextfield.getText();

        // Check if id field is empty
        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null, "Please enter the ID");
            return;
        }

        // If check passes, call the deleteUserData method
        deleteUserData(userId);
        setVisible(false);
        new Login().setVisible(true);
    } else if(ae.getSource() == back){
        this.setVisible(false);
        new UserDashboard(userId).setVisible(true);
    }
}
    public static void main(String[] args) {
        new UserAccount(userId).setVisible(true);

    }
}