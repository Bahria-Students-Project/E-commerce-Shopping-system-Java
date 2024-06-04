package user;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Payment extends JFrame implements ActionListener {

    JTextField cardholdertextfield, cardnotextfield,cvctextfield,expdatetextfield;
    JButton back, pay;
    static int userId;
    private boolean isPaid;
    private Purchase purchase;

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    private void insertPaymentDetails() throws SQLException {
        // Establish a connection
        Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true", "sa", "data123");

        // Get the latest purchase id
        int purchaseId = getLatestPurchaseId(userId);

        // Create a SQL query
        String sql = "INSERT INTO payment (uid, purchase_id, card_holder_name, card_number, cvc, expiry_date) VALUES (?, ?, ?, ?, ?, ?)";

        // Create a statement
        PreparedStatement ps = con.prepareStatement(sql);

        // Set parameters
        ps.setInt(1, userId);
        ps.setInt(2, purchaseId);
        ps.setString(3, cardholdertextfield.getText());
        ps.setString(4, cardnotextfield.getText());
        ps.setString(5, cvctextfield.getText());
        ps.setString(6, expdatetextfield.getText());

        // Execute the query
        ps.executeUpdate();

        // Close the connection
        con.close();
    }

    // Method to get the latest purchase id for a user
    private int getLatestPurchaseId(int userId) throws SQLException {
        // Establish a connection
        Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true", "sa", "data123");

        // Create a SQL query
        String sql = "SELECT TOP 1 id FROM purchase WHERE uid = ? ORDER BY id DESC";

        // Create a statementr
        PreparedStatement ps = con.prepareStatement(sql);

        // Set parameters
        ps.setInt(1, userId);

        // Execute the query
        ResultSet rs = ps.executeQuery();

        // Get the latest purchase id
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("No purchases found for user id: " + userId);
        }
    }

    Payment(int userId, boolean isPaid, Purchase purchase){
        this.userId = userId;
        this.isPaid = isPaid;
        this.purchase = purchase;

        setLayout(null);
        setTitle("FORGOT PASSWORD");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/lightgrey.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 500, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,300,500);
        add(image);


        JLabel cashPayment = new JLabel("Cash Payment");
        cashPayment.setFont(new Font("Montserrat",Font.BOLD,30));
        cashPayment.setBounds(50,25,250,40);
        cashPayment.setForeground(Color.BLACK);
        image.add(cashPayment);

        JLabel cardholder = new JLabel("Card Holder Name");
        cardholder.setFont(new Font("Montserrat",Font.BOLD,14));
        cardholder.setBounds(50,80,200,30);
        image.add(cardholder);
        cardholdertextfield = new JTextField();
        cardholdertextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        cardholdertextfield.setBounds(50,105,200,25);
        image.add(cardholdertextfield);

        JLabel cardno = new JLabel("Card Number");
        cardno.setFont(new Font("Montserrat",Font.BOLD,14));
        cardno.setBounds(50,130,200,30);
        image.add(cardno);
        cardnotextfield = new JTextField();
        cardnotextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        cardnotextfield.setBounds(50,155,200,25);
        image.add(cardnotextfield);


        JLabel cvc = new JLabel("CVC");
        cvc.setFont(new Font("Montserrat",Font.BOLD,14));
        cvc.setBounds(50,180,200,30);
        image.add(cvc);
        cvctextfield = new JTextField();
        cvctextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        cvctextfield.setBounds(50,205,200,25);
        image.add(cvctextfield);

        JLabel expdate = new JLabel("Expiry Date");
        expdate.setFont(new Font("Montserrat",Font.BOLD,14));
        expdate.setBounds(50,230,200,30);
        image.add(expdate);
        expdatetextfield = new JTextField();
        expdatetextfield.setFont(new Font("Montserrat", Font.BOLD, 14));
        expdatetextfield.setBounds(50,255,200,25);
        image.add(expdatetextfield);


        back = new JButton("Back");
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.setFont(new Font("Montserrat",Font.BOLD,14));
        back.setBounds(50,310,90,30);
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

        pay = new JButton("Pay");
        pay.setBackground(Color.black);
        pay.setForeground(Color.white);
        pay.setFont(new Font("Montserrat",Font.BOLD,14));
        pay.setBounds(160,310,90,30);
        pay.addActionListener(this);
        image.add(pay);
        pay.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                pay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                pay.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });


        getContentPane().setBackground(Color.WHITE);

        setResizable(false);
        setSize(300,400);
        setLocation(500,100);
        setVisible(true);
    }




@Override
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == pay) {
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                String holderName = cardholdertextfield.getText();
                String cardNo = cardnotextfield.getText();
                String cvc = cvctextfield.getText();
                String expDate = expdatetextfield.getText();

                // Validate the input fields
                if (holderName.isEmpty() || cardNo.isEmpty() || cvc.isEmpty() || expDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                }

                if (cardNo.length() != 16 || cvc.length() != 3 || !expDate.matches("\\d{2}/\\d{2}")) {
                    JOptionPane.showMessageDialog(null, "Invalid card details.");

                }

                // If all checks pass, proceed with the payment
                try {
                    // Simulate payment processing delay
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                return true;
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(null, "Payment successful!");
                        insertPaymentDetails(); // Insert the payment details into the database
                        purchase.setPaid(true);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Payment failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Payment failed!");
                }
            }
        };
        worker.execute();
    } else if (ae.getSource() == back) {
        setVisible(false);
    }
}
    public static void main(String[] args) {
    // Create a Purchase instance
    Purchase purchase = new Purchase(userId);
    // Create a Payment instance
    new Payment(userId, purchase.isPaid, purchase).setVisible(true);
}
}