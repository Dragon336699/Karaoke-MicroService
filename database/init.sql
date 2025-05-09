-- 1. Booked-Room_Karaoke
CREATE DATABASE [Booked-Room_Karaoke];
GO
USE [Booked-Room_Karaoke];
GO
CREATE TABLE [dbo].[BookedRoom] (
    [Id] UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    [CheckInTime] DATETIME,
    [CheckOutTime] DATETIME,
    [RoomId] UNIQUEIDENTIFIER,
    [PriceAtBookTime] DECIMAL(10,2),
    [BookingId] UNIQUEIDENTIFIER
);
GO

-- 2. Booking_Karaoke
CREATE DATABASE [Booking_Karaoke];
GO
USE [Booking_Karaoke];
GO
CREATE TABLE [dbo].[Booking] (
    [Id] UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    [BookDay] DATE,
    [Note] NVARCHAR(MAX),
    [CustomerId] UNIQUEIDENTIFIER,
    [UserId] UNIQUEIDENTIFIER
);
GO

-- 3. Customers_Karaoke
CREATE DATABASE [Customers_Karaoke];
GO
USE [Customers_Karaoke];
GO
CREATE TABLE [dbo].[Customers] (
    [Id] UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    [FullName] NVARCHAR(255),
    [PhoneNumber] NVARCHAR(20)
);
GO

-- 4. Room_Karaoke
CREATE DATABASE [Room_Karaoke];
GO
USE [Room_Karaoke];
GO
CREATE TABLE [dbo].[Room] (
    [Id] UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    [RoomNumber] NVARCHAR(50),
    [PricePerHour] DECIMAL(10,2)
);
GO

-- 5. User_Karaoke
CREATE DATABASE [User_Karaoke];
GO
USE [User_Karaoke];
GO
CREATE TABLE [dbo].[Users] (
    [Id] UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    [Username] NVARCHAR(100),
    [Password] NVARCHAR(255),
    [FullName] NVARCHAR(255),
    [PhoneNumber] NVARCHAR(20),
    [Email] NVARCHAR(255),
    [Address] NVARCHAR(MAX)
);
GO
