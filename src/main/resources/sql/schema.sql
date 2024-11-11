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
    UserId      varchar(10)  not null,
    foreign key (UserId) references User (UserId)
        on update cascade on delete cascade

);

create table Driver
(
    DriverId  varchar(10)  not null
        primary key,
    Name      varchar(50)  not null,
    LicenseNo varchar(50)  not null,
    ContactNo varchar(15)  not null,
    Email     varchar(100) not null,
    StudentId varchar(10) not null,
    foreign key (StudentId) references Student(StudentId)
        on delete cascade
        on update cascade
);

create table Attendance
(
    AttendanceId varchar(10) not null
        primary key,
    Month        varchar(15) not null,
    DayCount     int         not null,
    Year         int         not null,
    StudentId varchar(10) not null,
    foreign key (StudentId) references Student(StudentId)
        on delete cascade
        on update cascade
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
    PaymentMethod varchar(30)    not null,
    Date          date           not null,
    Status        varchar(20)    not null,
    Amount        decimal(10, 2) not null
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

