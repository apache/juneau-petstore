<!--
 ***************************************************************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information regarding copyright ownership.  The ASF licenses this file        *
 * to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance            *
 * with the License.  You may obtain a copy of the License at                                                              *
 *                                                                                                                         *
 *  http://www.apache.org/licenses/LICENSE-2.0                                                                             *
 *                                                                                                                         *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the        *
 * specific language governing permissions and limitations under the License.                                              *
 ***************************************************************************************************************************
-->

# Juneau Pet Store Application

A sample application that shows the capabilities of the Juneau REST platform with Spring Boot and other technologies
to produce end-to-end REST microservices.

Juneau provides the following capabilities:

- Ability to serialize/parse Java POJOs to a wide variety of languages.
- Ability to construct REST interfaces using annotated POJOs.
- Ability to create Java interface proxies against remote REST interfaces.

The project is broken down into the following subprojects:

- juneau-petstore-api - Contains the Java interface and DTOs for the petstore application.
- juneau-petstore-server - Contains the server-side Java implementation of the petstore Java interface as a REST resource.
- juneau-petstore-client - Contains the client-side Java proxy of the petstore Java interface.

More information for this project can be found [here](http://juneau.apache.org/index.html#petstore.html)