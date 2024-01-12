# Online-Banking-System
<br>
A banking application has been designed with the following classes to encapsulate various functionalities:
<br>
Classes:
<br>
Account:

Methods:
getAccount(): Retrieve account details.
generateAccount(): Generate a new account.
generateAccountNumber(): Generate a unique account number.
accountExists(): Check if an account exists.
<br>
User:

Methods:
register(): Register a new user.
login(): Authenticate user login.
userExists(): Check if a user exists.
<br>
AccountManager:

Methods:
credit(): Perform a credit transaction.
debit(): Perform a debit transaction.
transfer(): Transfer funds between accounts.
checkBalance(): Check the account balance.
<br>
BankingApp:

Methods:
mainMenu(): Display the main menu.
createInstances(): Create instances of the above classes.
connectWithDB(): Establish a connection with the database.
loadDrivers(): Load JDBC drivers.
<br>
Databases:
<br>
Accounts:

Columns: full name, email, acc no, balance, security pin
<br>
Users:

Columns: full name, email, password

<br>
Database queries : 
<br>
<br>
CREATE DATABASE banking_system;
<br>
USE banking_system;
<br>
CREATE TABLE users (
    full_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    PRIMARY KEY (full_name, email, password)
);
<br>
CREATE TABLE account (
    account_number BIGINT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    security_pin CHAR(4) NOT NULL
);
<br>
Covered Concepts:
<br>
Object-oriented programming
<br>
Encapsulation
<br>
Classes and objects
<br>
Methods
<br>
Access modifiers
<br>
JDBC concepts
<br>
CRUD operations in a database
<br>