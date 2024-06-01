package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;

public class UserDashboard extends JFrame {

    JLabel purchase, purchasedetails, myaccount, logout;
    JTextField t2, t3;
    static int userId;
    DefaultCategoryDataset dataset;
    ChartPanel chartPanel;

    UserDashboard(int userId) {
        this.userId = userId;
        setLayout(null);
        setTitle("User Dashboard");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/userdash.png"));
        Image i2 = i1.getImage().getScaledInstance(1000, 650, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1000, 650);
        add(image);

        ImageIcon cartIcon = new ImageIcon(ClassLoader.getSystemResource("icons/cart.png"));
        Image cartImg = cartIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        cartIcon = new ImageIcon(cartImg);
        JLabel cartLabel = new JLabel(cartIcon);
        cartLabel.setBounds(300, 35, 60, 60);
        image.setLayout(null);
        image.add(cartLabel);

        JLabel system = new JLabel("Digital Trolley");
        system.setFont(new Font("Montserrat", Font.BOLD, 44));
        system.setForeground(Color.WHITE);
        system.setBounds(370, 40, 400, 50);
        image.add(system);

        ImageIcon dashboardIcon = new ImageIcon(ClassLoader.getSystemResource("icons/dashboard.png"));
        Image dashboardImg = dashboardIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        dashboardIcon = new ImageIcon(dashboardImg);
        JLabel dashboardLabel = new JLabel(dashboardIcon);
        dashboardLabel.setBounds(20, 155, 40, 40);
        image.setLayout(null);
        image.add(dashboardLabel);

        JLabel dashboard = new JLabel("DASHBOARD");
        dashboard.setFont(new Font("Montserrat", Font.BOLD, 26));
        dashboard.setForeground(Color.BLACK);
        dashboard.setBounds(70, 160, 200, 30);
        image.add(dashboard);


        ImageIcon shoppingIcon = new ImageIcon(ClassLoader.getSystemResource("icons/shopping.png"));
        Image shoppingImg = shoppingIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        shoppingIcon = new ImageIcon(shoppingImg);
        JLabel shoppingLabel = new JLabel(shoppingIcon);
        shoppingLabel.setBounds(20, 250, 30, 30);
        image.setLayout(null);
        image.add(shoppingLabel);


        purchase = new JLabel("Purchase Items");
        purchase.setBounds(70, 240, 235, 50);
        purchase.setFont(new Font("Montserrat", Font.BOLD, 20));
        purchase.setForeground(Color.white);
        purchase.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(purchase);
        purchase.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                purchase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                purchase.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                purchase.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                purchase.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new user.Purchase(userId).setVisible(true);
            }
        });

        ImageIcon notesIcon = new ImageIcon(ClassLoader.getSystemResource("icons/notes.png"));
        Image notesImg = notesIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        notesIcon = new ImageIcon(notesImg);
        JLabel notesLabel = new JLabel(notesIcon);
        notesLabel.setBounds(20, 310, 30, 30);
        image.setLayout(null);
        image.add(notesLabel);

        purchasedetails = new JLabel("Purchase Details");
        purchasedetails.setBounds(70, 300, 235, 50);
        purchasedetails.setFont(new Font("Montserrat", Font.BOLD, 20));
        purchasedetails.setForeground(Color.white);
        purchasedetails.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(purchasedetails);
        purchasedetails.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                purchase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                purchasedetails.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                purchasedetails.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                purchasedetails.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new user.PurchaseDetail(userId).setVisible(true);
            }
        });

        ImageIcon accountIcon = new ImageIcon(ClassLoader.getSystemResource("icons/accout.png"));
        Image accountImg = accountIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        accountIcon = new ImageIcon(accountImg);
        JLabel accountLabel = new JLabel(accountIcon);
        accountLabel.setBounds(20, 370, 30, 30);
        image.setLayout(null);
        image.add(accountLabel);

        myaccount = new JLabel("My Account");
        myaccount.setBounds(70, 360, 235, 50);
        myaccount.setFont(new Font("Montserrat", Font.BOLD, 20));
        myaccount.setForeground(Color.white);
        myaccount.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(myaccount);
        myaccount.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                myaccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                myaccount.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent e) {
                myaccount.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                myaccount.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new UserAccount(userId).setVisible(true);
            }
        });

        ImageIcon powerIcon = new ImageIcon(ClassLoader.getSystemResource("icons/power.png"));
        Image powerImg = powerIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        powerIcon = new ImageIcon(powerImg);
        JLabel powerLabel = new JLabel(powerIcon);
        powerLabel.setBounds(840, 100, 30, 30);
        image.setLayout(null);
        image.add(powerLabel);

        logout = new JLabel("Logout");
        logout.setBounds(870, 90, 235, 50);
        logout.setFont(new Font("Montserrat", Font.BOLD, 20));
        logout.setForeground(Color.white);
        logout.setHorizontalAlignment(SwingConstants.LEFT);
        image.add(logout);
        logout.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                logout.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent e) {
                logout.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                logout.setForeground(Color.white);
            }

            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new user.Login().setVisible(true);
            }
        });

        JLabel heading = new JLabel("Statistics");
        heading.setFont(new Font("Montserrat", Font.BOLD, 26));
        heading.setForeground(Color.BLACK);
        heading.setBounds(550, 160, 200, 30);
        image.add(heading);

        JLabel l2 = new JLabel("Purchased Products: ");
        l2.setFont(new Font("Montserrat", Font.BOLD, 20));
        l2.setForeground(Color.BLACK);
        l2.setBounds(340, 220, 250, 30);
        image.add(l2);
        t2 = new JTextField("0");
        t2.setEditable(false);
        t2.setFont(new Font("Montserrat", Font.BOLD, 20));
        t2.setBounds(550, 220, 50, 30);
        image.add(t2);

        JLabel l3 = new JLabel("Total Amount: ");
        l3.setFont(new Font("Montserrat", Font.BOLD, 20));
        l3.setForeground(Color.BLACK);
        l3.setBounds(650, 220, 200, 30);
        image.add(l3);
        t3 = new JTextField("0");
        t3.setEditable(false);
        t3.setFont(new Font("Montserrat", Font.BOLD, 20));
        t3.setBounds(800, 220, 100, 30);
        image.add(t3);

        dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart3D(
                "",
                "Category",
                "Score",
                dataset);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, new Color(173, 181, 189));
        renderer.setSeriesPaint(1, new Color(108, 117, 125));

        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(350, 300, 500, 300);
        image.add(chartPanel);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDataset();
            }
        });
        timer.start();

        getContentPane().setBackground(Color.WHITE);

        setResizable(false);
        setSize(1000, 650);
        setLocation(100, 10);
        setVisible(true);
    }

    private void updateDataset() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Connection con = DriverManager.getConnection("jdbc:sqlserver://SAADI\\SQLEXPRESS01;databaseName=Shopping;user=sa;password=data123;encrypt=true;trustServerCertificate=true");

                    String sql = "SELECT COUNT(*) FROM purchase WHERE uid = ?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setInt(1, userId);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        t2.setText(Integer.toString(rs.getInt(1)));
                    }

                    sql = "SELECT SUM(total) FROM purchase WHERE uid = ?";
                    stmt = con.prepareStatement(sql);
                    stmt.setInt(1, userId);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        t3.setText(Float.toString(rs.getFloat(1)));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                dataset.clear();

                int count = Integer.parseInt(t2.getText());
                System.out.println("Adding count to dataset: " + count); // Debug output
                dataset.addValue(count, "Products", "Purchased");

                dataset.addValue(Integer.parseInt(t2.getText()), "Products", "Purchased");
                dataset.addValue(Float.parseFloat(t3.getText()), "Amount", "Total");

                chartPanel.repaint();
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        new UserDashboard(userId).setVisible(true);
    }
}