# Bitly Test Suite

## Setup
Before the Tests can be run, the User will need to be registered with Bitly, have an Access Token, and know their Group ID.

Registration can be done via this link:
https://bitly.com/a/sign_up

Once Registered, a User can generate an Access Token through the following:
```text
User Home Page > Profile Settings > Generic Access Token
```

A User's Group ID can be found out by making an initial request to create a Bitlink via the API (through Postman for example); the Group ID will be returned as part of the Response.

## Running The Tests
The Tests can be run in two ways:

### IDE
The first is through the IDE; in Intellij, this can be done by many ways, such as pressing the Green Arrow next to the Test, or right clicking the Test in the Project Structure.

If running from an IDE, the System Properties for AUTH_TOKEN and GROUP_ID need to be set to the User's Access Token and Group ID respectively. In Intellij for example, this can be done via:
```text
Run > Edit Configurations > VM Options > Add to the Input Box:
-DAUTH_TOKEN="Bearer {MY_AUTH_TOKEN}" -DGROUP_ID={MY_GROUP_ID}
```

### Command Line
The second way of running is via the Command Line; If you don't have Maven Installed, this needs to be installed on your machine, and the location set as an Environment Variable on your machine. In addition, you must be using a JDK (as opposed to JRE), and have your JAVA_HOME Environment Variable pointing to that location.

Once done, the tests can be run simply via the following command from the Project Root Directory:

```shell script
mvn -DAUTH_TOKEN="Bearer {MY_AUTH_TOKEN}" -DGROUP_ID={MY_GROUP_ID} test
```

To generate the Cluecumber Report, the following command should be used after the Maven Test is run:

```shell script
mvn cluecumber-report:reporting
```

The Report can be found in target/generated-report/index.html, and can be opened as a Webpage.

## Candidate Comments
- Testing the Sorted Bitlinks API was very difficult without having control over the App. To Test it effectively, I would have liked to be able to inject the number of clicks directly, so then I could assert that the Response contained what I expected. However, due to coming in from outside, to Test this fully I would have had to put in logic to go directly to the Web Page via the links which were generated previously within the Test, which would have made them enormously complex.

- Furthermore, this made it very difficult to remove the existing Bitlinks which had been created, which meant that negative tests for when no results are returned could not be tested in this circumstance; it may have been possible, but likely would have meant either going through another API, adding a dependency to this Test, or going directly to a Web Page to delete them manually, which would have been both massively complex, and time consuming for the Test.

- As it stands, the Test simply checks that the sorting was correctly applied to whatever was returned, which I deemed was enough.

- Some Negative Cases were unable to be replicated; Server Errors could not be received due to not having control over the Application. There was also 404 and 417 Errors, but there was no clear way to generate these. Attempts at generating a 404 included touching an endpoint which did not exist (which gave a 404, but not in the Schema Format listed in the Documentation), and searching for a Group which did not exist (which generated a 403, expected for Security concerns).