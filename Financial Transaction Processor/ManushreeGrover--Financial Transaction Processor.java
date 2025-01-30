package Snowscript;
import java.util.*;

public class FinancialTransactionProcessor {
	    private double balance;
	    private List<Transaction> transactions;

	    // Constructor to initialize balance and transaction list
	    public FinancialTransactionProcessor(double balance, List<Transaction> transactions) {
	        this.balance = balance;
	        this.transactions = transactions;
	    }

	    // Transaction class to hold individual transaction data
	    static class Transaction {
	        String transactionType;  // Type of transaction (deposit/withdrawal/transfer)
	        double amount;           // Transaction amount
	        int transactionHour;     // Hour of the transaction
	        double transactionFee;   // Fee associated with the transaction

	        public Transaction(String transactionType, double amount, int transactionHour, double transactionFee) {
	            this.transactionType = transactionType;
	            this.amount = amount;
	            this.transactionHour = transactionHour;
	            this.transactionFee = transactionFee;
	        }
	    }

	    // Method to calculate total fee and final balance after processing all transactions
	    public double[] processTransactions() {
	        double totalFee = 0;
	        double currentBalance = this.balance;
	        boolean transDone=false;// to check if any valid transaction done or not
	        
	        for (Transaction transaction : transactions) {
	            if (isValidTransaction(transaction, currentBalance)) {
	            	if(transaction.transactionType.equals("deposit")) {
	            		 currentBalance += transaction.amount ; // for depositing money
	            		 currentBalance -= transaction.transactionFee;
	            	}
	            	else {  //for transfer or withdraw
	                currentBalance -= (transaction.amount + transaction.transactionFee); // Deduct transaction amount and fee
	            	}
	            	
	                totalFee += transaction.transactionFee; // Add fee to total fee
	                transDone=true;
	            }
	        }

	        if (transDone) {
	            return new double[]{totalFee, currentBalance}; // Return total fee and the final balance
	        } else {
	            return new double[]{-1}; // Return -1 if no valid transaction was processed
	        }
	    }

	    // Method to check if a transaction is valid based on certain constraints
	    private boolean isValidTransaction(Transaction transaction, double currentBalance) {
	        // Add your conditions to check for transaction validity here, e.g., min balance check
	        // Example: Withdrawal should not exceed current balance
	   	 
	        if (transaction.transactionType.equals("withdrawal") && (transaction.amount + transaction.transactionFee) > currentBalance) {
	            return false;
	        }
	    
	        // Add more checks based on transaction type, time constraints, etc.
	    	if(!transaction.transactionType.equals("withdrawal") && (!transaction.transactionType.equals("transfer")) && (!transaction.transactionType.equals("deposit"))) {
	    		return false;
	    	} // Since Transaction types are restricted to "deposit", "withdrawal", and "transfer".
	    	
	    	if (transaction.transactionHour < 0 || transaction.transactionHour > 23) {
	    	        return false;
	        } // 0 <= transaction_hour <= 23
	    	 
	    	if (transaction.transactionFee < 0 || transaction.transactionFee > 10) {
	    	        return false;
	    	} //0 <= transaction_fee <= 10
	    	
	    	if (transaction.amount < 1 || transaction.amount > 50000) {
	    	        return false;
	    	} //1 <= amount <= 50000
	        
	        if(transaction.transactionType.equals("transfer") && ((transaction.amount + transaction.transactionFee)>currentBalance)) {
	        	return false;
	        } // tranfer amount should not be more than the balance
	        
	        return true;
	    }

	    public static void main(String[] args) {
	        // Initial balance
	        double balance = 5000;

	        // Sample transactions
	        List<Transaction> transactions = new ArrayList<>();
	        transactions.add(new Transaction("deposit", 2000, 10, 5));
	        transactions.add(new Transaction("withdrawal", 1500, 12, 3));
	        transactions.add(new Transaction("transfer", 1000, 15, 2));

	        // Create a processor instance
	        FinancialTransactionProcessor processor = new FinancialTransactionProcessor(balance, transactions);

	        // Process transactions and get the result
	        double[] result = processor.processTransactions();

	        // Print the result (total fee and final balance)
	        if (result.length == 1 && result[0] == -1) {
	            System.out.println("No valid transactions processed.");
	        } else {
	            System.out.println("Total Fee: " + result[0]);
	            System.out.println("Final Balance: " + result[1]);
	        }
	    }
}
