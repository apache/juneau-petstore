package org.apache.juneau.petstore.test;

import org.apache.juneau.petstore.App;
import org.apache.juneau.petstore.dto.*;
import org.apache.juneau.petstore.rest.PetStoreResource;
import org.apache.juneau.rest.client.*;
import org.apache.juneau.rest.mock.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("javadoc")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { App.class })
@SpringBootTest
public class MockTest {

	@Autowired
	PetStoreResource petStoreResource;

	private RestClient petStoreRest;

	@Before
	public void setup() {
		// Wrap our resource in a MockRest object for testing.
		petStoreRest = MockRestClient.create(petStoreResource).simpleJson().build();
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Pets
	// -----------------------------------------------------------------------------------------------------------------

	private int createTestPet() throws Exception {

		int petId = petStoreRest
			.post("/pet", new CreatePet().name("Sunshine").price(100f).species(Species.BIRD).tags("nice"))
			.run()
			.assertStatus().asCode().is(200)
			.getContent().as(int.class);

		return petId;
	}

	private void deleteTestPets() throws Exception {
		petStoreRest
			.delete("/pets")
			.complete()  // Use complete() because we're not consuming response.
			.assertStatus().asCode().is(200);
	}

	// Delete pet by Id
	@Test
	public void testDeletePet() throws Exception {

		int petId = createTestPet();
		petStoreRest
			.delete("/pet/" + petId)
			.complete()
			.assertStatus().asCode().is(200);
	}

	// Getting all pets
	@Test
	public void testGettingPets() throws Exception {

		int petId = createTestPet();
		petStoreRest
			.get("/pet")
			.run()
			.assertStatus().asCode().is(200)
			.assertContent().is("[{id:" + petId + ",species:'BIRD',name:'Sunshine',price:100.0,status:'AVAILABLE'}]");

		deleteTestPets();
	}

	// Posting pet
	@Test
	public void testPostPet() throws Exception {

		petStoreRest
			.post("/pet", new CreatePet().name("Sunshine").price(100f).species(Species.BIRD).tags("nice"))
			.complete()
			.assertStatus().asCode().is(200);

		deleteTestPets();
	}

	// Find pet by Id
	@Test
	public void testfindPet() throws Exception {

		int petId = createTestPet();
		petStoreRest
			.get("/pet/" + petId)
			.run()
			.assertCode().is(200)
			.assertContent().is("{id:" + petId + ",species:'BIRD',name:'Sunshine',tags:['nice'],price:100.0,status:'AVAILABLE'}");

		deleteTestPets();

	}

	// Find pet by status
	@Test
	public void testFindPetByStatus() throws Exception {

		int petId = createTestPet();
		petStoreRest
			.get("/pet/findByStatus?status=AVAILABLE")
			.run()
			.assertCode().is(200)
			.assertContent().is("[{id:" + petId + ",species:'BIRD',name:'Sunshine',tags:['nice'],price:100.0,status:'AVAILABLE'}]");

		deleteTestPets();
	}

	// Updating pet

	@Test
	public void testUpdatePet() throws Exception {

		int petId = createTestPet();
		petStoreRest
			.put(
				"/pet/" + petId,
				new UpdatePet().id(petId).name("Daisy1").price(1000f).species(Species.BIRD).status(PetStatus.AVAILABLE).tags("nice")
			)
			.complete()
			.assertCode().is(200);

		deleteTestPets();
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Users
	// -----------------------------------------------------------------------------------------------------------------

	private void deleteTestUsers() throws Exception {
		petStoreRest
			.delete("/users/")
			.complete()
			.assertCode().is(200);
	}

	private String createTestUser(String username) throws Exception {
		petStoreRest
			.post(
				"/user",
				new User().username(username).firstName("Tom").lastName("Simon").userStatus(UserStatus.ACTIVE)
			)
			.complete()
			.assertCode().is(200);

		return username;
	}

	// Create user
	@Test
	public void testCreateUser() throws Exception {
		petStoreRest
			.post(
				"/user",
				new User().username("catlover").firstName("Tom").lastName("Simon").userStatus(UserStatus.ACTIVE)
			)
			.complete()
			.assertCode().is(200);

		deleteTestUsers();
	}

	// Delete user
	@Test
	public void testDeleteUser() throws Exception {

		petStoreRest
			.delete("/user/" + "catlover1")
			.complete()
			.assertCode().is(200);

	}

	// Create list of users
	@Test
	public void testCreateUsers() throws Exception {

		petStoreRest
			.post(
				"/user/createWithArray",
				new User[] {
					new User().username("billy").firstName("Billy").lastName("Bob").userStatus(UserStatus.ACTIVE),
					new User().username("peter").firstName("Peter").lastName("Adams").userStatus(UserStatus.ACTIVE)
				}
			)
			.complete()
			.assertCode().is(200);

		deleteTestUsers();
	}

	// Getting all users
	@Test
	public void testGettingUsers() throws Exception {

		createTestUser("doglover");
		petStoreRest
			.get("/user")
			.run()
			.assertCode().is(200)
			.assertContent().is("[{username:'doglover',firstName:'Tom',lastName:'Simon',userStatus:'ACTIVE'}]");

		deleteTestUsers();
	}

	// Get user by user name
	@Test
	public void testFindUserByName() throws Exception {

		createTestUser("garfield");
		petStoreRest
			.get("/user/garfield")
			.run()
			.assertCode().is(200)
			.assertContent().is("{username:'garfield',firstName:'Tom',lastName:'Simon',userStatus:'ACTIVE'}");

		deleteTestUsers();
	}

	// Updating user
	@Test
	public void testUpdateUser() throws Exception {

		createTestUser("snoopy");
		petStoreRest
			.put(
				"/user/snoopy",
				new User().username("snoopy").phone("34562345")
			)
			.complete()
			.assertCode().is(200);

		deleteTestUsers();
	}

	// -----------------------------------------------------------------------------------------------------------------
	// Orders
	// -----------------------------------------------------------------------------------------------------------------

	private void deleteTestOrders() throws Exception {
		petStoreRest
			.delete("/orders")
			.complete()
			.assertCode().is(200);
	}

	private int createTestOrder() throws Exception {

		int petId = createTestPet();
		int orderId = petStoreRest
			.post(
				"/store/order",
				new CreateOrder().petId(petId).username("catlover")
			)
			.run()
			.assertCode().is(200)
			.getContent().as(int.class);

		return orderId;
	}

	// Posting order
	@Test
	public void testPostOrder() throws Exception {

		petStoreRest
			.post(
				"/store/order",
				new CreateOrder().petId(1).username("snoopy")
			)
			.complete()
			.assertCode().is(200);

		deleteTestOrders();

	}

	// Getting all orders
	@Test
	public void testGettingOrders() throws Exception {

		int orderId = createTestOrder();
		petStoreRest
			.get("/store/order")
			.run()
			.assertCode().is(200)
			.assertContent().is("[{id:" + orderId + ",petId:0,status:'PLACED'}]");

		deleteTestOrders();

	}

	// Find order by Id
	@Test
	public void testfindOrder() throws Exception {

		int orderId = createTestOrder();
		petStoreRest
			.get("/store/order/" + orderId)
			.run()
			.assertCode().is(200)
			.assertContent().is("{id:" + orderId + ",petId:0,status:'PLACED'}");

		deleteTestOrders();
	}

	// Delete order by Id
	@Test
	public void testDeleteOrder() throws Exception {

		int orderId = createTestOrder();
		petStoreRest
			.delete("/store/order/" + orderId)
			.complete()
			.assertCode().is(200);
	}
}