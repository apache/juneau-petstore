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
import {
 Table, Col, Row, Container 
} from 'react-bootstrap';
import NavBlock from './NavBlock';
import StandardAside from './StandardAside';
import Navigation from './Navigation';
import FetchTypes from './FetchTypes';

class Inventory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      inventory: [],
      format: 'table',
      restUrl: `${window.$backendUrl}/petstore/store/inventory`
    };
  }

  componentDidMount() {
    this.getData();
  }

  getData() {
    const { restUrl } = this.state;
    fetch(restUrl, {
      headers: {
        Accept: 'application/json'
      }
    })
      .then((response) => response.json())
      .then((data) =>
        this.setState({
          inventory: data
        }));
  }

  changeType= (param) => {
    this.setState({
       format: param
     });
 }

  render() {
    const { inventory, format, restUrl } = this.state;
    
    switch (format) {
      case 'table':
        return (
          <div>
            <Container fluid>
              <Row>
                <Col>
                  {NavBlock(
                    'Petstore application',
                    'Returns pet inventories by status'
                  )}
                </Col>
              </Row>
              <Row>
                <Col>
                  <Navigation
                    upLink
                    ahref="/petstore/store"
                    changeType={(param) => this.changeType(param)}
                    
                  />
                </Col>
              </Row>
              <section>
                <Row>
                  <Col xs={6} md={4} lg={4}>
                    <article>
                      <div className="outerdata">
                        <div className="data" id="data">
                          {Object.keys(inventory).map((key) => (
                            <Table striped bordered hover>
                              <tbody>
                                <tr key={key}>
                                  <th>{key}</th>
                                  <td>{inventory[key]}</td>
                                </tr>
                              </tbody>
                            </Table>
                          ))}
                        </div>
                      </div>
                    </article>
                  </Col>
                  <Col xs={6} md={8} lg={8}> 
                    <StandardAside />
                  </Col>
                </Row>
              </section>
            </Container>
          </div>
        );
      case 'json':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=application%2Fjson`}
            />
          </div>
        );
      case 'schema':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=application%2Fjson%2Bschema`}
            />
          </div>
        );
      case 'urlEncoded':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=application%2Fx-www-form-urlencoded`}
            />
          </div>
        );
      case 'octal':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=octal%2Fmsgpack`}
            />
          </div>
        );
      case 'texthtml':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fhtml`}
            />
          </div>
        );
      case 'htmlschema':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fhtml%2Bschema`}
            />
          </div>
        );
      case 'htmlstripped':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fhtml%2Bstripped`}
            />
          </div>
        );
      case 'openapi':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fopenapi`}
            />
          </div>
        );
      case 'textplain':
        return (
          <div>
            <FetchTypes
              ffetchContent={`${restUrl}?plainText=true&Accept=text%2Fplain`}
            />
          </div>
        );
      case 'textuon':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fuon`}
            />
          </div>
        );
      case 'textxml':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fxml`}
            />
          </div>
        );
      case 'textxmlschema':
        return (
          <div>
            <FetchTypes
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fxml%2Bschema`}
            />
          </div>
        );
      case 'textxmlsoap':
        return (
          <div>
            <FetchTypes
              fetch={`${restUrl}?plainText=true&Accept=text%2Fxml%2Bsoap`}
            />
          </div>
        );
      default:
        return null;
    }
  }
}

export default Inventory;
