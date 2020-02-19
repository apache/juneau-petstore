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

class Pets extends Component {
  constructor(props) {
    super(props);

    this.state = {
      pets: [],
      pet0: {},
      format: 'table',
      restUrl: `${window.$backendUrl}/petstore/pet`
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
          pets: data,
          pet0: data[0]
        }));
  }

  changeType = (param) => {
    this.setState({
      format: param
    });
  };

  render() {
    const {
 pets, pet0, format, restUrl 
} = this.state;

 switch (format) {
      case 'table':
        return (
          <div>
            <Container fluid>
              <Row>
                <Col>
                  {NavBlock('Petstore application', 'All pets in the store')}
                </Col>
              </Row>
              <Row>
                <Col>
                  <Navigation
                    upLink
                    ahref="/petstore"
                    changeType={(param) => this.changeType(param)}
                  />
                </Col>
              </Row>
              <section>
                <Row>
                  <Col>
                    <article>
                      <div className="outerdata">
                        
                        <div className="data">
                          <Table striped bordered hover>
                            <thead>
                              <tr>
                                {Object.entries(pet0).map(([key]) => (
                                  <th key={key}>{key}</th>
                                ))}
                              </tr>
                            </thead>
                            <tbody>
                              {pets.map((petItem) => (
                                <tr key={petItem.key}>
                                  <td>
                                    <a href={`/petstore/pet/${petItem.id}`}>
                                      {petItem.id}
                                    </a>
                                  </td>
                                  <td>{petItem.species}</td>
                                  <td>{petItem.name}</td>
                                  <td>
                                    <ul>
                                      {petItem.tags.map((value, index) => (
                                        // eslint-disable-next-line react/no-array-index-key
                                        <li key={index}>{value}</li>
                                      ))}
                                    </ul>
                                  </td>
                                  <td>{petItem.price}</td>
                                  <td>{petItem.status}</td>
                                </tr>
                              ))}
                            </tbody>
                          </Table>
                        </div> 
                      </div>
                    </article>
                  </Col>
                  <Col>
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
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fplain`}
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
              fetchContent={`${restUrl}?plainText=true&Accept=text%2Fxml%2Bsoap`}
            />
          </div>
        );
      default:
        return null;
    }  
  }
}

export default Pets; 
