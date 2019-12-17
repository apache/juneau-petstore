package org.apache.juneau.petstore.test;


import org.apache.juneau.petstore.App;
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

	// Posting pet

	@Test
	public void testPostPet() throws Exception {

		petStoreRest
		.post("/pet", "{name:'Sunshine',tags:['nice'], price:100.0,species:'BIRD'}")
		.execute()
		.assertStatus(200)
		.assertBody("1");
	}

	// Getting all pets

	@Test
	public void testGettingPets() throws Exception {

		petStoreRest
		.get("/pet")
		.execute()
		.assertStatus(200)
		.assertBody("[{id:1,species:'BIRD',name:'Sunshine',tags:['nice'],price:100.0,status:'AVAILABLE'}]");

	}

	// Find pet by Id

	@Test
	public void testfindPet() throws Exception {

		petStoreRest
		.get("/pet/1")
		.execute()
		.assertStatus(200)
		.assertBody("{id:1,species:'BIRD',name:'Sunshine',tags:['nice'],price:100.0,status:'AVAILABLE'}");

	}

	// Find pet by status

	@Test
	public void testfindPetByStatus() throws Exception {

		petStoreRest
		.request("GET", "/pet/findByStatus", "{'AVAILABLE'}")
		.execute()
		.assertStatus(200)
		.assertBody("[{id:1,species:'BIRD',name:'Sunshine',tags:[],price:100.0,status:'AVAILABLE'}]");

	}

	// Find pet by tags

	@Test
	public void testFindPetByTags() throws Exception {

		petStoreRest
		.request("GET", "/pet/findByTags", "{'nice'}")
		.execute()
		.assertStatus(200)
		.assertBody("[{id:1,species:'BIRD',name:'Sunshine',tags:['nice'],price:100.0,status:'AVAILABLE'}]");

	}
	// Updating pet

	@Test
	public void testUpdatePet() throws Exception {

		petStoreRest
		.put("/pet/1", "{species:'BIRD',name:'Daisy',tags:[],price:120.0,status:'AVAILABLE'}")
		.execute()
		.assertStatus(200).assertBody("OK");

	}

	// Delete pet by Id

	@Test
	public void testDeletePet() throws Exception {

		petStoreRest
		.delete("/pet/1")
		.execute()
		.assertStatus(200)
		.assertBody("OK");
	}

	
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Users
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// Create user

	@Test
	public void testCreateUser() throws Exception {

		petStoreRest
		.post("/user", "{username:'catlover',firstName: 'Tom',lastName: 'Simon', userStatus: 'ACTIVE'}")
		.execute()
		.assertStatus(200)
		.assertBody("OK");
	}

	// Create list of users

	@Test
	public void testCreateUsers() throws Exception {

		petStoreRest
		.post("/user/createWithArray",
				"[{username:'billy',firstName: 'Billy',lastName: 'Bob', userStatus: 'ACTIVE'},"
				+ "{username:'peter',firstName: 'Peter',lastName: 'Adams', userStatus: 'ACTIVE'}]")
		.execute()
		.assertStatus(200)
		.assertBody("OK");
	}

	// Getting all users

	@Test
	public void testGettingUsers() throws Exception {

		petStoreRest
		.get("/user")
		.execute()
		.assertStatus(200)
		.assertBody(
				"[{username:'catlover',firstName: 'Tom',lastName: 'Simon', userStatus: 'ACTIVE'},"
				+ "{username:'billy',firstName: 'Billy',lastName: 'Bob', userStatus: 'ACTIVE'},"
				+ "{username:'peter',firstName: 'Peter',lastName: 'Adams', userStatus: 'ACTIVE'}]");

	}

	// Get user by user name

	@Test
	public void testFindUserByName() throws Exception {

		petStoreRest
		.get("/user/catlover")
		.execute()
		.assertStatus(200)
		.assertBody("{id:1, username:'catlover',firstName: 'Tom',lastName: 'Simon', userStatus: 'ACTIVE'}");

	}

	// Updating user

	@Test
	public void testUpdateUser() throws Exception {

		petStoreRest
		.put("/user/catlover", "{id:1, username:'catlover',phone: '34562345'}")
		.execute()
		.assertStatus(200)
		.assertBody("OK");
	}

	// Delete user

	@Test
	public void testDeleteUser() throws Exception {

		petStoreRest
		.delete("/user/billy")
		.execute()
		.assertStatus(200)
		.assertBody("OK");
	}



//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Orders
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// Posting order

	@Test
	public void testPostOrder() throws Exception {

		petStoreRest
		.post("/store/order", "{petId:'1',username: 'catlover'}")
		.execute()
		.assertStatus(200)
		.assertBody("1");
	}
	// Getting all orders

	@Test
	public void testGettingOrders() throws Exception {

		petStoreRest
		.get("/store/order")
		.execute()
		.assertStatus(200)
		.assertBody("[{id:1,petId:1,status:'PLACED'}]");

	}

	// Find order by Id

	@Test
	public void testfindOrder() throws Exception {

		petStoreRest
		.get("/store/order/1")
		.execute()
		.assertStatus(200)
		.assertBody("{id:1,petId:1,status:'PLACED'}");

	}

	// Delete order by Id

	@Test
	public void testDeleteOrder() throws Exception {

		petStoreRest
		.delete("/store/order/1")
		.execute()
		.assertStatus(200)
		.assertBody("OK");
	}
}