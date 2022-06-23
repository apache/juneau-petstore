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

import java.io.*;

import javax.servlet.*;

import org.apache.juneau.petstore.rest.*;
import org.apache.juneau.petstore.service.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.*;

/**
 * Entry point for PetStore application.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.apache.juneau.petstore")
@EnableCaching
@SuppressWarnings("javadoc")
@Controller
public class App {

	//-----------------------------------------------------------------------------------------------------------------
	// Beans
	//-----------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {
		try {
			ConfigurableApplicationContext ctx = new SpringApplicationBuilder(App.class).run(args);
			ctx.getBean(PetStoreService.class).initDirect(new PrintWriter(System.out));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//-----------------------------------------------------------------------------------------------------------------
	// Beans
	//-----------------------------------------------------------------------------------------------------------------

	@Bean
	public PetStoreService petStoreService() {
		return new PetStoreService();
	}

	@Bean
	public RootResources rootResources() {
		return new RootResources();
	}

	@Bean
	public PetStoreResource petStoreResource() {
		return new PetStoreResource();
	}

	@Bean
	public ServletRegistrationBean<Servlet> getRootServlet(RootResources rootResources) {
		return new ServletRegistrationBean<>(rootResources, "/*");
	}
}
