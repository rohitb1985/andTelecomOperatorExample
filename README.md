# andTelecomOperatorExample
This is a sample code.

The application has been developed using Spring Boot and can be run as a Java Application. The main class is TelecomOperatorApplication.
Once we run the application, we can access the URL on localhost port 8080.

The rest operations added are as follows:

Get All Phone Numbers
URL: /v1/telecom/org/api/allPhoneNumbers
Request Method: GET
Description: Returns the list of phone numbers.

Get Customer Phone Numbers:
URL: /v1/telecom/org/api/customerPhoneNumbers/{customerId}
Request Method: GET
Description: Returns a list of phone numbers for the customer id. If the customer id is not found "Customer not found" message is returned.
Currently customer details are also being returned as the data is being generated dynamically so to use the Customer information and 
Phone Numbers we are returning all the customer data.

Activate Number
URL:/v1/telecom/org/api/activateNumber/{customerId}/{phoneNumber}
Request Method: POST
Description: Returns a String "Phone Number activated successfully" if the number is activated successfully.
If number is already active a message "Phone Number is already active" is returned.
The result can be verified by calling the Get Customer Phone Numbers service operation.

Unit Test cases have been added to test the rest service operations. Using Mockito we are mocking the response. 
We can also add test cases to test the end to end flow of the application.
