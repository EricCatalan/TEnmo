package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.view.ConsoleService;

import java.util.List;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String MAIN_MENU_OPTION_VIEW_TRANSFER = "View transfer by id";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_VIEW_TRANSFER, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private static final String PENDING_REQUEST_APPROVE = "Approve";
	private static final String PENDING_REQUEST_REJECT = "Reject";
	private static final String PENDING_REQUEST_DO_NOTHING = "Don't approve or reject";
	private static final String [] PENDING_REQUEST_OPTIONS = {PENDING_REQUEST_APPROVE,PENDING_REQUEST_REJECT, PENDING_REQUEST_DO_NOTHING};

    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private UserService userService;
    private TransferService transferService;
    private List<Account> accountList;
	private List<Transfer> transferList;

    

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new UserService(API_BASE_URL),  new TransferService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, UserService userService, TransferService transferService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.userService = userService;
		this.transferService = transferService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		transferList = transferService.listAllTransfers(currentUser.getToken());
		accountList = userService.listUserAccounts(currentUser.getToken());
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_TRANSFER.equals(choice)) {
				viewSingleTransfer();
			}else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		System.out.println("Your current balance is: $" + String.format("%.2f",(userService.getAccountBalance(currentUser.getToken()))));
	}
	private void viewSingleTransfer(){
    	int transferID= console.getUserInputInteger("Please enter the transfer ID to view details (0 to cancel)");
    	if(transferID == 0) {
    		mainMenu();
		}
    	if(!transferService.isTransferIdValid(transferID, transferList)) {
			System.out.println("This transfer id is not valid");
			mainMenu();
		}
    	Transfer transfer=  transferService.getTransferByID(transferID, transferList);
    	String fromUsername = userService.getUser(userService.getUserIDByAccountID(transfer.getAccountFromID(), userService.listUserAccounts(currentUser.getToken())), userService.listAllUsers(currentUser.getToken())).getUsername();
    	String toUsername = userService.getUser(userService.getUserIDByAccountID(transfer.getAccountToID(), userService.listUserAccounts(currentUser.getToken())), userService.listAllUsers(currentUser.getToken())).getUsername();
		System.out.println("----------------------------------");
		System.out.println("Transfer Details");
		System.out.println("----------------------------------");
		System.out.println("Id: " + transfer.getTransferID() );
		System.out.println("From: " + fromUsername);
		System.out.println("To: " + toUsername);
		if(transfer.getTransferTypeID().intValue() == 2 ){
		System.out.println("Type: Send");}
		else{
			System.out.println("Type: Request");
		}
		if(transfer.getTransferStatusID().intValue() == 1){
		System.out.println("Status: Pending");}
		else if(transfer.getTransferStatusID().intValue() == 2){
			System.out.println("Status: Approved");}
		else if(transfer.getTransferStatusID().intValue() == 3){
			System.out.println("Status: Rejected");}
		System.out.println("Amount: $" + String.format("%.2f", transfer.getAmount()) );
	}

	private void viewTransferHistory() {

		if(transferService.listUserTransfers(currentUser.getToken()).size() == 0) {
			System.out.println("You have no transfer history");
			mainMenu();
		}


		System.out.println("----------------------------------");
		System.out.println("Transfers");
		System.out.println("ID         From/To        Amount");
		System.out.println("----------------------------------");

		for(Transfer transfer: transferService.listUserTransfers(currentUser.getToken())) {
			int userId = userService.getUserIDByAccountID(transfer.getAccountToID(), userService.listUserAccounts(currentUser.getToken()));


			if (transfer.getTransferStatusID() == 2 && (transfer.getAccountFromID().intValue() == userService.getAccountByUserID(currentUser.getUser().getId(),accountList).getAccountID().intValue())) {
				System.out.println(transfer.getTransferID() +
						"       " + "To: " + userService.getUser(userId, userService.listOtherUsers(currentUser.getToken())).getUsername() +
						"       " + "$" + String.format("%.2f", transfer.getAmount()));
			}

			if (transfer.getTransferStatusID() == 2 && (transfer.getAccountToID().intValue() == userService.getAccountByUserID(currentUser.getUser().getId(),accountList).getAccountID().intValue())) {
				System.out.println(transfer.getTransferID() +
						"       " + "From: " + userService.getUser(userService.getUserIDByAccountID(transfer.getAccountFromID(), userService.listUserAccounts(currentUser.getToken())), userService.listOtherUsers(currentUser.getToken())).getUsername() +
						"       " + "$" + String.format("%.2f", transfer.getAmount()));
			}
		}
		
	}

	private void viewPendingRequests() {
		System.out.println("----------------------------------");
		System.out.println("Transfers");
		System.out.println("ID         To        Amount");
		System.out.println("----------------------------------");

		//changing listusertransfers to pending
		for(Transfer transfer: transferService.listPendingTransfers(currentUser.getToken())) {
			int userId = userService.getUserIDByAccountID(transfer.getAccountToID(), userService.listUserAccounts(currentUser.getToken()));

			if(transferService.listUserTransfers(currentUser.getToken()).size() == 0) {
				System.out.println("You have no pending transfers");
				mainMenu();
			}

			if (transfer.getTransferTypeID() == 1 && (transfer.getAccountFromID().intValue() == userService.getAccountByUserID(currentUser.getUser().getId(),accountList).getAccountID().intValue())) {
				System.out.println(transfer.getTransferID() +
						"       " + userService.getUser(userId, userService.listOtherUsers(currentUser.getToken())).getUsername() +
						"       " + "$" + String.format("%.2f", transfer.getAmount()));
			}

		}

		int transferID = console.getUserInputInteger("\nPlease enter transfer ID to approve/reject (0 to cancel)");
		if(transferID == 0) {
			mainMenu();
		}

		for(Transfer transfer: transferService.listPendingTransfers(currentUser.getToken())) {
			if(transferID == transfer.getTransferID().intValue()) {
				approveOrRejectPendingTransfer(transferID);
				mainMenu();
			}
		}
		System.out.println("\n You did not enter a valid transfer id");
		mainMenu();
	}

	private void approveOrRejectPendingTransfer(int transferID) {
		String choice = (String) console.getChoiceFromOptions(PENDING_REQUEST_OPTIONS);
    	if(PENDING_REQUEST_APPROVE.equals(choice)){
    		transferService.approveTransfer(transferService.getTransferByID(transferID,transferList), currentUser.getToken());

		}
    	else if(PENDING_REQUEST_REJECT.equals(choice)){
			transferService.rejectTransfer(transferService.getTransferByID(transferID,transferList), currentUser.getToken());
		}
    	else if (PENDING_REQUEST_DO_NOTHING.equals(choice)){
    		mainMenu();

		}else{
			System.out.println("You entered an invalid option");
			mainMenu();
		}
	}

	private void sendBucks() {
    	System.out.println("--------------------------------");
		System.out.println("Users");
		System.out.println("ID             Name");
		System.out.println("--------------------------------");
		for(User user: userService.listOtherUsers(currentUser.getToken())){
			System.out.println(user.getId()+"            "+user.getUsername() );
		}
		System.out.println("---------------");
		System.out.println("");
		System.out.println("");
		Integer sendingToID = console.getUserInputInteger("Enter ID of user you are sending to (0 to cancel)");
		if(sendingToID.intValue() == 0) {
			mainMenu();
		} if(!userService.isUserIdValid(sendingToID, userService.listOtherUsers(currentUser.getToken()))) {
			System.out.println("This user id is not valid. Please try again");
			mainMenu();
		}
		Double sendingAmount = console.getUserInputDouble("Enter amount");
		if(sendingAmount > userService.getAccountBalance(currentUser.getToken())) {
			System.out.println("Sorry, you do not have enough funds to execute this transaction");
			mainMenu();
		}
		Account accountFrom = userService.getAccountByUserID(currentUser.getUser().getId(), accountList);
		Account accountTo = userService.getAccountByUserID(sendingToID, accountList);
		Transfer transfer = new Transfer(2, 2, accountFrom.getAccountID(), accountTo.getAccountID(), sendingAmount);
		transferService.createTransfer(transfer, currentUser.getToken());
	}

	private void requestBucks() {
		System.out.println("--------------------------------");
		System.out.println("Users");
		System.out.println("ID             Name");
		System.out.println("--------------------------------");
		for(User user: userService.listOtherUsers(currentUser.getToken())){
			System.out.println(user.getId()+"            "+user.getUsername() );
		}
		System.out.println("---------------");
		System.out.println("");
		System.out.println("");
		Integer sendingToID = console.getUserInputInteger("Enter ID of user you are requesting to (0 to cancel)");
		if(sendingToID.intValue() == 0) {
			mainMenu();
		} if(!userService.isUserIdValid(sendingToID, userService.listOtherUsers(currentUser.getToken()))) {
			System.out.println("This user id is not valid. Please try again");
			mainMenu();
		}
		Double sendingAmount = console.getUserInputDouble("Enter amount");
		Account accountFrom = userService.getAccountByUserID(sendingToID, accountList);
		Account accountTo = userService.getAccountByUserID(currentUser.getUser().getId(), accountList);
		Transfer transfer = new Transfer(1, 1, accountFrom.getAccountID(), accountTo.getAccountID(), sendingAmount);
		transferService.requestTransfer(transfer, currentUser.getToken());
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
