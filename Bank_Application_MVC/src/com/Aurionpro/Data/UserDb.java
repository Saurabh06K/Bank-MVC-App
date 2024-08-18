package com.Aurionpro.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Aurionpro.Model.Customer;
import com.Aurionpro.Model.CustomerAccount;
import com.Aurionpro.Model.Transaction;

public class UserDb {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;

	public void connectToDb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb", "root", "root");
			connection.createStatement();
			System.out.println("Database Connected Successfully!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

	public boolean checkEmailInDatabase(String email) {
		boolean exists = false;
		String query = "SELECT COUNT(*) FROM customers WHERE email = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(query)) {

			pstmt.setString(1, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next() && rs.getInt(1) > 0) {
					exists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handle exceptions appropriately in a real application
		}

		return exists;
	}

	public boolean addCustomer(String firstname, String lastname, String email, String password) {
		String sql = "INSERT INTO customers (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
		try {

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, firstname);
			preparedStatement.setString(2, lastname);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, password);

			int rows = preparedStatement.executeUpdate();
			System.out.println("Rows affected: " + rows); // Log how many rows were affected

			if (rows > 0) {
				System.out.println("Customer registered successfully: "); // Success log
				return true;
			} else {
				System.out.println("No rows affected, user not registered."); // Failure log
			}

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage()); // Log SQL exceptions
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				System.out.println("SQLException on close: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	public String validateAdmin(String email, String password) {
		String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return "admin";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String validateCustomers(String email, String password) {
		String sql = "SELECT * FROM customers WHERE email = ? AND password = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return "customer";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Customer getCustomerById(int customerId) {
		Customer customer = null;
		String query = "SELECT customerid, firstname, lastname, email FROM customers WHERE customerid = ?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, customerId);
			System.out.println("Executing query with customerId: " + customerId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int customerid = resultSet.getInt("customerid");
					String firstName = resultSet.getString("firstname");
					String lastName = resultSet.getString("lastname");
					String email = resultSet.getString("email");

					customer = new Customer(customerid, firstName, lastName, email);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}

	public boolean addBankAccount(int customerId, String accountNumber, double balance) {
		String sql = "INSERT INTO accounts (customerid, accountno, balance) VALUES (?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerId);
			preparedStatement.setString(2, accountNumber);
			preparedStatement.setDouble(3, balance);

			int rows = preparedStatement.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<CustomerAccount> getAllCustomers() {
		List<CustomerAccount> customers = new ArrayList<>();

		String query = "SELECT c.customerid, c.firstname, c.lastname, a.accountno, a.balance " + "FROM customers c "
				+ "JOIN accounts a ON c.customerid = a.customerid";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				CustomerAccount customer = new CustomerAccount();
				customer.setId(resultSet.getInt("customerid"));
				customer.setFirstName(resultSet.getString("firstname"));
				customer.setLastName(resultSet.getString("lastname"));
				customer.setAccountNumber(resultSet.getString("accountno"));
				customer.setBalance(resultSet.getDouble("balance"));

				customers.add(customer);
			}
			System.out.println("returned customer list is:" + customers);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customers;
	}

	public List<CustomerAccount> getCustomersByName(String name) throws SQLException {
		List<CustomerAccount> customers = new ArrayList<>();
		String query = "SELECT c.customerid, c.firstname, c.lastname, a.accountno, a.balance " + "FROM customers c "
				+ "JOIN accounts a ON c.customerid = a.customerid " + "WHERE c.firstname LIKE ? OR c.lastname LIKE ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			String searchPattern = "%" + name + "%";
			preparedStatement.setString(1, searchPattern);
			preparedStatement.setString(2, searchPattern);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int customerId = resultSet.getInt("customerid");
					String firstName = resultSet.getString("firstname");
					String lastName = resultSet.getString("lastname");
					String accountNumber = resultSet.getString("accountno");
					double balance = resultSet.getDouble("balance");

					customers.add(new CustomerAccount(customerId, firstName, lastName, accountNumber, balance));
				}
			}
		}
		return customers;
	}

	public void handleSameAccountTransaction(String accountNo, String transactionType, double amount)
			throws SQLException {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		}

		double currentBalance = getCurrentBalance(accountNo);

		if (transactionType.equalsIgnoreCase("credit")) {
			updateBalance(accountNo, amount);
			insertTransaction(accountNo, accountNo, transactionType, amount);
		} else if (transactionType.equalsIgnoreCase("debit")) {
			if (currentBalance < amount) {
				throw new IllegalArgumentException("Insufficient balance in account.");
			}
			updateBalance(accountNo, -amount);
			insertTransaction(accountNo, accountNo, transactionType, amount);
		}
	}

	public void handleDifferentAccountTransaction(String fromAccountNo, String toAccountNo, double amount)
			throws SQLException {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		}

		double fromAccountBalance = getCurrentBalance(fromAccountNo);

		if (fromAccountBalance < amount) {
			throw new IllegalArgumentException("Insufficient balance in account.");
		}

		// Start transaction
		try {
			connection.setAutoCommit(false); // Disable auto-commit

			// Debit the amount from the source account
			updateBalance(fromAccountNo, -amount);
			insertTransaction(fromAccountNo, toAccountNo, "Transfer", amount);

			// Credit the amount to the destination account
			updateBalance(toAccountNo, amount);
			insertTransaction(fromAccountNo, toAccountNo, "Transfer", amount);

			connection.commit(); // Commit transaction
		} catch (SQLException e) {
			connection.rollback(); // Rollback in case of error
			throw e;
		} finally {
			connection.setAutoCommit(true); // Restore auto-commit mode
		}
	}

	public void insertTransaction(String senderAccNo, String receiverAccNo, String transactionType, double amount)
			throws SQLException {
		String query = "INSERT INTO transactions (sender_AccountNo, receiver_AccountNo, tmode, amount, tdate) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, senderAccNo);
			statement.setString(2, receiverAccNo);
			statement.setString(3, transactionType);
			statement.setDouble(4, amount);
			statement.setDate(5, java.sql.Date.valueOf(LocalDate.now())); // Use current date

			statement.executeUpdate();
		}
	}

	public double getCurrentBalance(String accountNumber) throws SQLException {
		String query = "SELECT balance FROM accounts WHERE accountno = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, accountNumber);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getDouble("balance");
				} else {
					throw new SQLException("Account not found.");
				}
			}
		}
	}

	public void updateBalance(String accountNo, double amount) throws SQLException {
		String query = "UPDATE accounts SET balance = balance + ? WHERE accountno = ?";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setDouble(1, amount);
		preparedStatement.setString(2, accountNo);

		int updatedRows = preparedStatement.executeUpdate();

		if (updatedRows == 0) {
			throw new SQLException("Account not found or update failed.");
		}
	}

	public List<Transaction> getTransactionsForAccount(String accountNo) {
		List<Transaction> transactions = new ArrayList<>();
		String query = "SELECT t.sender_AccountNo, t.receiver_AccountNo, t.tmode, t.amount, t.tdate FROM transactions t JOIN accounts a ON t.sender_AccountNo = a.accountNo OR t.receiver_AccountNo = a.accountNo WHERE a.accountNo = ? ORDER BY \r\n"
				+ "    t.tdate DESC;";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, accountNo);
			System.out.println("Executing query with accountNo: " + accountNo);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Transaction transaction = new Transaction();
					transaction.setSenderAccNo(resultSet.getString("sender_AccountNo"));
					transaction.setReceiverAccNo(resultSet.getString("receiver_AccountNo"));
					transaction.setTypeOfTransaction(resultSet.getString("tmode"));
					transaction.setAmount(resultSet.getDouble("amount"));
					transaction.setDate(resultSet.getDate("tdate").toLocalDate()); // Convert to LocalDate

					System.out.println(transaction);
					transactions.add(transaction);

				}
			}
			if (transactions.isEmpty()) {
				System.out.println("No transactions found for accountNo: " + accountNo);
			} else {
				System.out.println("Transactions retrieved: " + transactions);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle exceptions accordingly
		}

		return transactions;
	}

	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		String query = "SELECT sender_AccountNo, receiver_AccountNo, tmode, amount, tdate FROM transactions ORDER BY \r\n"
				+ "    tdate DESC;";

		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Transaction transaction = new Transaction();
				transaction.setSenderAccNo(resultSet.getString("sender_AccountNo"));
				transaction.setReceiverAccNo(resultSet.getString("receiver_AccountNo"));
				transaction.setTypeOfTransaction(resultSet.getString("tmode"));
				transaction.setAmount(resultSet.getDouble("amount"));
				transaction.setDate(resultSet.getDate("tdate").toLocalDate()); // Convert to LocalDate

				transactions.add(transaction);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return transactions;
	}

	public List<Customer> getRecentCustomers() {
		List<Customer> recentCustomers = new ArrayList<>();
		String query = "SELECT customerid, email FROM customers ORDER BY customerid DESC LIMIT 3;";
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerid(rs.getInt("customerid"));
				customer.setEmail(rs.getString("email"));
				recentCustomers.add(customer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recentCustomers;
	}

	public boolean isAccountOwnedByEmail(String accountNo, String email) {
		String query = "SELECT COUNT(*) FROM accounts WHERE accountno = ? AND email = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, accountNo);
			preparedStatement.setString(2, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
