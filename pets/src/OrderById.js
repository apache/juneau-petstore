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
import PropTypes from 'prop-types';
import {
  Table, Col, Row, Container 
 } from 'react-bootstrap';

import NavBlock from './NavBlock';
import StandardAside from './StandardAside';
import Navigation from './Navigation';
import FetchTypes from './FetchTypes';

class OrderById extends Component {
  constructor(props) {
    super(props);
    const {
      match: { params }
    } = this.props;
    this.state = {
      order: {},
      format: 'table',
      restUrl: `${window.$backendUrl}/petstore/store/order/${params.id}`
    };
  }

  componentDidMount() {
    this.getData();
  }

  getData() {
    const { restUrl } = this.state;
    fetch(restUrl, {
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json'
      }
    })
      .then((response) => response.json())
      .then((data) =>
        this.setState({
          order: data
        }));
  }

  changeType= (param) => {
    this.setState({
       format: param
     });
 }
  
  render() {
    const { order, format, restUrl } = this.state;
    switch (format) {
      case 'table':
        return (
          <div>
            <Container fluid>
              <Row> 
                <Col>
                  {NavBlock('Petstore application', 'Find purchase order by ID')}
                </Col>
              </Row>
              <Row> 
                <Col>
                  <Navigation
                    upLink
                    ahref="/petstore/store/order"
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
                            <tbody>
                              {Object.entries(order).map(([key, value]) => (
                                <tr key={key}>
                                  <th> 
                                    {key}
                                  </th>
                                  <td>{value}</td>
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
            <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=application%2Fjson`} />
          </div>
        );
      case 'schema':
        return (
          <div>
            <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=application%2Fjson%2Bschema`} />
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

OrderById.propTypes = {
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.node
    }).isRequired
  }).isRequired
};

export default OrderById;
