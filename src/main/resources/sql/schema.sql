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
    Email    varchar(100) not null
);



create table Driver
(
    DriverId  varchar(10) not null
        primary key,
    Name      varchar(50) not null,
    LicenseNo varchar(50) not null,
    NIC       varchar(20) not null,
    ContactNo varchar(15) not null,
    Address   varchar(50) not null,
    Email     varchar(50) not null
);



create table Expense
(
    ExpenseId   varchar(10)    not null
        primary key,
    Date        date           not null,
    Amount      decimal(10, 2) not null,
    Description varchar(100)   null,
    UserId      varchar(10)    not null,
    foreign key (UserId) references User (UserId)
        on update cascade on delete cascade
);

create table Student
(
    StudentId      varchar(10)                not null
        primary key,
    StudentName    varchar(50)                not null,
    ParentName     varchar(50)                not null,
    PickupLocation varchar(100)               not null,
    Email          varchar(100)               not null,
    StudentGrade   varchar(10)                not null,
    ContactNo      varchar(15)                not null,
    UserId         varchar(10)                not null,
    CreditBalance  decimal(6, 2) default 0.00 null,
    foreign key (UserId) references User (UserId)
        on update cascade on delete cascade
);

create table Attendance
(
    AttendanceId varchar(10) not null
        primary key,
    StudentId    varchar(10) not null,
    Year         int         not null,
    Month        varchar(15) not null,
    DayCount     int         not null,
    DriverId     varchar(10) not null,
    foreign key (StudentId) references Student (StudentId)
        on update cascade on delete cascade,
    foreign key (DriverId) references Driver (DriverId)
        on update cascade on delete cascade
);

create table Payment
(
    PaymentId  varchar(10)    not null
        primary key,
    Date       date           not null,
    Amount     decimal(10, 2) not null,
    MonthlyFee decimal(10, 2) null,
    Balance    decimal(10, 2) null,
    Status     varchar(20)    not null,
    StudentId  varchar(10)    not null,
    foreign key (StudentId) references Student (StudentId)
        on update cascade on delete cascade
);

create table Vehicle
(
    VehicleId          varchar(10)   not null
        primary key,
    RegistrationNo     varchar(50)   not null,
    VehicleType        varchar(20)   not null,
    EngineCapacity     decimal(4, 1) not null,
    FuelType           varchar(20)   not null,
    Model              varchar(20)   null,
    SeatCount          int           not null,
    AvailableSeatCount int           not null
);

create table Route
(
    RouteId     varchar(10)    not null
        primary key,
    RouteName   varchar(50)    not null,
    StartPoint  varchar(100)   not null,
    Destination varchar(100)   not null,
    RouteFee    decimal(10, 2) not null
);



create table StudentRegistration
(
    StudentRegistrationId varchar(10)    not null
        primary key,
    StudentId             varchar(10)    not null,
    Distance              decimal(10, 2) not null,
    DayPrice              decimal(10, 2) not null,
    Date                  date           not null,
    RouteId               varchar(10)    not null,
    VehicleId             varchar(10)    not null,
    foreign key (StudentId) references Student (StudentId)
        on update cascade on delete cascade,
    foreign key (RouteId) references Route (RouteId)
        on update cascade on delete cascade,
    foreign key (VehicleId) references Vehicle (VehicleId)
        on update cascade on delete cascade
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

-- User Table
INSERT INTO User (UserId, UserName, Password, RoleType, Email) VALUES
                                                                   ('U001', 'admin', 'admin', '123', 'admin@swiftsts.lk'),
                                                                   ('U002', 'driver1', 'driver123', 'Driver', 'driver1@swiftsts.lk'),
                                                                   ('U003', 'driver2', 'driver123', 'Driver', 'driver2@swiftsts.lk'),
                                                                   ('U004', 'driver3', 'driver123', 'Driver', 'driver3@swiftsts.lk'),
                                                                   ('U005', 'driver4', 'driver123', 'Driver', 'driver4@swiftsts.lk');

INSERT INTO Expense (ExpenseId, Date, Amount, Description, UserId) VALUES
                                                                       ('EXP001', '2024-11-01', 150.00, 'Diesel refill for bus', 'U001'),
                                                                       ('EXP002', '2024-11-02', 500.00, 'Monthly vehicle maintenance', 'U002'),
                                                                       ('EXP003', '2024-11-03', 1200.00, 'Driver salary', 'U001'),
                                                                       ('EXP004', '2024-11-04', 250.00, 'Miscellaneous repairs', 'U003'),
                                                                       ('EXP005', '2024-11-05', 100.00, 'Toll charges', 'U001');


-- Student Table
INSERT INTO Student (StudentId, StudentName, ParentName, PickupLocation, Email, StudentGrade, ContactNo, UserId ,CreditBalance)
VALUES
    ('S001', 'Amal Perera', 'Sanduni Silva', 'Karapitiya', 'amal@galle.sch.lk', 'Grade 5', '0711234567', 'U003',1000.00),
    ('S002', 'Kavindi Silva', 'Sanduni Silva', 'Pinnaduwa', 'kavindi@galle.sch.lk', 'Grade 8', '0719876543', 'U003',500.00),
    ('S003', 'Nimesh Perera', 'Nuwan Jayasekara', 'Hikkaduwa', 'nimesh@galle.sch.lk', 'Grade 10', '0711122334', 'U005',400.00),
    ('S004', 'Tharindu Fernando', 'Nuwan Jayasekara', 'Gintota', 'tharindu@galle.sch.lk', 'Grade 7', '0775566778', 'U005',1500.00),
    ('S005', 'Sasini Kumari', 'Nuwan Jayasekara', 'Unawatuna', 'sasini@galle.sch.lk', 'Grade 3', '0756677889', 'U005',300.00);

-- Driver Table
INSERT INTO Driver (DriverId, Name, LicenseNo, NIC, ContactNo, Address, Email)
VALUES
    ('D001', 'Kamal Perera', 'B12345678', '882345678V', '0712456789', 'Wackwella Road, Galle', 'driver1@galle.sch.lk'),
    ('D002', 'Ruwan Fernando', 'C87654321', '912345678X', '0719876543', 'Dadalla, Galle', 'driver2@galle.sch.lk'),
    ('D003', 'Sunil Liyanage', 'B98765432', '852145698V', '0723456789', 'Karapitiya, Galle', 'driver3@galle.sch.lk'),
    ('D004', 'Sarath Kumara', 'C65498712', '902134567V', '0703456788', 'Pinnaduwa, Galle', 'driver4@galle.sch.lk'),
    ('D005', 'Chamara Silva', 'B45213456', '872145698V', '0712459876', 'Unawatuna, Galle', 'driver5@galle.sch.lk');

INSERT INTO Vehicle (VehicleId, RegistrationNo, VehicleType, EngineCapacity, FuelType, Model, SeatCount, AvailableSeatCount)
VALUES
    ('V001', 'ABC1234', 'Bus', 2.5, 'Diesel', 'ModelX', 40, 35),
    ('V002', 'XYZ5678', 'Van', 1.8, 'Petrol', 'ModelY', 15, 12),
    ('V003', 'LMN2345', 'Bus', 3.0, 'Diesel', 'ModelZ', 50, 45),
    ('V004', 'OPQ6789', 'Van', 2.0, 'Diesel', 'ModelA', 18, 16),
    ('V005', 'RST9012', 'Bus', 2.8, 'Diesel', 'ModelB', 45, 40);


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


INSERT INTO Payment (PaymentId, Date, Amount, MonthlyFee, Balance, Status, StudentId) VALUES
                                                                                          ('P001', '2024-11-01', 500.00, 1000.00, 500.00, 'Pending', 'S001'),
                                                                                          ('P002', '2024-11-02', 1000.00, 1000.00, 0.00, 'Paid', 'S002'),
                                                                                          ('P003', '2024-11-03', 800.00, 1200.00, 400.00, 'Pending', 'S003'),
                                                                                          ('P004', '2024-11-04', 1200.00, 1200.00, 0.00, 'Paid', 'S004'),
                                                                                          ('P005', '2024-11-05', 0.00, 1000.00, 1000.00, 'Overdue', 'S001');



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

