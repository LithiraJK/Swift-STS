-- Create Database
DROP DATABASE IF EXISTS SwiftSTS;
CREATE DATABASE SwiftSTS;
USE SwiftSTS;

-- Table: User
CREATE TABLE User (
                      UserId VARCHAR(10) PRIMARY KEY,
                      UserName VARCHAR(50) NOT NULL,
                      Password VARCHAR(50) NOT NULL,
                      RoleType VARCHAR(20) NOT NULL,
                      Email VARCHAR(100) NOT NULL
);

-- Table: Attendance
CREATE TABLE Attendance (
                            AttendanceId VARCHAR(10) PRIMARY KEY,
                            Month VARCHAR(15) NOT NULL,
                            DayCount INT NOT NULL
);

-- Table: Student
CREATE TABLE Student (
                         StudentId VARCHAR(10) PRIMARY KEY,
                         StudentName VARCHAR(50) NOT NULL,
                         ParentName VARCHAR(50) NOT NULL,
                         Address VARCHAR(100) NOT NULL,
                         Email VARCHAR(100) NOT NULL,
                         StudentGrade VARCHAR(10) NOT NULL,
                         ContactNo VARCHAR(15) NOT NULL
);

-- Table: Driver
CREATE TABLE Driver (
                        DriverId VARCHAR(10) PRIMARY KEY,
                        Name VARCHAR(50) NOT NULL,
                        LicenseNo VARCHAR(50) NOT NULL,
                        ContactNo VARCHAR(15) NOT NULL
);

-- Table: Expenses
CREATE TABLE Expenses (
                          ExpenseId VARCHAR(10) PRIMARY KEY,
                          ExpensesCategory VARCHAR(50) NOT NULL,
                          Date DATE NOT NULL,
                          Amount DECIMAL(10, 2) NOT NULL
);

-- Table: Payment
CREATE TABLE Payment (
                         PaymentId VARCHAR(10) PRIMARY KEY,
                         PaymentMethod VARCHAR(20) NOT NULL,
                         Date DATE NOT NULL,
                         Status VARCHAR(20) NOT NULL,
                         Amount DECIMAL(10, 2) NOT NULL
);

-- Table: Vehicle
CREATE TABLE Vehicle (
                         VehicleId VARCHAR(10) PRIMARY KEY,
                         RegistrationNo VARCHAR(50) NOT NULL,
                         VehicleType VARCHAR(50) NOT NULL,
                         EngineCapacity DECIMAL(4, 1) NOT NULL
);

-- Table: Route
CREATE TABLE Route (
                       RouteId VARCHAR(10) PRIMARY KEY,
                       StartPoint VARCHAR(100) NOT NULL,
                       Destination VARCHAR(100) NOT NULL,
                       Distance DECIMAL(5, 2) NOT NULL,
                       PaymentId VARCHAR(10) NOT NULL,
                       CONSTRAINT FOREIGN KEY(PaymentId) REFERENCES Payment(PaymentId)
                           ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: ServicePlans
CREATE TABLE ServicePlans (
                              ServicePlanId VARCHAR(10) PRIMARY KEY,
                              DayPrice DECIMAL(10, 2) NOT NULL,
                              RouteId VARCHAR(10) NOT NULL,
                              StudentId VARCHAR(10) NOT NULL,
                              CONSTRAINT FOREIGN KEY(RouteId) REFERENCES Route(RouteId)
                                  ON UPDATE CASCADE ON DELETE CASCADE,
                              CONSTRAINT FOREIGN KEY(StudentId) REFERENCES Student(StudentId)
                                  ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: VehicleSchedule
CREATE TABLE VehicleSchedule (
                                 VehicleScheduleId VARCHAR(10) PRIMARY KEY,
                                 VehicleId VARCHAR(10) NOT NULL,
                                 RouteId VARCHAR(10) NOT NULL,
                                 Date DATE NOT NULL,
                                 ArrivalTime TIME NOT NULL,
                                 DepartureTime TIME NOT NULL,
                                 CONSTRAINT FOREIGN KEY(VehicleId) REFERENCES Vehicle(VehicleId)
                                     ON UPDATE CASCADE ON DELETE CASCADE,
                                 CONSTRAINT FOREIGN KEY(RouteId) REFERENCES Route(RouteId)
                                     ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: DriverSchedule
CREATE TABLE DriverSchedule (
                                DriverScheduleId VARCHAR(10) PRIMARY KEY,
                                DriverId VARCHAR(10) NOT NULL,
                                RouteId VARCHAR(10) NOT NULL,
                                Date DATE NOT NULL,
                                CONSTRAINT FOREIGN KEY(DriverId) REFERENCES Driver(DriverId)
                                    ON UPDATE CASCADE ON DELETE CASCADE,
                                CONSTRAINT FOREIGN KEY(RouteId) REFERENCES Route(RouteId)
                                    ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO User VALUES
                     ('U001', 'Admin1', 'password1', 'Admin', 'admin1@example.com'),
                     ('U002', 'Parent1', 'password2', 'Parent', 'parent1@example.com'),
                     ('U003', 'Driver1', 'password3', 'Driver', 'driver1@example.com'),
                     ('U004', 'Parent2', 'password4', 'Parent', 'parent2@example.com'),
                     ('U005', 'Driver2', 'password5', 'Driver', 'driver2@example.com'),
                     ('U006', 'Admin2', 'password6', 'Admin', 'admin2@example.com'),
                     ('U007', 'Parent3', 'password7', 'Parent', 'parent3@example.com'),
                     ('U008', 'Driver3', 'password8', 'Driver', 'driver3@example.com'),
                     ('U009', 'Parent4', 'password9', 'Parent', 'parent4@example.com'),
                     ('U010', 'Driver4', 'password10', 'Driver', 'driver4@example.com');

INSERT INTO Attendance VALUES
                           ('A001', 'January', 20),
                           ('A002', 'February', 18),
                           ('A003', 'March', 22),
                           ('A004', 'April', 15),
                           ('A005', 'May', 25),
                           ('A006', 'June', 20),
                           ('A007', 'July', 23),
                           ('A008', 'August', 21),
                           ('A009', 'September', 19),
                           ('A010', 'October', 20);

INSERT INTO Student VALUES
                        ('S001', 'Student1', 'Parent1', '123 Main St', 'student1@example.com','6A', '123-456-7890'),
                        ('S002', 'Student2', 'Parent2', '456 Park Ave', 'student2@example.com','7B', '234-567-8901'),
                        ('S003', 'Student3', 'Parent3', '789 Oak Dr', 'student3@example.com','6D', '345-678-9012'),
                        ('S004', 'Student4', 'Parent4', '321 Pine St', 'student4@example.com','4D', '456-789-0123'),
                        ('S005', 'Student5', 'Parent5', '654 Maple Ave', 'student5@example.com','9D', '567-890-1234'),
                        ('S006', 'Student6', 'Parent6', '987 Cedar Dr', 'student6@example.com','7D', '678-901-2345'),
                        ('S007', 'Student7', 'Parent7', '111 Elm St', 'student7@example.com','2D', '789-012-3456'),
                        ('S008', 'Student8', 'Parent8', '222 Birch Rd', 'student8@example.com','6A', '890-123-4567'),
                        ('S009', 'Student9', 'Parent9', '333 Fir Ln', 'student9@example.com','6F', '901-234-5678'),
                        ('S010', 'Student10', 'Parent10', '444 Spruce Blvd', 'student10@example.com','4D', '012-345-6789');

INSERT INTO Driver VALUES
                       ('D001', 'Driver1', 'L123456', '123-456-7890'),
                       ('D002', 'Driver2', 'L789012', '234-567-8901'),
                       ('D003', 'Driver3', 'L345678', '345-678-9012'),
                       ('D004', 'Driver4', 'L901234', '456-789-0123'),
                       ('D005', 'Driver5', 'L567890', '567-890-1234'),
                       ('D006', 'Driver6', 'L234567', '678-901-2345'),
                       ('D007', 'Driver7', 'L890123', '789-012-3456'),
                       ('D008', 'Driver8', 'L456789', '890-123-4567'),
                       ('D009', 'Driver9', 'L123890', '901-234-5678'),
                       ('D010', 'Driver10', 'L789456', '012-345-6789');

INSERT INTO Expenses VALUES
                         ('E001', 'Diesel', '2024-01-01', 50.00),
                         ('E002', 'Maintenance', '2024-02-01', 75.00),
                         ('E003', 'Salaries', '2024-03-01', 100.00),
                         ('E004', 'Insurance', '2024-04-01', 120.00),
                         ('E005', 'Repairs', '2024-05-01', 60.00),
                         ('E006', 'Diesel', '2024-06-01', 55.00),
                         ('E007', 'Tires', '2024-07-01', 150.00),
                         ('E008', 'Oil Change', '2024-08-01', 65.00),
                         ('E009', 'Brakes', '2024-09-01', 80.00),
                         ('E010', 'Diesel', '2024-10-01', 52.00);

INSERT INTO Payment VALUES
                        ('P001', 'Cash', '2024-01-10', 'Paid', 100.00),
                        ('P002', 'Online', '2024-02-15', 'Unpaid', 150.00),
                        ('P003', 'Cash', '2024-03-20', 'Paid', 120.00),
                        ('P004', 'Online', '2024-04-25', 'Paid', 130.00),
                        ('P005', 'Cash', '2024-05-30', 'Unpaid', 110.00),
                        ('P006', 'Online', '2024-06-05', 'Paid', 115.00),
                        ('P007', 'Cash', '2024-07-10', 'Unpaid', 140.00),
                        ('P008', 'Online', '2024-08-15', 'Paid', 125.00),
                        ('P009', 'Cash', '2024-09-20', 'Paid', 105.00),
                        ('P010', 'Online', '2024-10-25', 'Unpaid', 135.00);

INSERT INTO Vehicle VALUES
                        ('V001', 'AB123CD', 'Bus', 2.5),
                        ('V002', 'EF456GH', 'Van', 1.8),
                        ('V003', 'IJ789KL', 'Bus', 3.0),
                        ('V004', 'MN012OP', 'Van', 2.0),
                        ('V005', 'QR345ST', 'Bus', 2.8),
                        ('V006', 'UV678WX', 'Van', 1.9),
                        ('V007', 'YZ901AB', 'Bus', 3.2),
                        ('V008', 'CD234EF', 'Van', 2.1),
                        ('V009', 'GH567IJ', 'Bus', 2.7),
                        ('V010', 'KL890MN', 'Van', 1.7);

INSERT INTO Route VALUES
                      ('R001', 'City Center', 'School', 15.5, 'P001'),
                      ('R002', 'Suburb', 'School', 10.0, 'P002'),
                      ('R003', 'North District', 'School', 20.0, 'P003'),
                      ('R004', 'South District', 'School', 12.5, 'P004'),
                      ('R005', 'West District', 'School', 18.0, 'P005'),
                      ('R006', 'East District', 'School', 14.0, 'P006'),
                      ('R007', 'Central Park', 'School', 13.5, 'P007'),
                      ('R008', 'River Side', 'School', 11.0, 'P008'),
                      ('R009', 'Mountain View', 'School', 16.0, 'P009'),
                      ('R010', 'Valley Heights', 'School', 9.0, 'P010');


INSERT INTO ServicePlans VALUES
                             ('SP001', 5.00, 'R001', 'S001'),
                             ('SP002', 4.50, 'R002', 'S002'),
                             ('SP003', 6.00, 'R003', 'S003'),
                             ('SP004', 5.50, 'R004', 'S004'),
                             ('SP005', 4.00, 'R005', 'S005'),
                             ('SP006', 4.75, 'R006', 'S006'),
                             ('SP007', 5.25, 'R007', 'S007'),
                             ('SP008', 5.10, 'R008', 'S008'),
                             ('SP009', 5.75, 'R009', 'S009'),
                             ('SP010', 4.85, 'R010', 'S010');


INSERT INTO VehicleSchedule VALUES
                                ('VS001', 'V001', 'R001', '2024-01-10', '08:00:00', '15:00:00'),
                                ('VS002', 'V002', 'R002', '2024-02-10', '08:15:00', '15:15:00'),
                                ('VS003', 'V003', 'R003', '2024-03-10', '08:30:00', '15:30:00'),
                                ('VS004', 'V004', 'R004', '2024-04-10', '08:00:00', '15:00:00'),
                                ('VS005', 'V005', 'R005', '2024-05-10', '08:15:00', '15:15:00'),
                                ('VS006', 'V006', 'R006', '2024-06-10', '08:30:00', '15:30:00'),
                                ('VS007', 'V007', 'R007', '2024-07-10', '08:00:00', '15:00:00'),
                                ('VS008', 'V008', 'R008', '2024-08-10', '08:15:00', '15:15:00'),
                                ('VS009', 'V009', 'R009', '2024-09-10', '08:30:00', '15:30:00'),
                                ('VS010', 'V010', 'R010', '2024-10-10', '08:00:00', '15:00:00');


INSERT INTO DriverSchedule VALUES
                               ('DS001', 'D001', 'R001', '2024-01-10'),
                               ('DS002', 'D002', 'R002', '2024-02-10'),
                               ('DS003', 'D003', 'R003', '2024-03-10'),
                               ('DS004', 'D004', 'R004', '2024-04-10'),
                               ('DS005', 'D005', 'R005', '2024-05-10'),
                               ('DS006', 'D006', 'R006', '2024-06-10'),
                               ('DS007', 'D007', 'R007', '2024-07-10'),
                               ('DS008', 'D008', 'R008', '2024-08-10'),
                               ('DS009', 'D009', 'R009', '2024-09-10'),
                               ('DS010', 'D010', 'R010', '2024-10-10');
