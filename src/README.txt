READ ME
CPS406-081
Kyle Galingan
Riddhima Garg
Shanaya John
Francis Porca
Roxie Reginold
Barbod Salehi


The following purchasing system is running on Oracle SDK17
using IntelliJ IDEA Community Edition 2021.3.1.

The program can be executed by the main method located in WelcomeStart.java.
\src\WelcomeStart.java


SYSTEM
Package containing our system and its user classes.

ORGANIZATION
Implementation of unique gui to the organization.


CREATEACCOUNT
Handles the sign-up page
CreateAccount accesses the database and writes a new user into the system.

EVENTINFO
Displays further event info.
Event page accesses database for event information.

EVENTPAGE
Event page contains list of clickable events that can be added to cart.
Items from the cart can be clicked to be removed.
Event page shows event information as well as subtotal information for order.
Event page accesses database for event information.

LOGIN
Takes in the username and password of a user, includes a sign-up button that leads to CreateAccount.
Login accesses database for user information.

PAYMENT
Takes in payment information that once all fields are successful it proceeds with processing the order.
Proceed includes buttons to go to previous window as well as proceed.

PROCESSING the order and updates the database for updated information such as event information.
Includes button to continue shopping and redirects to the event page.

WELCOMESTART
Homepage of the system, contains the main method.

DATABASES
Databases include EventDatabase - stores event information, UserDatabase - stores user information.

TESTS (in the TicketTango directory)
Package containing test cases for the System classes. (Right click on the Tests folder in Intellij and run the tests)


