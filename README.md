# petstore


## Automation test regression suite for pet store rest api

### The below functionalities covered in testcases.

* Adding pet to pet store
* Updating an exisitng pet
* Finding the pets by status
* Finding the pet by petId
* Updating pet in pet store using form data
* Uploading image to a existing pet
* Deleting the pet from pet store.


### Tools and Technologies used

* Java
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
	


