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

import React, { Component } from 'react';
import SwaggerUi, { presets } from 'swagger-ui';
import 'swagger-ui/dist/swagger-ui.css';
import NavBlock from './NavBlock';

class Options extends Component {
  componentDidMount() {
    SwaggerUi({
      dom_id: '#swaggerContainer',
      url: `${window.$backendUrl}/petstore/?method=OPTIONS`,
      presets: [presets.apis]
    });
  }

  render() {
    return (
      <div>
        {NavBlock('Petstore application', 'Swagger documentation')}
        <nav>
          <ol>
            <li>
              <a href="/petstore">back</a>
            </li>
            <li>
              <a href="/json">json</a>
            </li>
          </ol>
        </nav>
        <div id="swaggerContainer" />
      </div>
    );
  }
}

export default Options;
