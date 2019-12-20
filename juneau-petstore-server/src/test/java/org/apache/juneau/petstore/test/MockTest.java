package org.apache.juneau.petstore.test;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.juneau.petstore.App;
import org.apache.juneau.petstore.dto.PetStatus;
import org.apache.juneau.petstore.dto.Species;
import org.apache.juneau.petstore.rest.PetStoreResource;
import org.apache.juneau.rest.mock2.MockRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { App.class })
@SpringBootTest
public class MockTest {

	@Autowired
	PetStoreResource petStoreResource;
	MockRest petStoreRest;

	@Before
	public void setup() {

		petStoreRest = MockRest.create(petStoreResource).simpleJson().build();

	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Pets
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private String createTestPet() throws AssertionError, ServletException, IOException {
		String petId = petStoreRest
				.post("/pet", "{name:'Sunshine',tags:['nice'], price:100.0,species:'BIRD'}")
				.execute()
				.assertStatus(200)
				.getBodyAsString();

		return petId;
	}
	
	private void deleteTestPet(String petId) throws AssertionError, ServletException, IOException {
		petStoreRest
		.delete("/pet/" + petId)
		.execute()
		.assertStatus(200);

	}

	// Delete pet by Id

	@Test
	public void testDeletePet() throws Exception {
		String petId = createTestPet();
		petStoreRest
		.delete("/pet/" + petId)
		.execute()
		.assertStatus(200);
		
	}

	// Getting all pets

	@Test
	public void testGettingPets() throws Exception {
		String petId = createTestPet();
		petStoreRest
		.get("/pet")
		.execute()
		.assertStatus(200)
		.assertBody(
				"[{id:" + petId + ",species:'BIRD',name:'Sunshine',tags:['nice'],price:100.0,status:'AVAILABLE'}]");
		deleteTestPet(petId);
	}

	// Posting pet     

	@Test
	public void testPostPet() throws Exception {

		petStoreRest	
		.post("/pet", "{name:'Sunshine',tags:['nice'], price:100.0,species:'BIRD'}")
		.execute()
		.assertStatus(200)
		.assertBody("2");

		deleteTestPet("2");
	}

	
	// Find pet by Id

	
	  @Test public void testfindPet() throws Exception { 
		  String petId = createTestPet(); 
		  petStoreRest 
		  .get("/pet/" + petId) 
		  .execute()
	      .assertStatus(200) 
	      .assertBody("{id:"+petId+",species:'BIRD',name:'Sunshine',tags:['nice'],price:100.0,status:'AVAILABLE'}"
	  );
		  deleteTestPet(petId);
	  
	  }
	  
	  // Find pet by status
	 
	 @Test public void testfindPetByStatus() throws Exception {
		 String petId = createTestPet();
		 PetStatus [] status= {PetStatus.AVAILABLE};
		 
	  petStoreRest 
	  .request("GET", "/pet/findByStatus", status) 
	  .execute()
	  .assertStatus(200) 
	  .assertBody(
	  "[{id:"+petId+",species:'BIRD',name:'Sunshine',tags:[],price:100.0,status:'AVAILABLE'}]"
	  );
	  deleteTestPet(petId);
	  }
	  
	  // Find pet by tags
	
	  @Test public void testFindPetByTags() throws Exception {
		  String petId = createTestPet(); 
		  String[] tags= {"nice"};
		  
	  petStoreRest 
	  .request("GET", "/pet/findByTags", tags) 
	  .execute()
	  .assertStatus(200) 
	  .assertBody(
	 "[{id:"+petId+",species:'BIRD',name:'Sunshine',tags:['nice'],price:100.0,status:'AVAILABLE'}]"
	 );
	 
	  } 
	
	  
	 // Updating pet
	
	   @Test public void testUpdatePet() throws Exception { 
		   
	String petId = createTestPet(); 
	   petStoreRest 

	  .put("/pet/" + petId , "{id: "+petId+",name:'Daisy1',price:1000.0,species:'BIRD'tags:['nice'], status:'AVAILABLE' }")
	  .execute() 
	  .assertStatus(200); 
	  
	   deleteTestPet(petId);

	  }
	  
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Users
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	  private void deleteTestUser(String username) throws AssertionError, ServletException, IOException {
			
			petStoreRest
			.delete("/user/"+ username)
			.execute()
			.assertStatus(200);

		}
	  
	   private String createTestUser(String username) throws AssertionError, ServletException, IOException {

		 
		   petStoreRest
			.post("/user", "{username:"+username+",firstName: 'Tom',lastName: 'Simon', userStatus: 'ACTIVE'}")
		    .execute()
			.assertStatus(200);
		   
			return username;		
		} 
	   
	// Create user

		@Test
		public void testCreateUser() throws Exception {
			
			petStoreRest
			.post("/user", "{username:'catlover',firstName: 'Tom',lastName: 'Simon', userStatus: 'ACTIVE'}")
			.execute()
			.assertStatus(200);
				
			deleteTestUser("catlover");
		}
		
	// Delete user

		@Test
		public void testDeleteUser() throws Exception {
			String name="catlover1";
			createTestUser(name);
			
			petStoreRest
			.delete("/user/" + name)
			.execute()
			.assertStatus(200);
			
		}
				

	// Create list of users

	@Test
	public void testCreateUsers() throws Exception {

		petStoreRest
				.post("/user/createWithArray",
						"[{username:'billy',firstName: 'Billy',lastName: 'Bob', userStatus: 'ACTIVE'},"
								+ "{username:'peter',firstName: 'Peter',lastName: 'Adams', userStatus: 'ACTIVE'}]")
				.execute()
				.assertStatus(200);
		deleteTestUser("billy");
		deleteTestUser("peter");
	}

	// Getting all users

	@Test
	public void testGettingUsers() throws Exception {
		createTestUser("doglover");
		petStoreRest
		.get("/user")
		.execute()
		.assertStatus(200)
		.assertBody("[{username:'doglover',firstName: 'Tom',lastName: 'Simon', userStatus: 'ACTIVE'}]");
		deleteTestUser("doglover");
	}

	// Get user by user name

	@Test
	public void testFindUserByName() throws Exception {
		createTestUser("garfield");
		petStoreRest
		.get("/user/garfield")
		.execute()
		.assertStatus(200)
		.assertBody("{username:'garfield',firstName:  'Tom',lastName: 'Simon', userStatus: 'ACTIVE'}");
		deleteTestUser("garfield");
	}

	// Updating user

	@Test
	public void testUpdateUser() throws Exception {
		createTestUser("snoopy");
		petStoreRest
		.put("/user/snoopy", "{username:'snoopy',phone: '34562345'}")
		.execute()
		.assertStatus(200);
		deleteTestUser("snoopy");
	}

	

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Orders
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private void deleteTestOrder(String orderId) throws AssertionError, ServletException, IOException {
		petStoreRest
		.delete("/store/order/" + orderId)
		.execute()
		.assertStatus(200);
		
	}
	
	private String createTestOrder() throws AssertionError, ServletException, IOException {
		String petId = createTestPet();
		String orderId = petStoreRest
				.post("/store/order", "{petId:"+petId+" + ,username: 'catlover'}")				
				.execute()
				.assertStatus(200)
				.getBodyAsString();

		return orderId;
	}
	
	// Posting order

	@Test
	public void testPostOrder() throws Exception {
	
				petStoreRest
		.post("/store/order", "{petId:'1',username: 'snoopy'}")
		.execute()
		.assertStatus(200)
		.assertBody("4");
		deleteTestOrder("4");
	
		
	}
	
	// Getting all orders

	@Test
	public void testGettingOrders() throws Exception {
		
		String orderId = createTestOrder();
		petStoreRest
		.get("/store/order")
		.execute()
		.assertStatus(200)
		.assertBody("[{id:"+orderId+",petId:0,status:'PLACED'}]");
		
		deleteTestOrder(orderId);
	
	}

	// Find order by Id

	@Test
	public void testfindOrder() throws Exception {
		String orderId = createTestOrder();
		petStoreRest
		.get("/store/order/"+orderId)
		.execute()
		.assertStatus(200)
		.assertBody("{id:"+orderId+",petId:0,status:'PLACED'}");
		
		deleteTestOrder(orderId);
	}

	// Delete order by Id

	@Test
	public void testDeleteOrder() throws Exception {
		String orderId = createTestOrder();
		petStoreRest
		.delete("/store/order/"+orderId)
		.execute()
		.assertStatus(200);
		
	} 
}