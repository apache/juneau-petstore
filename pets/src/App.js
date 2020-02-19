/***************************************************************************************************************************
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
 ***************************************************************************************************************************/

import React from 'react';
import './App.css';
import { Route, Switch } from 'react-router-dom';
import Footer from './Footer';
import Petstore from './Petstore';
import Root from './Root';
import Pets from './Pets';
import Users from './Users';
import Options from './Options';
import Store from './Store';
import Orders from './Orders';
import Inventory from './Inventory';
import PetById from './PetById';
import OrderById from './OrderById';
import UserDetails from './UserDetails';
import JsonSwagger from './JsonSwagger';
import 'bootstrap/dist/css/bootstrap.min.css';

const App = () => 
   (
    
     <div>
       <Switch>
       
         <Route exact path="/" component={Root} /> 
         <Route exact path="/petstore" component={Petstore} /> 
         <Route exact path="/petstore/store" component={Store} />
         <Route exact path="/petstore/pet" component={Pets} /> 
         <Route exact path="/petstore/user" component={Users} /> 
         <Route exact path="/petstore/store/order" component={Orders} />         
         <Route exact path="/petstore/store/inventory" component={Inventory} />    
         <Route exact path="/petstore/pet/:id" component={PetById} /> 
         <Route exact path="/petstore/store/order/:id" component={OrderById} />
         <Route exact path="/petstore/user/:username" component={UserDetails} />
         <Route exact path="/options" component={Options} />
         <Route exact path="/json" component={JsonSwagger} />
      
       </Switch>

       <Footer />
     </div>
  );

export default App;
