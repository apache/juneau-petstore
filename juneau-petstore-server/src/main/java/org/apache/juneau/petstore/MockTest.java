package org.apache.juneau.petstore;

import static org.apache.juneau.http.HttpMethodName.DELETE;
import static org.apache.juneau.http.HttpMethodName.GET;
import static org.apache.juneau.http.HttpMethodName.POST;
import static org.apache.juneau.http.HttpMethodName.PUT;
import static org.apache.juneau.http.response.Ok.OK;
import org.apache.juneau.http.annotation.Body;
import org.apache.juneau.http.exception.NotAcceptable;
import org.apache.juneau.http.exception.UnsupportedMediaType;
import org.apache.juneau.http.response.Ok;
import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.petstore.dto.CreateOrder;
import org.apache.juneau.petstore.dto.CreatePet;
import org.apache.juneau.petstore.dto.IdConflict;
import org.apache.juneau.petstore.dto.IdNotFound;
import org.apache.juneau.petstore.dto.InvalidId;
import org.apache.juneau.petstore.dto.InvalidTag;
import org.apache.juneau.petstore.dto.InvalidUsername;
import org.apache.juneau.petstore.dto.PetStatus;
import org.apache.juneau.petstore.dto.Species;
import org.apache.juneau.petstore.dto.UpdatePet;
import org.apache.juneau.petstore.dto.User;
import org.apache.juneau.petstore.dto.UserStatus;
import org.apache.juneau.rest.annotation.Rest;
import org.apache.juneau.rest.annotation.RestMethod;
import org.apache.juneau.rest.mock2.MockRest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


    @RunWith(SpringRunner.class)
	@ContextConfiguration(classes = { App.class })
	@SpringBootTest
public class MockTest {

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Pets
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    	// Getting all pets

    			@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
    			public static class GetPetsMockRest {

    				@RestMethod(name = GET, path = "/pet", summary = "All pets in the store")

    				public Ok getPets() throws NotAcceptable {
    					return OK;
    				}

    			}

    			@Test
    			public void testGettingPets() throws Exception {

    				MockRest
    				.build(GetPetsMockRest.class)
    				.get("/pet")
    				.execute()
    				.assertStatus(200);

    			}
    			// Updating pet
    			
    			@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
    			public static class UpdatePetMockRest {

    				@RestMethod(name = PUT, path = "/pet/{petId}", summary = "Update an existing pet")

    				public UpdatePet echo(@Body UpdatePet pet) throws IdNotFound, NotAcceptable, UnsupportedMediaType {
    					return pet;
    				}
    			}

    			@Test
    			public void testUpdatePet() throws Exception {

    				UpdatePet pet = new UpdatePet(1, "Rocky", 100, Species.DOG, null, null);

    				MockRest
    				.build(UpdatePetMockRest.class)
    				.put("/pet/1", pet)
    				.execute()
    				.assertStatus(200)			
    				.assertBody(pet.toString());
    			}

    	// Posting pet 

    	@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
    	public static class PostPetMockRest {

    		@RestMethod(name = POST, path = "/pet", summary = "Add a new pet to the store")

    		public Ok echo(@Body CreatePet pet) throws IdConflict, NotAcceptable, UnsupportedMediaType {
    			return OK;
    		}
    	}
    		@Test
    		public void testPostPet() throws Exception {
    			CreatePet pet = new CreatePet("Sunshine", 100, Species.BIRD, null);

    			MockRest
    			.build(PostPetMockRest.class)
    			.post("/pet", pet)
    			.execute()
    			.assertStatus(200)
    			.assertBody(pet.toString());
    		}
	
	
		
		// Delete pet by Id

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class DeletePetMockRest {

			@RestMethod(name = DELETE, path = "/pet/{petId}", summary = "Deletes a pet"

			)
			public Ok deletePet(@Body long petId) throws IdNotFound, NotAcceptable {

				return OK;
			}

		}

		@Test
		public void testDeletePet() throws Exception {

			MockRest
			.build(DeletePetMockRest.class)
			.delete("/pet/1")
			.execute()
			.assertStatus(200)
			.assertBody("1");

		}

		// Find pet by Id

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class FindPetByIdMockRest {

			@RestMethod(name = GET, path = "/pet/{petId}", summary = "Find pet by ID", description = "Returns a single pet"

			)
			public Ok getPet(@Body long petId) throws IdNotFound, NotAcceptable {
				return OK;
			}

		}

		@Test
		public void testfindPet() throws Exception {

			MockRest
			.build(FindPetByIdMockRest.class)
			.get("/pet/1")
			.execute()
			.assertStatus(200)
			.assertBody("1");
		}

		// Find pet by status

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class FindPetByStatusMockRest {

			@RestMethod(name = GET, path = "/pet/findByStatus", summary = "Finds Pets by status"

			)
			public Ok findPetsByStatus(@Body PetStatus[] status) throws NotAcceptable {
				return OK;
			}

		}

		@Test
		public void testfindPetByStatus() throws Exception {
			PetStatus[] status = { PetStatus.AVAILABLE };
			
			MockRest
			.build(FindPetByStatusMockRest.class)
			.request("GET", "/pet/findByStatus", status)
			.execute()
			.assertStatus(200)
			.assertBodyContains(status.toString());
		}

		// Find pet by tags

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class FindPetByTagsMockRest {

			@RestMethod(name = GET, path = "/pet/findByTags", summary = "Finds Pets by tags"

			)
			public Ok findPetsByTags(@Body PetStatus[] tags) throws InvalidTag, NotAcceptable {
				return OK;
			}

		}

		@Test
		public void testFindPetByTags() throws Exception {
			String[] tags = { "nice", "friendly" };
			
			MockRest
			.build(FindPetByTagsMockRest.class)
			.request("GET", "/pet/findByTags", tags)
			.execute()
			.assertStatus(200)
			.assertBodyContains(tags);
		}

		

		// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		// Orders
		// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		// Getting all orders

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class GetOrdersMockRest {
			
			@RestMethod(name = GET, path = "/store/order", summary = "Petstore orders"

			)

			public Ok getOrders() throws NotAcceptable {
				return OK;
			}

		}

		@Test
		public void testGettingOrders() throws Exception {

			MockRest
			.build(GetOrdersMockRest.class)
			.get("/store/order")
			.execute()
			.assertStatus(200);

		}

		// Find order by Id

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class FindOrderByIdMockRest {

			@RestMethod(name = GET, path = "/store/order/{orderId}", summary = "Find purchase order by ID"

			)
			public Ok getOrder(@Body long orderId) throws InvalidId, IdNotFound, NotAcceptable {
				
				return OK;
			}

		}

		@Test
		public void testfindOrder() throws Exception {

			MockRest
			.build(FindOrderByIdMockRest.class)
			.get("/store/order/1")
			.execute()
			.assertStatus(200)
			.assertBody("1");
		}

		// Posting order

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class PostOrderMockRest {

			@RestMethod(name = POST, path = "/store/order", summary = "Place an order for a pet"

			)
			public Ok placeOrder(@Body long petId, @Body String username)
					throws IdConflict, NotAcceptable, UnsupportedMediaType {

				return OK;
			}
		}

		@Test
		public void testPostOrder() throws Exception {
			CreateOrder co = new CreateOrder(123, "MyOrder");
			
			MockRest
			.build(PostOrderMockRest.class)
			.post("/store/order", co)
			.execute()
			.assertStatus(200)
			.assertBody(co.toString());
		}

		// Delete order by Id

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class DeleteOrderMockRest {

			@RestMethod(name = DELETE, path = "/store/order/{orderId}", summary = "Delete purchase order by ID"

			)
			public Ok deleteOrder(@Body long orderId) throws InvalidId, IdNotFound, NotAcceptable {
				
				return OK;
			}

		}

		@Test
		public void testDeleteOrder() throws Exception {

			MockRest
			.build(DeleteOrderMockRest.class)
			.delete("/store/order/1")
			.execute()
			.assertStatus(200)
			.assertBody("1");
		}

		

		// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		// Users
		// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		// Getting all users

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class GetUsersMockRest {

			@RestMethod(name = GET, path = "/user", summary = "Petstore users"

			)
			public Ok getUsers() throws NotAcceptable {
				return OK;
			}
		}

		@Test
		public void testGettingUsers() throws Exception {

			MockRest
			.build(GetUsersMockRest.class)
			.get("/user")
			.execute()
			.assertStatus(200);

		}

		// Get user by user name

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class FindUserByUserNameMockRest {

			@RestMethod(name = GET, path = "/user/{username}", summary = "Get user by user name"

			)
			public Ok getUser(@Body String username) throws InvalidUsername, IdNotFound, NotAcceptable {
				return OK;
			}

		}

		@Test
		public void testFindUserByName() throws Exception {

			MockRest
			.build(FindPetByTagsMockRest.class)
			.get("/user/Mister")
			.execute()
			.assertStatus(200)
			.assertBody("Mister");
		}

		// Create user

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class CreateUserMockRest {

			@RestMethod(name = POST, path = "/user", summary = "Create user"

			)
			public Ok createUser(@Body User user) throws InvalidUsername, IdConflict, NotAcceptable, UnsupportedMediaType {

				return OK;
			}
		}

		@Test
		public void testCreateUser() throws Exception {
			User user = new User()
					.username("billy")
					.firstName("Billy")
					.lastName("Bob")
					.email("billy@apache.org")
					.userStatus(UserStatus.ACTIVE)
					.phone("111-222-3333");

			MockRest
			.build(CreateUserMockRest.class)
			.post("/user", user)
			.execute()
			.assertStatus(200)
			.assertBody(user.toString());
		}

		// Create list of users
		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class CreateListOfUsersMockRest {

			@RestMethod(name = POST, path = "/user/createWithArray", summary = "Creates list of users with given input array"

			)
			public Ok createUsers(@Body User[] users)
					throws InvalidUsername, IdConflict, NotAcceptable, UnsupportedMediaType {

				return OK;
			}
		}

		@Test
		public void testCreateUsers() throws Exception {
			
			User user1 = new User()
					.username("billy")
					.firstName("Billy")
					.lastName("Bob")
					.email("billy@apache.org")
					.userStatus(UserStatus.ACTIVE)
					.phone("111-222-3333");
			
			User user2 = new User()
					.username("peter")
					.firstName("Peter")
					.lastName("Adams")
					.email("peter@apache.org")
					.userStatus(UserStatus.ACTIVE)
					.phone("154-222-3333");

			User[] users = { user1, user2 };

			MockRest
			.build(CreateUserMockRest.class)
			.post("/user/createWithArray", users)
			.execute()
			.assertStatus(200)
			.assertBody(users.toString());
		}

		// Updating user

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class UpdateUserMockRest {

			@RestMethod(name = PUT, path = "/user/{username}", summary = "Update user")

			public Ok updateUser(@Body String username, @Body User user)
					throws InvalidUsername, IdNotFound, NotAcceptable, UnsupportedMediaType {

				return OK;
			}
		}

		@Test
		public void testUpdateUser() throws Exception {

			User user = new User().username("Mister").phone("544-226-343");

			MockRest
			.build(UpdateUserMockRest.class)
			.put("/user/Mister", user)	
			.execute()
			.assertStatus(200)
			.assertBody(user.toString());
		}

		// Delete user

		@Rest(serializers = JsonSerializer.class, parsers = JsonParser.class)
		public static class DeleteUserMockRest {

			@RestMethod(name = DELETE, path = "/user/{username}", summary = "Delete user")
			public Ok deleteUser(@Body String username) throws InvalidUsername, IdNotFound, NotAcceptable {

				return OK;
			}
		}

		@Test
		public void testDeleteUser() throws Exception {

			MockRest
			.build(DeleteUserMockRest.class)
			.delete("/user/John")
			.execute()
			.assertStatus(200)
			.assertBody("John");
		}
		
	}

