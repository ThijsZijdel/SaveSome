package thijszijdel.savesome.database;

import java.sql.*;
import java.util.Enumeration;

/**
 *
 * @author Thijs Zijdel
 *
 */
public class JDBC {

        //Part 1
        private static final String DB_DEFAULT_DATABASE = "money_saver";
        private static final String DB_DEFAULT_SERVER_URL = "localhost:3306";
        private static final String DB_DEFAULT_ACCOUNT = "root";
        private static final String DB_DEFAULT_PASSWORD = "root";

        private final static String DB_DRIVER_URL = "com.mysql.jdbc.Driver";
        private final static String DB_DRIVER_PREFIX = "jdbc:mysql://";
        private final static String DB_DRIVER_PARAMETERS = "?useSSL=false";

        private Connection connection = null;

        // set for verbose logging of all queries
        private boolean verbose = true;

        // remembers the first error message on the connection
        private String errorMessage = null;

        // constructors
        public JDBC() {
            this(DB_DEFAULT_DATABASE, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
        }

        public JDBC(String dbName) {
            this(dbName, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
        }

        public JDBC(String dbName, String account, String password) {
            this(dbName, DB_DEFAULT_SERVER_URL, account, password);
        }

        public JDBC(String dbName, String serverURL, String account, String password) {
            try {
                // verify that a proper JDBC driver has been installed and linked
                if (!selectDriver(DB_DRIVER_URL)) {
                    return;
                }

                if (password == null) {
                    password = "";
                }

                // establish a connection to a named database on a specified server
                String connStr = DB_DRIVER_PREFIX + serverURL + "/" + dbName + DB_DRIVER_PARAMETERS;
                log("Connecting " + connStr);
                this.connection = DriverManager.getConnection(connStr, account, password);

            } catch (SQLException eSQL) {
                error(eSQL);
                this.close();
            }
        }

        public final void close() {

            if (this.connection == null) {
                // db has been closed earlier already
                return;
            }
            try {
                this.connection.close();
                this.connection = null;
                this.log("IData base has been closed");
            } catch (SQLException eSQL) {
                error(eSQL);
            }
        }

        /**
         * *
         * elects proper loading of the named driver for database connections. This
         * is relevant if there are multiple drivers installed that match the JDBC
         * type
         *
         * @param driverName the name of the driver to be activated.
         * @return indicates whether a suitable driver is available
         */
        private Boolean selectDriver(String driverName) {
            try {
                Class.forName(driverName);
                // Put all non-prefered drivers to the end, such that driver selection hits the first
                Enumeration<Driver> drivers = DriverManager.getDrivers();
                while (drivers.hasMoreElements()) {
                    Driver d = drivers.nextElement();
                    if (!d.getClass().getName().equals(driverName)) {   // move the driver to the end of the list
                        DriverManager.deregisterDriver(d);
                        DriverManager.registerDriver(d);
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                error(ex);
                return false;
            }
            return true;
        }

        /**
         * *
         * Executes a DDL, DML or DCL query that does not yield a result set
         *
         * @param sql the full sql text of the query.
         * @return the number of rows that have been impacted, -1 on error
         */
        public int executeUpdateQuery(String sql) {
            try {
                Statement s = this.connection.createStatement();
                log(sql);
                int n = s.executeUpdate(sql);
                s.close();
                return (n);
            } catch (SQLException ex) {
                // handle exception
                error(ex);
                return -1;
            }
        }

        /**
         * *
         * Executes an SQL query that yields a ResultSet with the outcome of the
         * query. This outcome may be a single row with a single column in case of a
         * scalar outcome.
         *
         * @param sql the full sql text of the query.
         * @return a ResultSet object that can iterate along all rows
         * @throws SQLException
         */
        public ResultSet executeResultSetQuery(String sql) throws SQLException {
            Statement s = this.connection.createStatement();
            log(sql);
            ResultSet rs = s.executeQuery(sql);
            // cannot close the statement, because that also closes the resultset
            return rs;
        }

        /**
         * *
         * Executes query that is expected to return a single String value
         *
         * @param sql the full sql text of the query.
         * @return the string result, null if no result or error
         */
        public String executeStringQuery(String sql) {
            String result = null;
            try {
                Statement s = this.connection.createStatement();
                log(sql);
                ResultSet rs = s.executeQuery(sql);
                if (rs.next()) {
                    result = rs.getString(1);
                }
                // close both statement and resultset
                s.close();
            } catch (SQLException ex) {
                error(ex);
            }

            return result;
        }

        /**
         * *
         * Executes query that is expected to return a list of String values
         *
         * @param sql the full sql text of the query.
         * @return the string result, null if no result or error
         */
        public String executeStringListQuery(String sql) {
            String result = null;
            try {
                Statement s = this.connection.createStatement();
                log(sql);
                ResultSet rs = s.executeQuery(sql);
                if (rs.next()) {
                    result = rs.getString(1);
                }
                // close both statement and resultset
                s.close();
            } catch (SQLException ex) {
                error(ex);
            }

            return result;
        }

        /**
         * *
         * echoes a message on the system console, if run in verbose mode
         *
         * @param message
         */
        public void log(String message) {
            if (isVerbose()) {
                System.out.println("MyJDBC: " + message);
            }
        }

        /**
         * *
         * echoes an exception and its stack trace on the system console. remembers
         * the message of the first error that occurs for later reference. closes
         * the connection such that no further operations are possible.
         *
         * @param e
         */
        public final void error(Exception e) {
            String msg = "MyJDBC-" + e.getClass().getName() + ": " + e.getMessage();

            // capture the message of the first error of the connection
            if (this.errorMessage == null) {
                this.errorMessage = msg;
            }
            System.out.println(msg);
            e.printStackTrace();

            // if an error occurred, close the connection to prevent further operations
            this.close();
        }

        public boolean isVerbose() {
            return verbose;
        }

        public void setVerbose(boolean verbose) {
            this.verbose = verbose;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

//    /*
//
//    Part 2
//
//     */
//        /**
//         * *
//         * Executes an SQL query that yields a ResultSet with a user if the filled
//         * in fields match a id and password in the user table. Else returns an
//         * ResultSet
//         *
//         * Using a Prepared Statement. Prepared statements are used against SQL
//         * injection
//         *
//         *
//         * @param id the username from the user.
//         * @param password the password that the user entered
//         * @return a ResultSet object (User)
//         */
//        public User executeLogInQuery(String id, String password) throws SQLException {
//            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM employee  "
//                    + "WHERE employeeId = ? AND password = ?");
//            preparedStatement.setString(1, id);
//            preparedStatement.setString(2, password);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            User currentUser = null;
//
//            while (resultSet.next()) {
//                String employeeId = resultSet.getString("employeeId");
//                String firstname = resultSet.getString("firstname");
//                String lastname = resultSet.getString("lastname");
//                String location = resultSet.getString("location");
//                String role = resultSet.getString("role");
//                String status = resultSet.getString("status");
//                currentUser = new User(employeeId, lastname, firstname, location, role, status);
//
//            }
//
//            return currentUser;
//        }
//
//        public ResultSet executeSearchQuery(String searchInput) throws SQLException {
//
//            PreparedStatement preparedStatement = this.connection.prepareStatement(
//                    "SELECT * "
//                            + "FROM employee WHERE employeeId LIKE ? ESCAPE '!' OR firstname "
//                            + "LIKE ? ESCAPE '!' OR lastname LIKE ? ESCAPE '!' OR location LIKE ? "
//                            + "ESCAPE '!' OR status LIKE ? ESCAPE '!' OR role LIKE ? ESCAPE '!'");
//            String search = searchInput
//                    .replace("!", "!!")
//                    .replace("%", "!%")
//                    .replace("_", "!_")
//                    .replace("[", "![");
//
//            preparedStatement.setString(1, "%" + search + "%");
//            preparedStatement.setString(2, "%" + search + "%");
//            preparedStatement.setString(3, "%" + search + "%");
//            preparedStatement.setString(4, "%" + search + "%");
//            preparedStatement.setString(5, "%" + search + "%");
//            preparedStatement.setString(6, "%" + search + "%");
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            return resultSet;
//        }
//
//        public int executePasswordUpdateQuery(String id, String newPassword) throws SQLException {
//
//            try {
//
//                PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE employee SET "
//                        + "password = ? WHERE employeeId = ?");
//
//                preparedStatement.setString(1, newPassword);
//                preparedStatement.setString(2, id);
//
//                int returnValue = preparedStatement.executeUpdate();
//                preparedStatement.close();
//                System.out.println(returnValue);
//
//                return returnValue;
//            } catch (SQLException ex) {
//                // handle exception
//                error(ex);
//                return -1;
//            }
//
//        }
//
//        public int executeUserUpdateQuery(User user) throws SQLException {
//            String firstname = user.getFirstName();
//            String lastname = user.getLastName();
//            String location = user.getLocation();
//            String role = user.getRole();
//            String status = user.getStatus();
//            String employeeID = user.getId();
//
//            System.out.println(firstname + lastname + location + role + status + employeeID);
//            try {
//
//                PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE employee SET "
//                        + "firstname = ?, lastname = ?, "
//                        + "location = ?, "
//                        + "role = ?, status = ? WHERE employeeId = ?");
//
//                preparedStatement.setString(1, firstname);
//                preparedStatement.setString(2, lastname);
//                preparedStatement.setString(3, location);
//                preparedStatement.setString(4, role);
//                preparedStatement.setString(5, status);
//                preparedStatement.setString(6, employeeID);
//
//                int returnValue = preparedStatement.executeUpdate();
//                preparedStatement.close();
//                System.out.println(returnValue);
//
//                return returnValue;
//            } catch (SQLException ex) {
//                // handle exception
//                error(ex);
//                return -1;
//            }
//
//        }
//
//        // -------------------------------------------------------------------------
//
//        /**
//         * @author Thijs Zijdel - 500782165
//         *
//         * Execute update query for editing a fields of a luggage.
//         * Note: Prepared statement so the db is be protected against SQL Injection.
//         *
//         * @param table table of the field that will be updated
//         * @param field field that need to be changed
//         * @param value new value of the field
//         * @param registrationNr of the luggage that will changed
//         * @throws java.sql.SQLException updating data in the db
//         **/
//        public void executeUpdateLuggageFieldQuery(
//                String table,
//                String field,
//                String value,
//                String registrationNr) throws SQLException {
//            //try to create en execute an prepared statment
//            try (
//                    PreparedStatement preparedStatement = this.connection.prepareStatement(
//                            "UPDATE `"+table+"` SET  "
//                                    + " "+field+" = ? "
//                                    + "WHERE `registrationNr`= ? ;")) {
//                //initializing the preparedstatement
//                preparedStatement.setString(1, value);
//                preparedStatement.setString(2, registrationNr);
//
//                //execute the prepared statement
//                preparedStatement.executeUpdate();
//
//                //close preparedStatement
//                preparedStatement.close();
//            }
//        }
//        /**
//         * @author Thijs Zijdel - 500782165
//         *
//         * Execute update query for editing the fields of a passenger.
//         * Note: Prepared statement so the db is be protected against SQL Injection.
//         *
//         * @param table table of the field that will be updated
//         * @param field field that need to be changed
//         * @param where statement / field that will be checked on the @param id
//         * @param value new value of the field
//         * @param id of the row that will changed
//         * @throws java.sql.SQLException updating data in the db
//         **/
//        public void executeUpdateQueryWhere(
//                String table,
//                String field,
//                String where,
//                String value,
//                String id) throws SQLException {
//            //try to create en execute an prepared statment
//            try (
//                    PreparedStatement preparedStatement = this.connection.prepareStatement(
//                            "UPDATE `"+table+"` SET  "
//                                    + " "+field+" = ? "
//                                    + "WHERE `"+where+"`= ? ;")) {
//                //initializing the preparedstatement
//                preparedStatement.setString(1, value);
//                preparedStatement.setString(2, id);
//
//                //execute the prepared statement
//                preparedStatement.executeUpdate();
//
//                //close preparedStatement
//                preparedStatement.close();
//            }
//        }
//        /**
//         * @author Thijs Zijdel - 500782165
//         *
//         * Execute update query for editing the fields of a passenger.
//         * Note: Prepared statement so the db is be protected against SQL Injection.
//         *
//         *
//         * //All the parameters are the values of the new fields
//         * @param name
//         * @param address
//         * @param place
//         * @param postalcode
//         * @param country
//         * @param email
//         * @param phone
//         *
//         * @param passengerId -> the passenger where the values are set
//         * @throws java.sql.SQLException because there will be a SQL query executed
//         **/
//        public void executeUpdatePassengerQuery(
//                String name,
//                String address,
//                String place,
//                String postalcode,
//                String country,
//                String email,
//                String phone,
//                String passengerId) throws SQLException {
//            //try to create en execute an prepared statment
//            try (
//                    PreparedStatement preparedStatement =
//                            this.connection.prepareStatement(
//                                    "UPDATE `passenger` SET  "
//                                            + " name = ? ,"
//                                            + " address = ? ,"
//                                            + " place = ? ,"
//                                            + " postalcode = ? ,"
//                                            + " country = ? ,"
//                                            + " email = ? ,"
//                                            + " phone = ? "
//                                            + "WHERE `passengerId`= ? ;")) {
//                //initializing the preparedstatement
//                preparedStatement.setString(1, name);
//                preparedStatement.setString(2, address);
//                preparedStatement.setString(3, place);
//                preparedStatement.setString(4, postalcode);
//                preparedStatement.setString(5, country);
//                preparedStatement.setString(6, email);
//                preparedStatement.setString(7, phone);
//                preparedStatement.setString(8, passengerId);
//
//                //execute the prepared statement
//                preparedStatement.executeUpdate();
//
//                //close preparedStatement
//                preparedStatement.close();
//            }
//        }
//
//        /**
//         * @author Thijs Zijdel - 500782165
//         *
//         * Execute update query for editing almost all fields of a luggage.
//         * Note: Prepared statement so the db is be protected against SQL Injection.
//         *
//         * //All the parameters are the values of the new fields
//         * @param tag
//         * @param brand
//         * @param size
//         * @param signatures
//         * @param id
//         *
//         *
//         * @param luggageTable  should be found luggage or lost luggage
//         * @throws java.sql.SQLException because there will be data updated in the Db
//         **/
//        public void executeUpdateLuggageQuery(
//                String tag,
//                String brand,
//                String size,
//                String signatures,
//                String id,
//                String luggageTable) throws SQLException {
//            //try to create en execute an prepared statment
//            try (
//                    PreparedStatement preparedStatement =
//                            this.connection.prepareStatement(
//                                    "UPDATE `"+luggageTable+"` SET  "
//                                            + " luggageTag = ? ,"
//                                            + " brand = ? ,"
//                                            + " size = ? ,"
//                                            + " otherCharacteristics = ? "
//                                            + "WHERE `registrationNr` =  ?  ;")) {
//                //initializing the preparedstatement
//                preparedStatement.setString(1, tag);
//                preparedStatement.setString(2, brand);
//                preparedStatement.setString(3, size);
//                preparedStatement.setString(4, signatures);
//                preparedStatement.setString(5, id);
//
//                //execute the prepared statement
//                preparedStatement.executeUpdate();
//
//                //close preparedStatement
//                preparedStatement.close();
//            }
//        }
//
//        /**
//         * @author Thijs Zijdel - 500782165
//         *
//         * Execute insert query for inserting the fields of a match. Note: this is
//         * an prepared statement so the db will be protected against SQL Injection.
//         *
//         *
//         * //All the parameters are the values of the new fields
//         * @param matchedId            //generated id for the match itself  PKey
//         * @param foundRegistrationNr  //id of the matched found luggage    FKey
//         * @param lostRegistrationNr   //id of the matched lost luggage     FKey
//         * @param employeeId           //id of the employee that confirmed the match
//         * @param dateMatched          //current date
//         *
//         * @throws java.sql.SQLException because there will be a SQL query executed
//         **/
//        public void executeInsertMatchQuery(
//                String matchedId,
//                String foundRegistrationNr,
//                String lostRegistrationNr,
//                String employeeId,
//                String dateMatched) throws SQLException {
//            try (
//                    PreparedStatement preparedStatement =
//                            this.connection.prepareStatement(
//                                    " INSERT INTO `matched` "
//                                            + " (`matchedId`, `foundluggage`, `lostluggage`, `employeeId`, `dateMatched`, `delivery`) "
//                                            + " VALUES ( ? , ? , ? , ? , ? , '' ) ")){
//                //initializing the preparedstatement
//                preparedStatement.setString(1, matchedId);
//                preparedStatement.setString(2, foundRegistrationNr);
//                preparedStatement.setString(3, lostRegistrationNr);
//                preparedStatement.setString(4, employeeId);
//                preparedStatement.setString(5, dateMatched);
//
//                //execute the prepared statement
//                preparedStatement.executeUpdate();
//
//                //close preparedStatement
//                preparedStatement.close();
//            }
//        }

        // -------------------------------------------------------------------------





}
