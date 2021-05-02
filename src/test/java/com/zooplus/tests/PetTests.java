package com.zooplus.tests;

import java.io.File;
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

public class PetTests extends TestBase {

	private PetJsonPayload petJsonPayload;
	private PetPojo petPojo;
	private PetOperations petOperations;

	private Response response;
	private JsonPath jsonResponse;

	@BeforeSuite
	public void setBaseUri() {

		RestAssured.baseURI = getBaseUri();

	}

	@BeforeTest
	public void createPetPayLoad() {

		petJsonPayload = new PetJsonPayload();
		petPojo = petJsonPayload.createPetJsonpayload();
		petOperations = new PetOperations();

	}

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

	@Test
	public void updatePetFormDataTest() {

		response = petOperations.addPet(petPojo);
		jsonResponse = new JsonPath(response.asString());
		int petId = Integer.parseInt(jsonResponse.getString("id"));

		petPojo.setName("Bulldog");
		petPojo.setStatus("sold");

		response = petOperations.updatePetFormData(petId, petPojo.getName(), petPojo.getStatus());
		jsonResponse = new JsonPath(response.asString());

		response = petOperations.deletePet(petId); jsonResponse=new
		JsonPath(response.asString()); 
		int code =Integer.parseInt(jsonResponse.getString("code")); 
		Assert.assertEquals(code,200);
		  
	}
	
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
	
	@Test
	public void getPetByStatusTest() {
		
		String[] status = {"notinStock","available","pending"};
		List<String> statusList = Arrays.asList(status);

		response = petOperations.getPetByStatus(status);
		jsonResponse = new JsonPath(response.asString());
		
		List<String> responseStatusList = jsonResponse.get("status");
		Assert.assertTrue(statusList.containsAll(responseStatusList));
	
	}

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
