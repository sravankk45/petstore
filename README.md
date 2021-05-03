# petstore


## Automation test regression suite for pet store rest api

### The below functionalities covered in testcases.

* Adding new pet to pet store
* Updating an exisitng pet
* Updating a non existing pet(Negative scenario, after deleting the pet trying to update)
* Updating a non exisitng pet in pet store using form data (Negative scenario, after deleting the pet trying to update)
* Finding the pet by petId
* Finding the non existing pet by petId (Negative scenario, after deleting the pet trying to find the pet)
* Finding the pets by status
* Finding the pets by Tags
* Deleting the pet from pet store
* Deleting the non existing pet from pet store. (Negative scenario, after deleting the pet trying to delete the pet)
* Uploading image to a existing pet


* Note: 
	* /pet/{petId}/uploadImage endpoint request is getting successful even with the petId does not exist and there is no expected status code(404) given in swagger.
	* /pet end point for updating pet request is getting succesful even with  the petId doesnt not exist and a new petId is getting created.



### Tools and Technologies used

* Java (1.8 or higher)
* TestNG
* RestAssured
* Maven
* Extent Report

### Configuration/setup to be done to run the project

* The below details are configured in config.properies file(available in petstore\src\main\java\com\zooplus\config\config.properties)
	* baseURI for pet store rest api

* TestBase class (com.zooplus.base package) is base class for all tests and it provides configurations like baseURI and base directory of project to tests
* All test classes (com.zooplus.tests package) extends TestBase class to inherit base configuration data.
* Json payload for Pet(creation and updation) is created using Pojo java class pattern with getter and setter methods.
	* com.zooplus.pojo.PetPojo class to represent the nested JSON payload for Pet in API requests

### To Run Tests
* testng.xml file is configured with tests and extent reports
* run the tests by executing the testng.xml file as testng suite
* after tests run, open report under test-output/PetStoreTestReport.html to see interactuve HTML report on test execution status.


