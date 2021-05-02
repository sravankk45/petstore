package com.zooplus.payloads;

import java.util.ArrayList;
import java.util.List;

import com.zooplus.pojo.PetCategory;
import com.zooplus.pojo.PetPojo;
import com.zooplus.pojo.PetTag;



public class PetJsonPayload {
	
	private PetPojo petPojo;
	private PetCategory petCategory;
	private PetTag petTag;
	private List<PetTag> petTags;
	private List<String> photoUrls;
	
	public PetPojo createPetJsonpayload() {
		
		petCategory = new PetCategory();
		petTag = new PetTag();
		petTags = new ArrayList<PetTag>();
		photoUrls = new ArrayList<String>();
		petPojo = new PetPojo();
		
		petCategory.setId(123);
		petCategory.setName("Dogs");
		
		petTag.setId(456);
		petTag.setName("Handy");
		petTags.add(petTag);
		
		photoUrls.add("URL1");
		
		petPojo.setId(789);
		petPojo.setName("Dog");
		petPojo.setCategory(petCategory);
		petPojo.setTags(petTags);
		petPojo.setStatus("Available");
		petPojo.setPhotoUrls(photoUrls);
		
		return petPojo;
	}

}
