DROP DATABASE IF EXISTS SwiftSTS;
CREATE DATABASE SwiftSTS;
USE SwiftSTS;

create table User
(
    UserId   varchar(10)  not null
        primary key,
    UserName varchar(50)  not null,
    Password varchar(50)  not null,
    RoleType varchar(20)  not null,
    Email    varchar(100) not null,
    Name     varchar(30)  not null
);

create table Expense
(
    ExpenseId        varchar(10)    not null
        primary key,
    ExpensesCategory varchar(50)    not null,
    Date             date           not null,
    Amount           decimal(10, 2) not null,
    UserId           varchar(10)    not null,
    foreign key (UserId) references User (UserId)
        on update cascade on delete cascade
);

create table Student
(
    StudentId    varchar(10)  not null
        primary key,
    StudentName  varchar(50)  not null,
    ParentName   varchar(50)  not null,
    Address      varchar(100) not null,
    Email        varchar(100) not null,
    StudentGrade varchar(10)  not null,
    ContactNo    varchar(15)  not null,
    UserId      varchar(10)  not null ,
    foreign key (UserId) references User (UserId)
        on update cascade on delete cascade

);

create table Driver
(
    DriverId  varchar(10)  not null
        primary key,
    Name      varchar(50)  not null,
    LicenseNo varchar(50)  not null,
    NIC       varchar(20)  not null,
    ContactNo varchar(15)  not null,
    Address   varchar(50)  not null,
    Email     varchar(50) not null
);

create table Attendance
(
    AttendanceId varchar(10) not null
        primary key,
    Month        varchar(15) not null,
    DayCount     int         not null,
    Year         int         not null,
    StudentId    varchar(10) not null,
    DriverId     varchar(10)  not null,
    foreign key (DriverId) references Driver (DriverId)
        on update cascade on delete cascade,
    foreign key (StudentId) references Student (StudentId)
        on update cascade on delete cascade
);
create table Route
(
    RouteId     varchar(10)    not null
        primary key,
    StartPoint  varchar(100)   not null,
    Destination varchar(100)   not null,
    day_fee     decimal(10, 2) not null
);

create table Payment
(
    PaymentId     varchar(10)    not null
        primary key,
    Date          date           not null,
    Amount        decimal(10, 2) not null,
    CreditBalance decimal(10, 2) null,
    MonthlyFee    decimal(10, 2) null,
    Balance       decimal(10, 2) null,
    Status        varchar(20)    not null,
    StudentId     varchar(10)    not null,
    foreign key (StudentId) references Student (StudentId)
        on update cascade on delete cascade
);

create table ServicePlans
(
    ServicePlanId varchar(10)    not null
        primary key,
    DayPrice      decimal(10, 2) not null,
    Date          date           not null,
    RouteId       varchar(10)    not null,
    StudentId     varchar(10)    not null,
    Distance      decimal        not null,
    PaymentId     varchar(10)    not null,
    foreign key (RouteId) references Route (RouteId)
        on update cascade on delete cascade,
    foreign key (StudentId) references Student (StudentId)
        on update cascade on delete cascade,
    foreign key (PaymentId) references Payment (PaymentId)
        on update cascade on delete cascade
);

create table DriverSchedule
(
    DriverScheduleId varchar(10) not null
        primary key,
    DriverId         varchar(10) not null,
    RouteId          varchar(10) not null,
    Date             date        not null,
    foreign key (DriverId) references Driver (DriverId)
        on update cascade on delete cascade,
    foreign key (RouteId) references Route (RouteId)
        on update cascade on delete cascade
);


create table Vehicle
(
    VehicleId      varchar(10)   not null
        primary key,
    RegistrationNo varchar(50)   not null,
    VehicleType    varchar(20)   not null,
    EngineCapacity decimal(4, 1) not null,
    FuelType       varchar(20)  not null,
    Model          varchar(20)   null,
    VehicleNo      varchar(20)   null
);

create table VehicleSchedule
(
    VehicleScheduleId varchar(10) not null
        primary key,
    VehicleId         varchar(10) not null,
    RouteId           varchar(10) not null,
    Date              date        not null,
    ArrivalTime       time        not null,
    DepartureTime     time        not null,
    foreign key (VehicleId) references Vehicle (VehicleId)
        on update cascade on delete cascade,
    foreign key (RouteId) references Route (RouteId)
        on update cascade on delete cascade
);



========================================================================


USE SwiftSTS;

-- User Table
INSERT INTO User (UserId, UserName, Password, RoleType, Email, Name) VALUES
                                                                         ('U001', 'admin', 'admin', '123', 'admin@swiftsts.lk', 'Admin User'),
                                                                         ('U002', 'driver1', 'driver123', 'Driver', 'driver1@swiftsts.lk', 'Ruwan Perera'),
                                                                         ('U003', 'driver2', 'driver123', 'Driver', 'driver2@swiftsts.lk', 'Kamal Silva'),
                                                                         ('U004', 'parent1', 'parent123', 'Parent', 'parent1@swiftsts.lk', 'Nimal Jayasuriya'),
                                                                         ('U005', 'parent2', 'parent123', 'Parent', 'parent2@swiftsts.lk', 'Sunil Fernando');

-- Expense Table
INSERT INTO Expense (ExpenseId, ExpensesCategory, Date, Amount, UserId) VALUES
                                                                            ('EX001', 'Fuel', '2024-11-01', 2000.00, 'U002'),
                                                                            ('EX002', 'Maintenance', '2024-11-05', 1500.00, 'U002'),
                                                                            ('EX003', 'Toll Fees', '2024-11-03', 500.00, 'U003'),
                                                                            ('EX004', 'Salary', '2024-11-10', 25000.00, 'U003'),
                                                                            ('EX005', 'Insurance', '2024-11-08', 1200.00, 'U001');

-- Student Table
INSERT INTO Student (StudentId, StudentName, ParentName, Address, Email, StudentGrade, ContactNo, UserId) VALUES
                                                                                                              ('S001', 'Saman Rajapaksha', 'Nimal Rajapaksha', 'Kandy', 'saman@school.lk', 'Grade 10', '0771234567', 'U004'),
                                                                                                              ('S002', 'Kavindi Kumari', 'Sunil Kumari', 'Colombo', 'kavindi@school.lk', 'Grade 9', '0772234567', 'U005'),
                                                                                                              ('S003', 'Mahesh Silva', 'Chandana Silva', 'Galle', 'mahesh@school.lk', 'Grade 8', '0773234567', 'U004'),
                                                                                                              ('S004', 'Piumi Fernando', 'Janaka Fernando', 'Kurunegala', 'piumi@school.lk', 'Grade 7', '0774234567', 'U005'),
                                                                                                              ('S005', 'Thilina Perera', 'Nayana Perera', 'Jaffna', 'thilina@school.lk', 'Grade 11', '0775234567', 'U004');

-- Driver Table
INSERT INTO Driver (DriverId, Name, LicenseNo, NIC, ContactNo, Address, Email) VALUES
                                                                                   ('D001', 'Ruwan Perera', 'B123456', '882345678V', '0776001234', 'Kandy', 'ruwan@swiftsts.lk'),
                                                                                   ('D002', 'Kamal Silva', 'B234567', '891234567V', '0776005678', 'Galle', 'kamal@swiftsts.lk'),
                                                                                   ('D003', 'Sunil Jayasooriya', 'B345678', '900987654V', '0776007890', 'Colombo', 'sunil@swiftsts.lk'),
                                                                                   ('D004', 'Chamara Wickramasinghe', 'B456789', '912345678V', '0776003456', 'Matara', 'chamara@swiftsts.lk'),
                                                                                   ('D005', 'Asela Gunasekara', 'B567890', '921234567V', '0776001235', 'Kegalle', 'asela@swiftsts.lk');

-- Attendance Table
INSERT INTO Attendance (AttendanceId, Month, DayCount, Year, StudentId, DriverId) VALUES
                                                                                      ('AT001', 'November', 18, 2024, 'S001', 'D001'),
                                                                                      ('AT002', 'November', 20, 2024, 'S002', 'D002'),
                                                                                      ('AT003', 'November', 22, 2024, 'S003', 'D003'),
                                                                                      ('AT004', 'November', 15, 2024, 'S004', 'D004'),
                                                                                      ('AT005', 'November', 19, 2024, 'S005', 'D005');

-- Route Table
INSERT INTO Route (RouteId, StartPoint, Destination, day_fee) VALUES
                                                                  ('R001', 'Kandy', 'Colombo', 500.00),
                                                                  ('R002', 'Galle', 'Colombo', 450.00),
                                                                  ('R003', 'Kurunegala', 'Colombo', 400.00),
                                                                  ('R004', 'Matara', 'Colombo', 550.00),
                                                                  ('R005', 'Jaffna', 'Colombo', 600.00);

-- Payment Table
INSERT INTO Payment (PaymentId, Date, Amount, CreditBalance, MonthlyFee, Balance, Status, StudentId) VALUES
                                                                                                         ('P001', '2024-11-01', 1000.00, 100.00, 1100.00, 0.00, 'Paid', 'S001'),
                                                                                                         ('P002', '2024-11-02', 900.00, 0.00, 900.00, 0.00, 'Paid', 'S002'),
                                                                                                         ('P003', '2024-11-03', 1200.00, 300.00, 1500.00, 0.00, 'Partial', 'S003'),
                                                                                                         ('P004', '2024-11-04', 1300.00, 0.00, 1300.00, 0.00, 'Paid', 'S004'),
                                                                                                         ('P005', '2024-11-05', 800.00, 200.00, 1000.00, 0.00, 'Partial', 'S005');

-- ServicePlans Table
INSERT INTO ServicePlans (ServicePlanId, DayPrice, Date, RouteId, StudentId, Distance, PaymentId) VALUES
                                                                                                      ('SP001', 500.00, '2024-11-01', 'R001', 'S001', 120.0, 'P001'),
                                                                                                      ('SP002', 450.00, '2024-11-01', 'R002', 'S002', 100.0, 'P002'),
                                                                                                      ('SP003', 400.00, '2024-11-01', 'R003', 'S003', 150.0, 'P003'),
                                                                                                      ('SP004', 550.00, '2024-11-01', 'R004', 'S004', 200.0, 'P004'),
                                                                                                      ('SP005', 600.00, '2024-11-01', 'R005', 'S005', 250.0, 'P005');

-- DriverSchedule Table
INSERT INTO DriverSchedule (DriverScheduleId, DriverId, RouteId, Date) VALUES
                                                                           ('DS001', 'D001', 'R001', '2024-11-01'),
                                                                           ('DS002', 'D002', 'R002', '2024-11-02'),
                                                                           ('DS003', 'D003', 'R003', '2024-11-03'),
                                                                           ('DS004', 'D004', 'R004', '2024-11-04'),
                                                                           ('DS005', 'D005', 'R005', '2024-11-05');

-- Vehicle Table
INSERT INTO Vehicle (VehicleId, RegistrationNo, VehicleType, EngineCapacity, FuelType, Model, VehicleNo) VALUES
                                                                                                             ('V001', 'ABC1234', 'Van', 2.5, 'Diesel', 'Toyota', 'ABC1234'),
                                                                                                             ('V002', 'DEF5678', 'Bus', 4.0, 'Petrol', 'Nissan', 'DEF5678'),
                                                                                                             ('V003', 'GHI9101', 'Car', 1.8, 'Diesel', 'Honda', 'GHI9101'),
                                                                                                             ('V004', 'JKL2345', 'Three-Wheeler', 0.8, 'Petrol', 'Bajaj', 'JKL2345'),
                                                                                                             ('V005', 'MNO6789', 'Lorry', 5.0, 'Diesel', 'Tata', 'MNO6789');

-- VehicleSchedule Table
INSERT INTO VehicleSchedule (VehicleScheduleId, VehicleId, RouteId, Date, ArrivalTime, DepartureTime) VALUES
                                                                                                          ('VS001', 'V001', 'R001', '2024-11-01', '08:00:00', '17:00:00'),
                                                                                                          ('VS002', 'V002', 'R002', '2024-11-02', '08:30:00', '17:30:00'),
                                                                                                          ('VS003', 'V003', 'R003', '2024-11-03', '09:00:00', '18:00:00'),
                                                                                                          ('VS004', 'V004', 'R004', '2024-11-04', '08:15:00', '17:15:00'),
                                                                                                          ('VS005', 'V005', 'R005', '2024-11-05', '09:30:00', '18:30:00');
