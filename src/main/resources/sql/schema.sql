-- Drop and Create the Database
DROP DATABASE IF EXISTS SwiftSTS;
CREATE DATABASE SwiftSTS;
USE SwiftSTS;

-- Create Tables
CREATE TABLE User
(
    UserId   VARCHAR(10)  NOT NULL PRIMARY KEY,
    UserName VARCHAR(50)  NOT NULL,
    Password VARCHAR(50)  NOT NULL,
    RoleType VARCHAR(20)  NOT NULL,
    Email    VARCHAR(100) NOT NULL,
    Name     VARCHAR(30)  NOT NULL
);

CREATE TABLE Expense
(
    ExpenseId        VARCHAR(10)    NOT NULL PRIMARY KEY,
    ExpensesCategory VARCHAR(50)    NOT NULL,
    Date             DATE           NOT NULL,
    Amount           DECIMAL(10, 2) NOT NULL,
    UserId           VARCHAR(10)    NOT NULL,
    FOREIGN KEY (UserId) REFERENCES User (UserId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Student
(
    StudentId    VARCHAR(10)  NOT NULL PRIMARY KEY,
    StudentName  VARCHAR(50)  NOT NULL,
    ParentName   VARCHAR(50)  NOT NULL,
    PickupLocation VARCHAR(100) NOT NULL,
    Email        VARCHAR(100) NOT NULL,
    StudentGrade VARCHAR(10)  NOT NULL,
    ContactNo    VARCHAR(15)  NOT NULL,
    UserId       VARCHAR(10)  NOT NULL,
    FOREIGN KEY (UserId) REFERENCES User (UserId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Driver
(
    DriverId  VARCHAR(10)  NOT NULL PRIMARY KEY,
    Name      VARCHAR(50)  NOT NULL,
    LicenseNo VARCHAR(50)  NOT NULL,
    NIC       VARCHAR(20)  NOT NULL,
    ContactNo VARCHAR(15)  NOT NULL,
    Address   VARCHAR(50)  NOT NULL,
    Email     VARCHAR(50)  NOT NULL
);

CREATE TABLE Attendance
(
    AttendanceId VARCHAR(10) NOT NULL PRIMARY KEY,
    Month        VARCHAR(15) NOT NULL,
    DayCount     INT         NOT NULL,
    Year         INT         NOT NULL,
    StudentId    VARCHAR(10) NOT NULL,
    DriverId     VARCHAR(10) NOT NULL,
    FOREIGN KEY (StudentId) REFERENCES Student (StudentId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (DriverId) REFERENCES Driver (DriverId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Route
(
    RouteId     VARCHAR(10)    NOT NULL PRIMARY KEY,
    RouteName   varchar(50)    not null,
    StartPoint  VARCHAR(100)   NOT NULL,
    Destination VARCHAR(100)   NOT NULL,
    RouteFee    DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Vehicle
(
    VehicleId          VARCHAR(10)   NOT NULL PRIMARY KEY,
    RegistrationNo     VARCHAR(50)   NOT NULL,
    VehicleType        VARCHAR(20)   NOT NULL,
    EngineCapacity     DECIMAL(4, 1) NOT NULL,
    FuelType           VARCHAR(20)   NOT NULL,
    Model              VARCHAR(20)   NULL,
    SeatCount          INT           NOT NULL,
    AvailableSeatCount INT           NOT NULL
);

CREATE TABLE Payment
(
    PaymentId     VARCHAR(10)    NOT NULL PRIMARY KEY,
    Date          DATE           NOT NULL,
    Amount        DECIMAL(10, 2) NOT NULL,
    CreditBalance DECIMAL(10, 2) NULL,
    MonthlyFee    DECIMAL(10, 2) NULL,
    Balance       DECIMAL(10, 2) NULL,
    Status        VARCHAR(20)    NOT NULL,
    StudentId     VARCHAR(10)    NOT NULL,
    FOREIGN KEY (StudentId) REFERENCES Student (StudentId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE StudentRegistration
(
    StudentRegistrationId VARCHAR(10)    NOT NULL PRIMARY KEY,
    StudentId             VARCHAR(10)    NOT NULL,
    Distance              DECIMAL(10, 2) NOT NULL,
    DayPrice              DECIMAL(10, 2) NOT NULL,
    Date                  DATE           NOT NULL,
    RouteId               VARCHAR(10)    NOT NULL,
    VehicleId             VARCHAR(10)    NOT NULL,
    FOREIGN KEY (StudentId) REFERENCES Student (StudentId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (RouteId) REFERENCES Route (RouteId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (VehicleId) REFERENCES Vehicle (VehicleId)
        ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE DriverSchedule
(
    DriverScheduleId VARCHAR(10) NOT NULL PRIMARY KEY,
    DriverId         VARCHAR(10) NOT NULL,
    RouteId          VARCHAR(10) NOT NULL,
    Date             DATE        NOT NULL,
    FOREIGN KEY (DriverId) REFERENCES Driver (DriverId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (RouteId) REFERENCES Route (RouteId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE VehicleSchedule
(
    VehicleScheduleId VARCHAR(10) NOT NULL PRIMARY KEY,
    VehicleId         VARCHAR(10) NOT NULL,
    RouteId           VARCHAR(10) NOT NULL,
    Date              DATE        NOT NULL,
    ArrivalTime       TIME        NOT NULL,
    DepartureTime     TIME        NOT NULL,
    FOREIGN KEY (VehicleId) REFERENCES Vehicle (VehicleId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (RouteId) REFERENCES Route (RouteId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

Use SwiftSTS;

-- Insert Data
-- User Table
INSERT INTO User (UserId, UserName, Password, RoleType, Email, Name)
VALUES
    ('U001', 'admin', '123', 'Admin', 'admin@galle.sch.lk', 'Galle Admin'),
    ('U002', 'driver1', 'driver123', 'Driver', 'driver1@galle.sch.lk', 'Kamal Perera'),
    ('U003', 'driver2', 'driver123', 'Driver', 'driver2@galle.sch.lk', 'Sanduni Silva'),
    ('U004', 'driver3', 'driver456', 'Driver', 'driver3@galle.sch.lk', 'Ruwan Fernando'),
    ('U005', 'driver4', 'driver456', 'Driver', 'driver4@galle.sch.lk', 'Nuwan Jayasekara');

-- Student Table
INSERT INTO Student (StudentId, StudentName, ParentName, PickupLocation, Email, StudentGrade, ContactNo, UserId)
VALUES
    ('S001', 'Amal Perera', 'Sanduni Silva', 'Karapitiya', 'amal@galle.sch.lk', 'Grade 5', '0711234567', 'U003'),
    ('S002', 'Kavindi Silva', 'Sanduni Silva', 'Pinnaduwa', 'kavindi@galle.sch.lk', 'Grade 8', '0719876543', 'U003'),
    ('S003', 'Nimesh Perera', 'Nuwan Jayasekara', 'Hikkaduwa', 'nimesh@galle.sch.lk', 'Grade 10', '0711122334', 'U005'),
    ('S004', 'Tharindu Fernando', 'Nuwan Jayasekara', 'Gintota', 'tharindu@galle.sch.lk', 'Grade 7', '0775566778', 'U005'),
    ('S005', 'Sasini Kumari', 'Nuwan Jayasekara', 'Unawatuna', 'sasini@galle.sch.lk', 'Grade 3', '0756677889', 'U005');

-- Driver Table
INSERT INTO Driver (DriverId, Name, LicenseNo, NIC, ContactNo, Address, Email)
VALUES
    ('D001', 'Kamal Perera', 'B12345678', '882345678V', '0712456789', 'Wackwella Road, Galle', 'driver1@galle.sch.lk'),
    ('D002', 'Ruwan Fernando', 'C87654321', '912345678X', '0719876543', 'Dadalla, Galle', 'driver2@galle.sch.lk'),
    ('D003', 'Sunil Liyanage', 'B98765432', '852145698V', '0723456789', 'Karapitiya, Galle', 'driver3@galle.sch.lk'),
    ('D004', 'Sarath Kumara', 'C65498712', '902134567V', '0703456788', 'Pinnaduwa, Galle', 'driver4@galle.sch.lk'),
    ('D005', 'Chamara Silva', 'B45213456', '872145698V', '0712459876', 'Unawatuna, Galle', 'driver5@galle.sch.lk');

-- Route Table
INSERT INTO Route (RouteId,RouteName, StartPoint, Destination, RouteFee)
VALUES
    ('R001', 'Dodangoda Road','Karapitiya', 'Richmond College', 2500.00),
    ('R002','Dodangoda Road','Pinnaduwa', 'Southlands College', 3000.00),
    ('R003','Dodangoda Road', 'Hikkaduwa', 'Mahinda College', 3500.00),
    ('R004', 'Dodangoda Road','Gintota', 'St. Aloysius College', 2000.00),
    ('R005','Dodangoda Road','Unawatuna', 'Sacred Heart Convent', 2200.00);

-- Attendance Table
INSERT INTO Attendance (AttendanceId, Month, DayCount, Year, StudentId, DriverId)
VALUES
    ('A001', 'January', 22, 2024, 'S001', 'D001'),
    ('A006', 'January', 20, 2024, 'S002', 'D002'),
    ('A003', 'January', 18, 2024, 'S003', 'D003'),
    ('A004', 'January', 23, 2024, 'S004', 'D004'),
    ('A005', 'January', 21, 2024, 'S005', 'D005');

-- Expense Table
INSERT INTO Expense (ExpenseId, ExpensesCategory, Date, Amount, UserId)
VALUES
    ('E001', 'Diesel', '2024-01-05', 15000.00, 'U001'),
    ('E002', 'Vehicle Maintenance', '2024-01-12', 12000.00, 'U001'),
    ('E003', 'Driver Salary', '2024-01-20', 50000.00, 'U001'),
    ('E004', 'Insurance', '2024-01-25', 8000.00, 'U001'),
    ('E005', 'Miscellaneous', '2024-01-28', 5000.00, 'U001');

-- Payment Table
INSERT INTO Payment (PaymentId, Date, Amount, CreditBalance, MonthlyFee, Balance, Status, StudentId)
VALUES
    ('P001', '2024-01-30', 2500.00, 0.00, 2500.00, 0.00, 'Paid', 'S001'),
    ('P002', '2024-01-30', 3000.00, 0.00, 3000.00, 0.00, 'Paid', 'S002'),
    ('P003', '2024-01-30', 3500.00, 0.00, 3500.00, 0.00, 'Paid', 'S003'),
    ('P004', '2024-01-30', 2000.00, 0.00, 2000.00, 0.00, 'Paid', 'S004'),
    ('P005', '2024-01-30', 2200.00, 0.00, 2200.00, 0.00, 'Paid', 'S005');

-- Vehicle Table
INSERT INTO Vehicle (VehicleId, RegistrationNo, VehicleType, EngineCapacity, FuelType, Model, SeatCount, AvailableSeatCount)
VALUES
    ('V001', 'SP-1234', 'Van', 2.0, 'Diesel', 'Toyota HiAce', 15, 10),
    ('V002', 'SP-5678', 'Bus', 4.0, 'Diesel', 'Nissan Civilian', 30, 25),
    ('V003', 'SP-9101', 'Van', 2.5, 'Petrol', 'Hyundai H1', 12, 8),
    ('V004', 'SP-1121', 'Car', 1.8, 'Petrol', 'Suzuki Alto', 4, 3),
    ('V005', 'SP-3141', 'Van', 2.2, 'Diesel', 'Kia Carnival', 7, 5);


-- StudentRegistrationDetails Table
INSERT INTO StudentRegistration (StudentRegistrationId, StudentId, Distance, DayPrice, Date, RouteId, VehicleId)
VALUES
    ('SR001', 'S001', 5.5, 100.00, '2024-01-01', 'R001', 'V001'),
    ('SR002', 'S002', 7.0, 120.00, '2024-01-02', 'R002', 'V002'),
    ('SR003', 'S003', 10.0, 150.00, '2024-01-03', 'R003', 'V003'),
    ('SR004', 'S004', 8.0, 110.00, '2024-01-04', 'R004', 'V004'),
    ('SR005', 'S005', 6.0, 90.00, '2024-01-05', 'R005', 'V005');


-- DriverSchedule Table
INSERT INTO DriverSchedule (DriverScheduleId, DriverId, RouteId, Date)
VALUES
    ('DS001', 'D001', 'R001', '2024-01-02'),
    ('DS002', 'D002', 'R002', '2024-01-02'),
    ('DS003', 'D003', 'R003', '2024-01-02'),
    ('DS004', 'D004', 'R004', '2024-01-02'),
    ('DS005', 'D005', 'R005', '2024-01-02');


INSERT INTO VehicleSchedule (VehicleScheduleId, VehicleId, RouteId, Date, ArrivalTime, DepartureTime)
VALUES
    ('VS001', 'V001', 'R001', '2024-01-01', '07:30:00', '08:00:00'),
    ('VS002', 'V002', 'R002', '2024-01-02', '07:40:00', '08:10:00'),
    ('VS003', 'V003', 'R003', '2024-01-03', '07:50:00', '08:20:00'),
    ('VS004', 'V004', 'R004', '2024-01-04', '08:00:00', '08:30:00'),
    ('VS005', 'V005', 'R005', '2024-01-05', '08:10:00', '08:40:00');

