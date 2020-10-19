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

class JsonSwagger extends Component {
  constructor(props) {
    super(props);

    this.state = {
      json: [],
      restUrl: `${window.$backendUrl}/petstore/?method=OPTIONS&Accept=text/json&plainText=true`
    };
  }

  componentDidMount() {
    this.getData();
  }

  getData() {
    const { restUrl } = this.state;
    fetch(
      restUrl,
      
    )
      .then((response) => response.json())
      .then((data) =>
        this.setState({
          json: data
        }));
  }

  render() {
    const { json } = this.state;
    return (
      <div>
        <pre>{JSON.stringify(json, null, 2)}</pre>
      </div>
    );
  }
}

export default JsonSwagger;
