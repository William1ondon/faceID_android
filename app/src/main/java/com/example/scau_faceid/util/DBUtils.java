package com.example.scau_faceid.util;

import android.util.Log;

import com.example.scau_faceid.info.AccountStudent;
import com.example.scau_faceid.info.AccountTeacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Base64;

public class DBUtils {
    private static final String TAG = "DBUtils";

    private ArrayList<AccountStudent> StudentList = null;

    private static Connection getConnection(String dbName) {
        Connection conn = null;
        try {
            //加载驱动，Java数据库连接，（Java Database Connectivity，简称JDBC）是Java语言中用来规范客户端程序如何来访问数据库的应用程序接口，提供了诸如查询和更新数据库中数据的方法。
            //为什么加载驱动要用Class.forName(“com.mysql.jdbc.Driver”) : https://blog.csdn.net/yue_2018/article/details/88294385
            Class.forName("com.mysql.jdbc.Driver");
            //jdbc:mysql://rm-bp1o9ju136152xb5iso.mysql.rds.aliyuncs.com为“外网地址”，3306为端口号
            conn = DriverManager.getConnection(
                    "jdbc:mysql://rm-bp1o9ju136152xb5iso.mysql.rds.aliyuncs.com:3306/" + dbName,
                    "user_william", "WOAIhyy520");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static void close(Object o) {
        if (o == null) {
            return;
        }
        if (o instanceof ResultSet) {
            try {
                ((ResultSet) o).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (o instanceof Statement) {
            try {
                ((Statement) o).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (o instanceof Connection) {
            Connection c = (Connection) o;
            try {
                if (!c.isClosed()) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs, Statement stmt,
                             Connection conn) {
        close(rs);
        close(stmt);
        close(conn);
    }

    public static void close(ResultSet rs,
                             Connection conn) {
        close(rs);
        close(conn);
    }

    public static boolean ifReapted(AccountStudent student) {
        Connection conn = getConnection("test_database");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //数据库之元数据——DatabaseMetaData的getMetaData()方法的简单使用 : https://blog.csdn.net/fhf2424045058/article/details/99292006
        try {
            pstmt = conn.prepareStatement("select * from StudentInfo ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (student.getAccount().equals(rs.getObject("Account")) || student.getPhone().equals(rs.getObject("Phone")) || student.getEmail().equals(rs.getObject("Email"))) {
                    close(rs, conn);
                    return false;
                }
            }

            close(rs, conn);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) {
                close(rs, conn);
            }
        }
        return false;
    }

    public static boolean UploadInfo(AccountStudent student) {
        String sql = "insert into studentinfo(Name,Account,Password,Sex,Major,Phone,Email) values  ('" + student.getName() + "','" + student.getAccount() + "','" + student.getPassword() + "'," + student.getSex() + ",'" + student.getMajor() + "','" + student.getPhone() + "','" + student.getEmail()
                + "');";
        Connection conn = getConnection("test_database");
        PreparedStatement pstmt = null;//若无相同账号（学号）、相同手机、相同邮箱则视为新学生，执行数据上传
        try {
            pstmt = conn.prepareStatement(sql);
            boolean success = pstmt.execute();
            close(conn);
            return !success;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        close(conn);
        return false;
    }

    public static AccountStudent StudentLogin(String ETAccount, String ETPassword) {
        Connection conn = getConnection("test_database");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from StudentInfo ");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getObject("Account").equals(ETAccount) && rs.getObject("Password").equals(ETPassword)) {
                    AccountStudent student = new AccountStudent(rs.getObject("Name").toString(), rs.getObject("Account").toString(), rs.getObject("Password").toString(), (Boolean) rs.getObject("Sex"), rs.getObject("Major").toString(), rs.getObject("Phone").toString(), rs.getObject("Email").toString(), null);
                    return student;
                }
            }
            close(rs, conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(rs, conn);
        }
        return null;
    }

    public static AccountTeacher TeacherLogin(String ETAccount, String ETPassword) {
        Connection conn = getConnection("test_database");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from TeacherInfo ");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getObject("Account").equals(ETAccount) && rs.getObject("Password").equals(ETPassword)) {
                    AccountTeacher teacher = new AccountTeacher(rs.getObject("Account").toString(), rs.getObject("Password").toString());
                    return teacher;
                }
            }
            close(rs, conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(rs, conn);
        }
        return null;
    }

    public static Boolean returnBoolean(AccountStudent loginStudent) {
        if (loginStudent != null) {
            return true;
        }
        return false;
    }

    public static Boolean ReturnBoolean(AccountTeacher loginTeacher) {
        if (loginTeacher != null) {
            return true;
        }
        return null;
    }

    public static boolean ifFeatureUploaded(AccountStudent student, byte[] dbFaceFeature) {
        String afterReplace = Base64Encoder(dbFaceFeature);
        String sql = "update StudentInfo set FaceFeature = '" + afterReplace + "' where Account = '" + student.getAccount() + "'";
        Connection conn = getConnection("test_database");
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            boolean success = pstmt.execute();
            close(conn);
            return !success;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        close(conn);
        return false;
    }

    private static String Base64Encoder(byte[] dbFaceFeature) {//人脸特征数据采用Base64编码成string后再上传到数据库，否则乱码中的单引号会引起sql语法错误
        Base64.Encoder base64 = null;
        String result = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            base64 = Base64.getEncoder();
            result = base64.encodeToString(dbFaceFeature);
        }
        return result;
    }

    private static byte[] Base64Decoder(String s) {
        Base64.Decoder base64 = null;
        String justForCheck = null;
        byte[] result = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            base64 = Base64.getDecoder();
            result = base64.decode(s);
        }
        return result;
    }

    public static boolean updatePassword(String etPassword, String email) {
        String sql = "update StudentInfo set Password = '" + etPassword + "' where Email = '" + email + "'";
        Connection conn = getConnection("test_database");
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            boolean success = pstmt.execute();
            close(conn);
            return !success;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        close(conn);
        return false;
    }


    public int initStudentList() {
        Connection conn = getConnection("test_database");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StudentList = new ArrayList<AccountStudent>();
        try {
            pstmt = conn.prepareStatement("select * from StudentInfo ");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getObject("FaceFeature") != null) {
                    byte[] tempBytes = Base64Decoder(rs.getObject("FaceFeature").toString());
                    String s = new String(tempBytes);
                    StudentList.add(new AccountStudent(rs.getObject("Name").toString()
                            , rs.getObject("Account").toString()
                            , rs.getObject("Password").toString()
                            , (Boolean) rs.getObject("Sex")
                            , rs.getObject("Major").toString()
                            , rs.getObject("Phone").toString()
                            , rs.getObject("Email").toString()
                            , tempBytes));
                }
            }

            close(rs, conn);
            return StudentList.size();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(rs, conn);
        }
        return 0;
    }

    public AccountStudent getStudent(int index){
        return StudentList.get(index);
    }
}
