package com.zooplus.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.zooplus.base.TestBase;
import com.zooplus.payloads.PetJsonPayload;
import com.zooplus.petoperations.PetOperations;
import com.zooplus.pojo.PetPojo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * The PetTests class is having tests to perform on pet section of pet store api
 */
public class PetTests extends TestBase {

	private PetJsonPayload petJsonPayload;
	private PetPojo petPojo;
	private PetOperations petOperations;

	private Response response;
	private JsonPath jsonResponse;

	/**
	 *  Gets the Base URI before test suite runs
	 */
	@BeforeSuite
	public void setBaseUri() {

		RestAssured.baseURI = getBaseUri();

	}
	
	/**
	 *  Creates Json payload using Pojo class before each test
	 */
	@BeforeTest
	public void createPetPayLoad() {

		petJsonPayload = new PetJsonPayload();
		petPojo = petJsonPayload.createPetJsonpayload();
		petOperations = new PetOperations();

	}
	
	/**
	 *  Test creates the Pet and validates it with name by getting details and deletes it
	 */
	@Test
	public void createPetTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		response = petOperations.getPetById(petId);
		jsonResponse = new JsonPath(response.asString());
		String petName = jsonResponse.getString("name");
		Assert.assertEquals(petName, petPojo.getName());

		response = petOperations.deletePet(petId);
		jsonResponse = new JsonPath(response.asString());
		int code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);

	}
	
	
	/**
	 *  Test updates the Pet after creating it and validates it with name by getting details and deletes it
	 */
	@Test
	public void updatePetTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		petPojo.setName("Poodle");
		response = petOperations.updatePet(petPojo);
		jsonResponse = new JsonPath(response.asString());

		response = petOperations.getPetById(petId);
		jsonResponse = new JsonPath(response.asString());
		String petName = jsonResponse.getString("name");
		Assert.assertEquals(petName, petPojo.getName());

		response = petOperations.deletePet(petId);
		jsonResponse = new JsonPath(response.asString());
		int code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);

	}
	
	/**
	 *  Test trying to Update the Pet that deleted 
	 */
	@Test
	public void updateInvalidPetTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		response = petOperations.deletePet(petId);
		jsonResponse = new JsonPath(response.asString());
		int code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);
		
		petPojo.setName("Chow Chow");
		response = petOperations.updatePet(petPojo);
		Assert.assertEquals(response.getStatusCode(), 404);



	}
	
	/**
	 *  Test Updates the Pet after creating it using form data and deletes it
	 */
	@Test
	public void updatePetFormDataTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		petPojo.setName("Bulldog");
		petPojo.setStatus("sold");

		response = petOperations.updatePetFormData(petId, petPojo.getName(), petPojo.getStatus());
		jsonResponse = new JsonPath(response.asString());
		
		response = petOperations.getPetById(petId);
		jsonResponse = new JsonPath(response.asString());
		String petName = jsonResponse.getString("name");
		Assert.assertEquals(petName, petPojo.getName());

		response = petOperations.deletePet(petId); 
		jsonResponse=new JsonPath(response.asString()); 
		int code =Integer.parseInt(jsonResponse.getString("code")); 
		Assert.assertEquals(code,200);
		  
	}
	
	/**
	 *  Test Trying to Update the Pet using form data after deleting it
	 */
	@Test
	public void updateInvalidPetFormDataTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));
		
		response = petOperations.deletePet(petId); 
		jsonResponse=new JsonPath(response.asString()); 
		int code =Integer.parseInt(jsonResponse.getString("code")); 
		Assert.assertEquals(code,200);

		petPojo.setName("Dobermann");
		petPojo.setStatus("sold");

		response = petOperations.updatePetFormData(petId, petPojo.getName(), petPojo.getStatus());
		jsonResponse = new JsonPath(response.asString());

		response = petOperations.deletePet(petId);
		Assert.assertEquals(response.getStatusCode(), 404);
		  
	}
	

	/**
	 *  Test Gets the Pet data by using pet id and delete it
	 */
	@Test
	public void getPetByIdTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		response = petOperations.getPetById(petId);
		jsonResponse = new JsonPath(response.asString());
		String petName = jsonResponse.getString("name");
		Assert.assertEquals(petName, petPojo.getName());

		response = petOperations.deletePet(petId);
		jsonResponse = new JsonPath(response.asString());
		int code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);

	}
	
	/**
	 *  Test trying to get the pet data after deleting it
	 */
	@Test
	public void getInvalidPetByIdTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		response = petOperations.getPetById(petId);
		jsonResponse = new JsonPath(response.asString());
		String petName = jsonResponse.getString("name");
		Assert.assertEquals(petName, petPojo.getName());

		response = petOperations.deletePet(petId);
		jsonResponse = new JsonPath(response.asString());
		int code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);
		
		response = petOperations.getPetById(petId);
		Assert.assertEquals(response.getStatusCode(), 404);


	}
	
	/**
	 *  Test get the pets data based on status
	 */
	@Test
	public void getPetByStatusTest() {
		
		String[] status = {"available","pending"};
		List<String> statusList = Arrays.asList(status);

		response = petOperations.getPetByStatus(status);
		jsonResponse = new JsonPath(response.asString());
		
		// response of status is in list, compares the response list with original status list
		List<String> responseStatusList = jsonResponse.get("status");
		Assert.assertTrue(statusList.containsAll(responseStatusList));
	
	}
	
	/**
	 *  Test get the pets data based on tags
	 */
	@Test
	public void getPetByTagsTest() {
		
		String[] tags = {"dog"};
		List<String> tagsList = Arrays.asList(tags);
		
		response = petOperations.getPetByTags(tags);
		jsonResponse = new JsonPath(response.asString());
		
		// response of tags is in list of lists, converts it into list and compares the response tags  with original tag list
		List<List<String>> responseTags = jsonResponse.get("tags.name");
		List<String> tagsListResponse = new ArrayList<String>();
		responseTags.forEach(tagsListResponse::addAll);	
		
		Assert.assertTrue(tagsList.containsAll(tagsListResponse));
	
	}

	/**
	 *  delete pet test
	 */
	@Test
	public void deletePetTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		response = petOperations.getPetById(petId);
		jsonResponse = new JsonPath(response.asString());
		String petName = jsonResponse.getString("name");
		Assert.assertEquals(petName, petPojo.getName());

		response = petOperations.deletePet(petId);
		jsonResponse = new JsonPath(response.asString());
		int code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);

		response = petOperations.getPetById(petId);
		Assert.assertEquals(response.getStatusCode(), 404);

	}
	
	/**
	 *  delete the non existing pet test
	 */
	@Test
	public void deleteInvalidPetTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		response = petOperations.getPetById(petId);
		jsonResponse = new JsonPath(response.asString());
		String petName = jsonResponse.getString("name");
		Assert.assertEquals(petName, petPojo.getName());

		response = petOperations.deletePet(petId);
		jsonResponse = new JsonPath(response.asString());
		int code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);

		response = petOperations.getPetById(petId);
		Assert.assertEquals(response.getStatusCode(), 404);
		
		response = petOperations.deletePet(petId);
		Assert.assertEquals(response.getStatusCode(), 404);

	}
	
	/**
	 *  upload image to pet test
	 */
	@Test
	public void uploadImageTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		response = petOperations.uploadImage(petId, "dogImage", new File(baseDir + "/images/dog.jpeg"));
		jsonResponse = new JsonPath(response.asString());
		int code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);
		String message = jsonResponse.getString("message");
		Assert.assertTrue(message.contains("File uploaded"));

		response = petOperations.deletePet(petId);
		jsonResponse = new JsonPath(response.asString());
		code = Integer.parseInt(jsonResponse.getString("code"));
		Assert.assertEquals(code, 200);

	}


}
