// ***************************************************************************************************************************
// * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file *
// * distributed with this work for additional information regarding copyright ownership.  The ASF licenses this file        *
// * to you under the Apache License, Version 2.0 (the 'License"); you may not use this file except in compliance            *
// * with the License.  You may obtain a copy of the License at                                                              *
// *                                                                                                                         *
// *  http://www.apache.org/licenses/LICENSE-2.0                                                                             *
// *                                                                                                                         *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an  *
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the        *
// * specific language governing permissions and limitations under the License.                                              *
// ***************************************************************************************************************************
package org.apache.juneau.petstore.rest;

import static org.apache.juneau.http.response.Ok.*;

import java.util.*;
import java.util.Map;

import org.apache.juneau.petstore.*;
import org.apache.juneau.petstore.dto.*;
import org.apache.juneau.petstore.service.*;
import org.apache.juneau.Value;
import org.apache.juneau.annotation.*;
import org.apache.juneau.html.annotation.*;
import org.apache.juneau.http.annotation.*;
import org.apache.juneau.rest.*;
import org.apache.juneau.rest.annotation.*;
import org.apache.juneau.rest.beans.*;
import org.apache.juneau.rest.config.*;
import org.apache.juneau.rest.converter.*;
import org.apache.juneau.rest.servlet.*;
import org.apache.juneau.http.response.*;
import org.apache.juneau.rest.widget.*;
import org.apache.juneau.swaps.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Sample Petstore application.
 *
 * <ul class='seealso'>
 * 	<li class='extlink'>{@source}
 * </ul>
 */
@Rest(
	path="/petstore",
	title="Petstore application",
	description={
		"This is a sample server Petstore server based on the Petstore sample at Swagger.io.",
		"You can find out more about Swagger at http://swagger.io.",
	},
	swagger=@Swagger(
		version="1.0.0",
		title="Swagger Petstore",
		termsOfService="You are on your own.",
		contact=@Contact(
			name="Juneau Development Team",
			email="dev@juneau.apache.org",
			url="http://juneau.apache.org"
		),
		license=@License(
			name="Apache 2.0",
			url="http://www.apache.org/licenses/LICENSE-2.0.html"
		),
		externalDocs=@ExternalDocs(
			description="Find out more about Juneau",
			url="http://juneau.apache.org"
		),
		tags={
			@Tag(
				name="pet",
				description="Everything about your Pets",
				externalDocs=@ExternalDocs(
					description="Find out more",
					url="http://juneau.apache.org"
				)
			),
			@Tag(
				name="store",
				description="Access to Petstore orders"
			),
			@Tag(
				name="user",
				description="Operations about user",
				externalDocs=@ExternalDocs(
					description="Find out more about our store",
					url="http://juneau.apache.org"
				)
			)
		}
	)
)
@HtmlDocConfig(
	widgets={
		ContentTypeMenuItem.class
	},
	navlinks={
		"up: request:/..",
		"api: servlet:/api",
		"stats: servlet:/stats",
		"$W{ContentTypeMenuItem}",
		"source: $C{Source/gitHub}/org/apache/juneau/petstore/rest/$R{servletClassSimple}.java"
	},
	head={
		"<link rel='icon' href='$U{servlet:/htdocs/cat.png}'/>"  // Add a cat icon to the page.
	},
	header={
		"<h1>$RS{title}</h1>",  // Use @Rest(title)
		"<h2>$RS{operationSummary,description}</h2>", // Use either @RestOp(summary) or @Rest(description)
		"$C{PetStore/headerImage}"
	},
	aside={
		"<div style='max-width:400px' class='text'>",
		"	<p>This page shows a standard nested REST resource.</p>",
		"	<p>It shows how different properties can be rendered on the same bean in different views.</p>",
		"	<p>It also shows examples of HtmlRender classes and @BeanProperty(format) annotations.</p>",
		"	<p>It also shows how the Queryable converter and query widget can be used to create searchable interfaces.</p>",
		"</div>"
	},
	stylesheet="servlet:/htdocs/themes/dark.css"  // Use dark theme by default.
)
public class PetStoreResource extends BasicRestObject implements BasicUniversalConfig, PetStore {

	@Autowired
	private PetStoreService store;

	/**
	 * Navigation page
	 *
	 * @return Navigation page contents.
	 */
	@RestGet(
		path="/",
		summary="Navigation page"
	)
	@HtmlDocConfig(
		style={
			"INHERIT",  // Flag for inheriting resource-level CSS.
			"body { ",
				"background-image: url('petstore/htdocs/background.jpg'); ",
				"background-color: black; ",
				"background-size: cover; ",
				"background-attachment: fixed; ",
			"}"
		}
	)
	public ResourceDescriptions getTopPage() {
		return new ResourceDescriptions()
			.append("pet", "All pets in the store")
			.append("store", "Orders and inventory")
			.append("user", "Petstore users")
		;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Pets
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override /* PetStore */
	@RestGet(
		path="/pet",
		summary="All pets in the store",
		swagger=@OpSwagger(
			tags="pet",
			parameters={
				Queryable.SWAGGER_PARAMS  // Documents searching.
			}
		),
		converters={Queryable.class}  // Searching support.
	)
	@Bean(on="Pet", excludeProperties="tags,photo")
	public Collection<Pet> getPets() throws NotAcceptable {
		return store.getPets();
	}

	@Override /* PetStore */
	@RestGet(
		path="/pet/{petId}",
		summary="Find pet by ID",
		description="Returns a single pet",
		swagger=@OpSwagger(
			tags="pet"
		)
	)
	public Pet getPet(long petId) throws IdNotFound, NotAcceptable {
		return store.getPet(petId);
	}

	@Override /* PetStore */
	@RestPost(
		path="/pet",
		summary="Add a new pet to the store",
		swagger=@OpSwagger(
			tags="pet"
		)
		//roleGuard="ROLE_ADMIN || (ROLE_USER && ROLE_WRITABLE)"  // Restrict access to this method.
	)
	public long createPet(CreatePet pet) throws IdConflict, NotAcceptable, UnsupportedMediaType {
		return store.create(pet).getId();
	}

	@Override /* PetStore */
	@RestPut(
		path="/pet/{petId}",
		summary="Update an existing pet",
		swagger=@OpSwagger(
			tags="pet"
		)
	)
	public Ok updatePet(UpdatePet pet) throws IdNotFound, NotAcceptable, UnsupportedMediaType {
		store.update(pet);
		return OK;
	}

	@Override /* PetStore */
	@RestGet(
		path="/pet/findByStatus",
		summary="Finds Pets by status",
		description="Multiple status values can be provided with comma separated strings.",
		swagger=@OpSwagger(
			tags="pet"
		)
	)
	public Collection<Pet> findPetsByStatus(PetStatus[] status) throws NotAcceptable {
		return store.getPetsByStatus(status);
	}

	@Override /* PetStore */
	@RestDelete(
		path="/pet/{petId}",
		summary="Deletes a pet",
		swagger=@OpSwagger(
			tags="pet"
		)
	)
	public Ok deletePet(String apiKey, long petId) throws IdNotFound, NotAcceptable {
		store.deletePet(petId);
		return OK;
	}

	@Override /* PetStore */
	@RestDelete(
		path="/pets",
		summary="Delete all pets",
		description="This can be done only by the logged in user."
	)
	public Ok deleteAllPets() {
		store.deleteAllPets();
		return OK;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Orders
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Store navigation page.
	 *
	 * @return Store navigation page contents.
	 */
	@RestGet(
		summary="Store navigation page",
		swagger=@OpSwagger(
			tags="store"
		)
	)
	public ResourceDescriptions getStore() {
		return new ResourceDescriptions()
			.append("store/order", "Petstore orders")
			.append("store/inventory", "Petstore inventory")
		;
	}

	@Override /* PetStore */
	@RestGet(
		path="/store/order",
		summary="Petstore orders",
		swagger=@OpSwagger(
			tags="store"
		)
	)
	@HtmlDocConfig(
		widgets={
			QueryMenuItem.class
		},
		navlinks={
			"INHERIT",                // Inherit links from class.
			"[2]:$W{QueryMenuItem}",  // Insert QUERY link in position 2.
			"[3]:$W{AddOrderMenuItem}"  // Insert ADD link in position 3.
		}
	)
	public Collection<Order> getOrders() throws NotAcceptable {
		return store.getOrders();
	}

	@Override /* PetStore */
	@RestGet(
		path="/store/order/{orderId}",
		summary="Find purchase order by ID",
		description="Returns a purchase order by ID.",
		swagger=@OpSwagger(
			tags="store"
		)
	)
	public Order getOrder(long orderId) throws InvalidId, IdNotFound, NotAcceptable {
		if (orderId < 1 || orderId > 1000)
			throw new InvalidId();
		return store.getOrder(orderId);
	}

	@Override /* PetStore */
	@RestPost(
		path="/store/order",
		summary="Place an order for a pet",
		swagger=@OpSwagger(
			tags="store"
		)
	)
	@BeanConfig(
		swaps = TemporalDateSwap.IsoLocalDate.class
	)
	public long placeOrder(long petId, String username) throws IdConflict, NotAcceptable, UnsupportedMediaType {
		CreateOrder co = new CreateOrder(petId, username);
		return store.create(co).getId();
	}

	@Override /* PetStore */
	@RestDelete(
		path="/store/order/{orderId}",
		summary="Delete purchase order by ID",
		description= {
			"For valid response try integer IDs with positive integer value.",
			"Negative or non-integer values will generate API errors."
		},
		swagger=@OpSwagger(
			tags="store"
		)
	)
	public Ok deleteOrder(long orderId) throws InvalidId, IdNotFound, NotAcceptable {
		if (orderId < 0)
			throw new InvalidId();
		store.deleteOrder(orderId);
		return OK;
	}

	@Override /* PetStore */
	@RestDelete(
		path="/orders",
		summary="Delete all orders",
		description="This can be done only by the logged in user."
	)
	public Ok deleteAllOrders() {
		store.deleteAllOrders();
		return OK;
	}

	@Override /* PetStore */
	@RestGet(
		path="/store/inventory",
		summary="Returns pet inventories by status",
		description="Returns a map of status codes to quantities",
		swagger=@OpSwagger(
			tags="store",
			responses={
				"200:{ 'x-example':{AVAILABLE:123} }",
			}
		)
	)
	public Map<PetStatus,Integer> getStoreInventory() throws NotAcceptable {
		return store.getInventory();
	}

	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Users
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override /* PetStore */
	@RestGet(
		path="/user",
		summary="Petstore users",
		swagger=@OpSwagger(
			tags="user"
		)
	)
	@Bean(on="User", excludeProperties="email,password,phone")
	public Collection<User> getUsers() throws NotAcceptable {
		return store.getUsers();
	}

	@Override /* PetStore */
	@RestGet(
		path="/user/{username}",
		summary="Get user by user name",
		swagger=@OpSwagger(
			tags="user"
		)
	)
	public User getUser(String username) throws InvalidUsername, IdNotFound, NotAcceptable {
		return store.getUser(username);
	}

	@Override /* PetStore */
	@RestPost(
		path="/user",
		summary="Create user",
		description="This can only be done by the logged in user.",
		swagger=@OpSwagger(
			tags="user"
		)
	)
	public Ok createUser(User user) throws InvalidUsername, IdConflict, NotAcceptable, UnsupportedMediaType {
		store.create(user);
		return OK;
	}

	@Override /* PetStore */
	@RestPost(
		path="/user/createWithArray",
		summary="Creates list of users with given input array",
		swagger=@OpSwagger(
			tags="user"
		)
	)
	public Ok createUsers(User[] users) throws InvalidUsername, IdConflict, NotAcceptable, UnsupportedMediaType {
		for (User user : users)
			store.create(user);
		return OK;
	}

	@Override /* PetStore */
	@RestPut(
		path="/user/{username}",
		summary="Update user",
		description="This can only be done by the logged in user.",
		swagger=@OpSwagger(
			tags="user"
		)
	)
	public Ok updateUser(String username, User user) throws InvalidUsername, IdNotFound, NotAcceptable, UnsupportedMediaType {
		store.update(user);
		return OK;
	}

	@Override /* PetStore */
	@RestDelete(
		path="/user/{username}",
		summary="Delete user",
		description="This can only be done by the logged in user.",
		swagger=@OpSwagger(
			tags="user"
		)
	)
	public Ok deleteUser(String username) throws InvalidUsername, IdNotFound, NotAcceptable {
		store.deleteUser(username);
		return OK;
	}

	@Override /* PetStore */
	@RestDelete(
		path="/users",
		summary="Delete all users",
		description="This can be done only by the admin."
	)
	public Ok deleteAllUsers() {
		store.deleteAllUsers();
		return OK;
	}

	@Override /* PetStore */
	@RestGet(
		path="/user/login",
		summary="Logs user into the system",
		swagger=@OpSwagger(
			tags="user"
		)
	)
	public Ok login(
			String username,
			String password,
			Value<ExpiresAfter> expiresAfter
		) throws InvalidLogin, NotAcceptable {

		RestRequest req = getRequest();

		if (! store.isValid(username, password))
			throw new InvalidLogin();

		Date d = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
		req.getSession().setAttribute("login-expires", d);
		expiresAfter.set(new ExpiresAfter(d));
		return OK;
	}

	@Override /* PetStore */
	@RestGet(
		path="/user/logout",
		summary="Logs out current logged in user session",
		swagger=@OpSwagger(
			tags="user"
		)
	)
	public Ok logout() throws NotAcceptable {
		getRequest().getSession().removeAttribute("login-expires");
		return OK;
	}
}