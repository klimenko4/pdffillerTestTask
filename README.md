# Register user on https://www.pdffiller.com/
Get API KEY. See Documentation: https://docs.pdffiller.com/
Implement automated tests for next tasks:

#Task 1
1. GET /v1/document
1.1. Get list of documents in API response.
1.2. Login to user’s account on Web (Selenium).
1.3. Get list of documents in [All Documents] folder on Web (Forms Page).
1.4. Verify that names of documents (from API) are equal to names of documents (from Web).

#Task 2
2. POST /v1/document
2.1. Create new document from url (by api call)
2.2. Check that new document was created (verify in web)
Use Java, Maven, TestNG, Selenium.
