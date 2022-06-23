// ***************************************************************************************************************************
// * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file *
// * distributed with this work for additional information regarding copyright ownership.  The ASF licenses this file        *
// * to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance            *
// * with the License.  You may obtain a copy of the License at                                                              *
// *                                                                                                                         *
// *  http://www.apache.org/licenses/LICENSE-2.0                                                                             *
// *                                                                                                                         *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an  *
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the        *
// * specific language governing permissions and limitations under the License.                                              *
// ***************************************************************************************************************************
package org.apache.juneau.petstore;

import java.util.*;

import org.apache.juneau.petstore.dto.*;
import org.apache.juneau.*;
import org.apache.juneau.http.annotation.*;
import org.apache.juneau.http.remote.*;
import org.apache.juneau.http.response.*;

/**
 * Defines the interface for both the server-side and client-side pet store application.
 *
 * <p>
 * On the server side, this interface is implemented by the <c>PetStoreResource</c> class.
 *
 * <p>
 * On the client side, this interface is instantiated as a proxy using the <c>RestClient.getRemoteProxy()</c> method.
 *
 * <ul class='seealso'>
 * 	<li class='extlink'>{@source}
 * </ul>
 */
@Remote(path="/petstore")
public interface PetStore {

	//------------------------------------------------------------------------------------------------------------------
	// Pets
	//------------------------------------------------------------------------------------------------------------------

	/**
	 * Returns all pets in the database.
	 *
	 * @return All pets in the database.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/pet")
	public Collection<Pet> getPets() throws NotAcceptable;

	/**
	 * Returns a pet from the database.
	 *
	 * @param petId The ID of the pet to retrieve.
	 * @return The pet.
	 * @throws IdNotFound Pet was not found.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/pet/{petId}") /* method inferred from method name */
	public Pet getPet(
		@Path(
			name="petId",
			schema=@Schema(description="ID of pet to return")
		)
		long petId
	) throws IdNotFound, NotAcceptable;

	/**
	 * Adds a pet to the database.
	 *
	 * @param pet The pet data to add to the database.
	 * @return {@link Ok} if successful.
	 * @throws IdConflict ID already in use.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 * @throws UnsupportedMediaType Unsupported <c>Content-Type</c> header specified.
	 */
	@RemotePost("/pet")
	public long createPet(
		@Content(
			schema=@Schema(description="Pet object to add to the store")
		)
		CreatePet pet
	) throws IdConflict, NotAcceptable, UnsupportedMediaType;

	/**
	 * Updates a pet in the database.
	 *
	 * @param pet The pet data to add to the database.
	 * @return {@link Ok} if successful.
	 * @throws IdNotFound ID not found.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 * @throws UnsupportedMediaType Unsupported <c>Content-Type</c> header specified.
	 */
	@RemotePut("/pet/{petId}")
	public Ok updatePet(
		@Content(
			schema=@Schema(description="Pet object that needs to be added to the store")
		)
		UpdatePet pet
	) throws IdNotFound, NotAcceptable, UnsupportedMediaType;

	/**
	 * Find all pets with the matching statuses.
	 *
	 * @param status The statuses to match against.
	 * @return The pets that match the specified statuses.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/pet/findByStatus")
	public Collection<Pet> findPetsByStatus(
		@Query(
			name="status",
			schema=@Schema(
				description="Status values that need to be considered for filter.",
				required=true,
				type="array",
				collectionFormat="csv",
				items=@Items(
					type="string",
					_enum="AVAILABLE,PENDING,SOLD",
					_default="AVAILABLE"
				)
			)
		)
		PetStatus[] status
	) throws NotAcceptable;

	/**
	 * Deletes the specified pet.
	 *
	 * @param apiKey Security key.
	 * @param petId ID of pet to delete.
	 * @return {@link Ok} if successful.
	 * @throws IdNotFound Pet not found.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteDelete("/pet/{petId}")
	public Ok deletePet(
		@Header(
			name="api_key",
			schema=@Schema(
				description="Security API key",
				required=true
			)
		)
		String apiKey,
		@Path(
			name="petId",
			schema=@Schema(description="Pet id to delete")
		)
		long petId
	) throws IdNotFound, NotAcceptable;

	/**
	 * Deletes all pets in the database.
	 *
	 * @return {@link Ok} if successful.
	 */
	@RemoteDelete("/pet")
	public Ok deleteAllPets();

	//------------------------------------------------------------------------------------------------------------------
	// Orders
	//------------------------------------------------------------------------------------------------------------------

	/**
	 * Returns all orders in the database.
	 *
	 * @return All orders in the database.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/store/order")
	public Collection<Order> getOrders() throws NotAcceptable;

	/**
	 * Returns an order from the database.
	 *
	 * @param orderId The ID of the order to retreieve.
	 * @return The retrieved order.
	 * @throws InvalidId ID was invalid.
	 * @throws IdNotFound Order was not found.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/store/order/{orderId}")
	public Order getOrder(
		@Path(
			name="orderId",
			schema=@Schema(
				description="ID of order to fetch",
				maximum="1000",
				minimum="1"
			)
		)
		long orderId
	) throws InvalidId, IdNotFound, NotAcceptable;

	/**
	 * Adds an order to the database.
	 *
	 * @param petId Id of pet to order.
	 * @param username The username of the user placing the order.
	 * @return The ID of the order.
	 * @throws IdConflict ID was already in use.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 * @throws UnsupportedMediaType Unsupported <c>Content-Type</c> header specified.
	 */
	@RemotePost("/store/order")
	public long placeOrder(
		@FormData(
			name="petId",
			schema=@Schema(description="Pet ID")
		)
		long petId,
		@FormData(
			name="username",
			schema=@Schema(description="The username of the user creating the order")
		)
		String username
	) throws IdConflict, NotAcceptable, UnsupportedMediaType;

	/**
	 * Deletes an order from the database.
	 *
	 * @param orderId The order ID.
	 * @return {@link Ok} if successful.
	 * @throws InvalidId ID not valid.
	 * @throws IdNotFound Order not found.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteDelete("/store/order/{orderId}")
	public Ok deleteOrder(
		@Path(
			name="orderId",
			schema=@Schema(
				description="ID of the order that needs to be deleted",
				minimum="1"
			)
		)
		long orderId
	) throws InvalidId, IdNotFound, NotAcceptable;

	/**
	 * Deletes all orders in the database.
	 *
	 * @return {@link Ok} if successful.
	 */
	@RemoteDelete("/store/order")
	public Ok deleteAllOrders();

	/**
	 * Returns an inventory of pet statuses and counts.
	 *
	 * @return An inventory of pet statuses and counts.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/store/inventory")
	public Map<PetStatus,Integer> getStoreInventory() throws NotAcceptable;

	//------------------------------------------------------------------------------------------------------------------
	// Users
	//------------------------------------------------------------------------------------------------------------------

	/**
	 * Returns all users in the database.
	 *
	 * @return All users in the database.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/user")
	public Collection<User> getUsers() throws NotAcceptable;

	/**
	 * Returns a user from the database.
	 *
	 * @param username The username.
	 * @return The user.
	 * @throws InvalidUsername Invalid username.
	 * @throws IdNotFound username not found.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/user/{username}")
	public User getUser(
		@Path(
			name="username",
			schema=@Schema(description="The name that needs to be fetched. Use user1 for testing.")
		)
		String username
	) throws InvalidUsername, IdNotFound, NotAcceptable;

	/**
	 * Adds a new user to the database.
	 *
	 * @param user The user to add to the database.
	 * @return {@link Ok} if successful.
	 * @throws InvalidUsername Username was invalid.
	 * @throws IdConflict Username already in use.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 * @throws UnsupportedMediaType Unsupported <c>Content-Type</c> header specified.
	 */
	@RemotePost("/user")
	public Ok createUser(
		@Content(
			schema=@Schema(description="Created user object")
		)
		User user
	) throws InvalidUsername, IdConflict, NotAcceptable, UnsupportedMediaType;

	/**
	 * Bulk creates users.
	 *
	 * @param users The users to add to the database.
	 * @return {@link Ok} if successful.
	 * @throws InvalidUsername Username was invalid.
	 * @throws IdConflict Username already in use.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 * @throws UnsupportedMediaType Unsupported <c>Content-Type</c> header specified.
	 */
	@RemotePost("/user/createWithArray")
	public Ok createUsers(
		@Content(
			schema=@Schema(description="List of user objects")
		)
		User[] users
	) throws InvalidUsername, IdConflict, NotAcceptable, UnsupportedMediaType;

	/**
	 * Updates a user in the database.
	 *
	 * @param username The username.
	 * @param user The updated information.
	 * @return {@link Ok} if successful.
	 * @throws InvalidUsername Username was invalid.
	 * @throws IdNotFound User was not found.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 * @throws UnsupportedMediaType Unsupported <c>Content-Type</c> header specified.
	 */
	@RemotePut("/user/{username}")
	public Ok updateUser(
		@Path(
			name="username",
			schema=@Schema(description="Name that need to be updated")
		)
		String username,
		@Content(
			schema=@Schema(description="Updated user object")
		)
		User user
	) throws InvalidUsername, IdNotFound, NotAcceptable, UnsupportedMediaType;

	/**
	 * Deletes a user from the database.
	 *
	 * @param username The username.
	 * @return {@link Ok} if successful.
	 * @throws InvalidUsername Username was not valid.
	 * @throws IdNotFound User was not found.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteDelete("/user/{username}")
	public Ok deleteUser(
		@Path(
			name="username",
			schema=@Schema(description="The name that needs to be deleted")
		)
		String username
	) throws InvalidUsername, IdNotFound, NotAcceptable;

	/**
	 * Deletes all users in the database.
	 *
	 * @return {@link Ok} if successful.
	 */
	@RemoteDelete("/user")
	public Ok deleteAllUsers();

	/**
	 * User login.
	 *
	 * @param username The username for login.
	 * @param password The password for login in clear text.
	 * @param expiresAfter The <bc>Expires-After</bc> response header.
	 * @return {@link Ok} if successful.
	 * @throws InvalidLogin Login was unsuccessful.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/user/login")
	public Ok login(
		@Query(
			name="username",
			schema=@Schema(
				description="The username for login.",
				required=true
			)
		)
		String username,
		@Query(
			name="password",
			schema=@Schema(
				description="The password for login in clear text.",
				required=true
			)
		)
		String password,
		Value<ExpiresAfter> expiresAfter
	) throws InvalidLogin, NotAcceptable;

	/**
	 * User logout.
	 *
	 * @return {@link Ok} if successful.
	 * @throws NotAcceptable Unsupported <c>Accept</c> header specified.
	 */
	@RemoteGet("/user/logout")
	public Ok logout() throws NotAcceptable;
}
