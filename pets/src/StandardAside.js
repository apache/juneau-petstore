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

const StandardAside = () => (
  <aside>
    <div className="text">
      <p>This page shows a standard nested REST resource.</p>
      <p>
      It shows how different properties can be rendered on the same bean
      in different views.
      </p>
      <p>
      It also shows examples of HtmlRender classes and
      @BeanProperty(format) annotations.
      </p>
      <p>
      It also shows how the Queryable converter and query widget can be
      used to create searchable interfaces.
      </p>
    </div>
  </aside>
  );
  
  export default StandardAside;
