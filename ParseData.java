package com.company;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ParseData {
    private static final String CSV_FILE_PATH = "./Textbook_Exchange.csv";

    private static int studentId;
    private static String name;
    private static String email;
    private static String password;
    private static int bookId;
    private static String bookName;
    private static String isbn;
    private static String subject;
    private static int subjectId;
    private static String className;
    private static int classId;
    private static int required;
    private static int transactionId;
    private static int transactionType;


    public static void getRecords()
    {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withHeader("Name", "Email", "Password", "BookID", "BookName", "ISBN", "Subject", "SubjectID", "ClassName", "ClassID", "Required", "TransactionID", "TransactionType")
                        //  .withHeader("Name", "Email", "Password", "BookID", "BookName", "ISBN", "Subject", "SubjectID", "ClassID", "Required", "TransactionID", "TransactionType")
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                studentId = Integer.parseInt(csvRecord.get(0));
                name = csvRecord.get(1);
                email = csvRecord.get(2);
                password = csvRecord.get(3);
                bookId = Integer.parseInt(csvRecord.get(4));
                bookName = csvRecord.get(5);
                isbn = csvRecord.get(6);
                subject = csvRecord.get(7);
                subjectId = Integer.parseInt(csvRecord.get(8));
                className = csvRecord.get(9); //added
                classId = Integer.parseInt(csvRecord.get(10));
                required = Integer.parseInt(csvRecord.get(11));
                transactionId = Integer.parseInt(csvRecord.get(12));
                transactionType = Integer.parseInt(csvRecord.get(13));
                insertRecord();

                /*System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------");
                System.out.println("Student ID: " + studentId);
                System.out.println("Name : " + name);
                System.out.println("Email : " + email);
                System.out.println("Password: " + password);
                System.out.println("Book ID: " + bookId);
                System.out.println("Book Name: " + bookName);
                System.out.println("ISBN Number: " + isbn);
                System.out.println("Subject: " + subject);
                System.out.println("Subject ID: " + subjectId);
                System.out.println("Class ID: " + classId);
                System.out.println("Class Name: " + className);
                System.out.println("Required: " + required);
                System.out.println("Transaction ID: " + transactionId);
                System.out.println("Transaction Type: " + transactionType);
                System.out.println("---------------\n\n");*/
            }
        }

        catch (IOException e){
            System.out.println("Error: " + e);
        }
    }

    public static void createTable()
    {
        Connection con = DBConfig.getMyConnection();
        String accQuery = "CREATE TABLE IF NOT EXISTS ACCOUNTS (" +
                "StudentId INTEGER, " +
                "FullName VARCHAR(50), " +
                "Email VARCHAR(100), " +
                "Password VARCHAR(15), " +
                "BookId INTEGER, " +
                "BookName VARCHAR(75),  " +
                "Isbn VARCHAR(25)," +
                "SubjectId INTEGER," +
                "Subject VARCHAR(30)," +
                "ClassId INTEGER," +
                "ClassName VARCHAR(15), " + //added this line
                "Required INTEGER ," +
                "TransactionId INTEGER," +
                "TransactionType INTEGER," +
                "PRIMARY KEY (StudentId, BookId, SubjectId, ClassId, TransactionId));";

        String stuQuery = "CREATE TABLE IF NOT EXISTS Student (" +
                "StudentId INTEGER, " +
                "FullName VARCHAR(50), " +
                "Email VARCHAR(100), " +
                "Password VARCHAR(15));";

        String booksQuery = "CREATE TABLE IF NOT EXISTS Books (" +
                "BookId INTEGER, " +
                "BookName VARCHAR(50), " +
                "Isbn VARCHAR(25),"+
                "Subject VARCHAR(30), " +
                "ClassName VARCHAR(15));";

        String tranQuery  = "CREATE TABLE IF NOT EXISTS Transactions (" +
                "TransactionId INTEGER, "+
                "TransactionType INTEGER);";

        String subjectQuery = "CREATE TABLE IF NOT EXISTS Subjects (" +
                "SubjectId INTEGER, "+
                "Subject VARCHAR(30));";

        String classQuery = "CREATE TABLE IF NOT EXISTS Class (" +
                "ClassId INTEGER, "+
                "ClassName VARCHAR(15), "+
                "Required INTEGER);";


        try {
            PreparedStatement ps = con.prepareStatement(accQuery);
            ps.execute();
            PreparedStatement stuPs = con.prepareStatement(stuQuery);
            stuPs.execute();

            PreparedStatement bookPs  = con.prepareStatement(booksQuery);
            bookPs.execute();

            PreparedStatement tranPs = con.prepareStatement(tranQuery);
            tranPs.execute();

            PreparedStatement subPs = con.prepareStatement(subjectQuery);
            subPs.execute();

            PreparedStatement classPs = con.prepareStatement(classQuery);
            classPs.execute();

        }
        catch (SQLException sq)
        {
            System.out.println("Error: "+ sq);
        }


    }

    public static void insertRecord()
    {
        Connection con = DBConfig.getMyConnection();
        String query = "INSERT INTO ACCOUNTS(StudentId, FullName, Email, Password, BookId, " +
                "BookName, Isbn, SubjectId, Subject, ClassId, ClassName, Required, TransactionId, TransactionType)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String stuQuery = "INSERT INTO Student(StudentId, FullName, Email, Password)"+
                "VALUES (?,?,?,?)";

        String booksQuery = "INSERT INTO Books(BookId, BookName, Isbn, Subject, ClassName)"+
                "VALUES (?,?,?,?,?)";

        String tranQuery = "INSERT INTO Transactions(TransactionId, TransactionType)"+
                "VALUES (?,?)";

        String subjectQuery = "INSERT INTO Subjects(SubjectId, Subject)"+
                "VALUES (?,?)";

        String classQuery = "INSERT INTO Class(ClassId, ClassName, Required)" +
                "VALUES (?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4,password);
            ps.setInt(5,bookId);
            ps.setString(6,bookName);
            ps.setString(7,isbn);
            ps.setInt(8,subjectId);
            ps.setString(9,subject);
            ps.setInt(10,classId);
            ps.setString(11, className);
            ps.setInt(12,required);
            ps.setInt(13,transactionId);
            ps.setInt(14,transactionType);
            ps.execute();
            System.out.println("Record "+studentId+" inserted.");

            PreparedStatement stuPs = con.prepareStatement(stuQuery);
            stuPs.setInt(1, studentId);
            stuPs.setString(2, name);
            stuPs.setString(3, email);
            stuPs.setString(4,password);
            stuPs.execute();


            PreparedStatement bookPs = con.prepareStatement(booksQuery);
            bookPs.setInt(1, bookId);
            bookPs.setString(2, bookName);
            bookPs.setString(3, isbn);
            bookPs.setString(4, subject);
            bookPs.setString(5, className);
            bookPs.execute();

            PreparedStatement tranPs = con.prepareStatement(tranQuery);
            tranPs.setInt(1, transactionId);
            tranPs.setInt(2,transactionType);
            tranPs.execute();

            PreparedStatement subPs = con.prepareStatement(subjectQuery);
            subPs.setInt(1,subjectId);
            subPs.setString(2, subject);
            subPs.execute();

            PreparedStatement classPs = con.prepareStatement(classQuery);
            classPs.setInt(1, classId);
            classPs.setString(2, className);
            classPs.setInt(3, required);
            classPs.execute();


        }
        catch (SQLException sq)
        {
            System.out.println(sq);
            System.out.println("Error: "+ sq);
        }
    }
}