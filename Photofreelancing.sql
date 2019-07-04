drop table reservation;
drop table image;
drop table album;
drop table photographer;
drop table customer;

create table photographer (
	ID int primary key generated always as identity,
	Username varchar (30) NOT NULL UNIQUE,
	Password varchar (100) NOT NULL,
	Fname varchar (30) NOT NULL,
	Lname varchar (30) NOT NULL,
	DOB date,
	Nationality varchar (20),
	Address varchar (200),
	EduHS boolean,
	EduBS boolean,
	EduMS boolean,
	Experience varchar (500),
	Rate double)
;
create table customer (
	ID int primary key generated always as identity,
	Username varchar (30) NOT NULL UNIQUE,
	Password varchar (100) NOT NULL,
	Fname varchar (30) NOT NULL,
	Lname varchar (30) NOT NULL,
	DOB date ,
	Nationality varchar (20),
	Address varchar (200))
;
create table album (
	ID int primary key generated always as identity,
	Name varchar (100) NOT NULL,
	Photographer_id int NOT NULL,
	constraint fk_album_phtgr foreign key (Photographer_id)
		references photographer (ID)
) 
;
create table image (
	ID int primary key generated always as identity,
	File_name varchar (100) NOT NULL,
	Dir_path varchar (100) NOT NULL,
	Album_id int NOT NULL,
	constraint fk_img_album foreign key (Album_id)
		references album (ID)
) 
;
create table reservation (
	ID int primary key generated always as identity,
	Customer_id int NOT NULL,
	Photographer_id int NOT NULL,
	Date_reserved date NOT NULL,
	Time_reserved time NOT NULL,
	Venue varchar (200) NOT NULL,
	Proposed_amount double NOT NULL,
	Status varchar (10) NOT NULL,
	constraint fk_reserve_customer foreign key (Customer_id)
		references customer (ID),
	constraint fk_reserve_phtgr foreign key (Photographer_id)
		references photographer (ID)
) 
;

