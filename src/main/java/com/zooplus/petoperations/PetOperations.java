package com.zooplus.petoperations;

import static io.restassured.RestAssured.given;

import java.io.File;

import com.zooplus.pojo.PetPojo;
import io.restassured.response.Response;

public class PetOperations {
	
	public Response addPet(PetPojo petPojo) {
		
		return given()
		  .header("content-type","application/json")
		  .body(petPojo)
		  .when()
		  .post("/v2/pet").then().assertThat().statusCode(200)
		  .extract().response();
	}
	
	public Response updatePet(PetPojo petPojo) {
		
		return given()
		  .header("content-type","application/json")
		  .body(petPojo)
		  .when()
		  .post("/v2/pet").then().assertThat().statusCode(200)
		  .extract().response();
	}
	
	public Response updatePetFormData(int petId, String name, String status) {
		
		return given()
		  .urlEncodingEnabled(true)
		  .param("name", name)
		  .param("status", status)
		  .pathParam("petId",petId)
		  .when()
		  .post("/v2/pet/{petId}").then()
		  .extract().response();
	}
	
	public Response getPetById(int petId) {
		
		return given()
				  .header("content-type","application/json")
				  .pathParam("petId",petId)
				  .when()
				  .get("v2/pet/{petId}").then()
				  .extract().response();
	}
	
	public Response getPetByStatus(String[] petStatus) {
		
		return given()
				  .urlEncodingEnabled(true)
				  .queryParam("status",petStatus)
				  .when()
				  .get("v2/pet/findByStatus").then()
				  .extract().response();
	}
	
	public Response deletePet(int petId) {
		
		return given()
				  .header("content-type","application/json")
				  .pathParam("petId",petId)
				  .when()
				  .delete("v2/pet/{petId}").then()
				  .extract().response();
	}
	
	public Response uploadImage(int petId, String additionalMetadata, File file) {
		
		return given()
		  .header("content-type","multipart/form-data")
		  .multiPart("additionalMetadata", additionalMetadata)
		  .multiPart(file)
		  .pathParam("petId",petId)
		  .when()
		  .post("/v2/pet/{petId}/uploadImage").then()
		  .extract().response();
	}



}
