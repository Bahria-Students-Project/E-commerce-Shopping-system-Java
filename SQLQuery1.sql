USE Shopping;

CREATE TABLE admin (
id INT IDENTITY(1,1) PRIMARY KEY,
email NVARCHAR(30),
password NVARCHAR(20)
);

CREATE TABLE [user] (
uid INT IDENTITY(1,1) PRIMARY KEY,
uname NVARCHAR(20),
upassword NVARCHAR(20),
uemail NVARCHAR(30),
uphone NVARCHAR(11),
ugender NVARCHAR(MAX),
uans NVARCHAR(MAX),
ucity NVARCHAR(MAX),
uaddress NVARCHAR(MAX)
);

CREATE TABLE category (
cid INT IDENTITY(1,1) PRIMARY KEY,
cname NVARCHAR(20) UNIQUE,
cdesc NVARCHAR(MAX)
);

CREATE TABLE product (
pid INT IDENTITY(1,1) PRIMARY KEY,
pname NVARCHAR(20),
cname NVARCHAR(20),
pqty INT,
pprice FLOAT,
pimage NVARCHAR(300),
FOREIGN KEY (cname) REFERENCES category(cname) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE purchase (
id INT IDENTITY(1,1) PRIMARY KEY,
uid INT,
uname NVARCHAR(20),
pid INT,
product_name NVARCHAR(20),
qty INT,
price FLOAT,
total FLOAT,
pimage 
SELECT * FROM admin;
INSERT INTO admin (email, password)
VALUES ('admin@gmail.com', 'admin123');
NVARCHAR(MAX),
FOREIGN KEY (uid) REFERENCES [user] ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE payment (
    payment_id INT IDENTITY(1,1) PRIMARY KEY,
    uid INT,
    purchase_id INT,
    card_holder_name NVARCHAR(50),
    card_number NVARCHAR(20),
    cvc NVARCHAR(3),
    expiry_date NVARCHAR(5),
    FOREIGN KEY (uid) REFERENCES [user](uid) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (purchase_id) REFERENCES purchase(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE notifications (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userId INT,
    content NVARCHAR(MAX),
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    seen BIT DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES [user](uid) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE user_notifications (
    user_id INT,
    notification_id INT,
    seen DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, notification_id),
    FOREIGN KEY (user_id) REFERENCES [user](uid) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (notification_id) REFERENCES notifications(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
SELECT *FROM [user];

SELECT *FROM category;

SELECT *FROM product;

SELECT *FROM purchase;

SELECT *FROM payment;

SELECT *FROM notifications;

SELECT *FROM user_notifications;

