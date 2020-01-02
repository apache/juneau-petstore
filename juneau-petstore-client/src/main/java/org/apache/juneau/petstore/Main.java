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

import static java.text.MessageFormat.*;

import java.io.*;
import java.util.*;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.juneau.json.*;
import org.apache.juneau.marshall.*;
import org.apache.juneau.parser.*;
import org.apache.juneau.petstore.dto.*;
import org.apache.juneau.rest.client.*;

/**
 * Example code showing how to connect to the PetStore application using a remote proxy.
 *
 * <p>
 * The remote proxy allows you to make REST calls against our REST interface through Java interface method calls.
 */
@SuppressWarnings("javadoc")
public class Main {

	private static final JsonParser JSON_PARSER = JsonParser.create().ignoreUnknownBeanProperties().build();

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		// TODO - This is broken until we can update to Juneau 8.1.3 which has a fix for handling how Spring Security
		// processes Basic Auth requests.

		// Set up BASIC auth.
		// User/passwords are hardcoded in SpringSecurityConfig.
		Credentials up = new UsernamePasswordCredentials("admin", "password");
		CredentialsProvider p = new BasicCredentialsProvider();
		p.setCredentials(AuthScope.ANY, up);

		// Create a RestClient with JSON serialization support.
		try (RestClient rc = RestClient.create(SimpleJsonSerializer.class, JsonParser.class).defaultCredentialsProvider(p).build()) {

			// Instantiate our proxy.
			PetStore petStore = rc.getRemote(PetStore.class, "http://localhost:5000");

			// Print out the pets in the store.
			Collection<Pet> pets = petStore.getPets();

			// Pretty-print them to SYSOUT.
			SimpleJson.DEFAULT_READABLE.println(pets);

			// Initialize the application through REST calls.
			init(new PrintWriter(System.out), petStore);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the petstore database by using a remote resource interface against our REST.
	 *
	 * @param w Console output.
	 * @throws ParseException Malformed input encountered.
	 * @throws IOException Thrown by client stream.
	 */
	public static void init(PrintWriter w, PetStore ps) throws ParseException, IOException {

		for (Pet x : ps.getPets()) {
			ps.deletePet("apiKey", x.getId());
			w.println(format("Deleted pet:  id={0}", x.getId()));
		}

		for (Order x : ps.getOrders()) {
			ps.deleteOrder(x.getId());
			w.println(format("Deleted order:  id={0}", x.getId()));
		}

		for (User x : ps.getUsers()) {
			ps.deleteUser(x.getUsername());
			w.println(format("Deleted user:  username={0}", x.getUsername()));
		}

		for (CreatePet x : load("init/Pets.json", CreatePet[].class)) {
			long id = ps.createPet(x);
			w.println(format("Created pet:  id={0}, name={1}", id, x.getName()));
		}

		for (Order x : load("init/Orders.json", Order[].class)) {
			long id = ps.placeOrder(x.getPetId(), x.getUsername());
			w.println(format("Created order:  id={0}", id));
		}

		for (User x : load("init/Users.json", User[].class)) {
			ps.createUser(x);
			w.println(format("Created user:  username={0}", x.getUsername()));
		}

		w.flush();
	}

	//-----------------------------------------------------------------------------------------------------------------
	// Helper methods
	//-----------------------------------------------------------------------------------------------------------------

	private static <T> T load(String fileName, Class<T> c) throws ParseException, IOException {
		return JSON_PARSER.parse(getStream(fileName), c);
	}

	private static InputStream getStream(String fileName) {
		return Main.class.getResourceAsStream(fileName);
	}
}
