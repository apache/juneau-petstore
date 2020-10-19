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
import Navigation from './Navigation';
import FetchTypes from './FetchTypes';

class Root extends Component {
  constructor(props) {
    super(props);

    this.state = {
      root: [],
      item0: {},
      format: 'table',
      restUrl: `${window.$backendUrl}/`
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
          root: data,
          item0: data[0]
        }));
  }

  changeType = (param) => {
    this.setState({
      format: param
    });
  };

  render() {
    const { 
      root, 
      item0, 
      format, 
      restUrl 
    } = this.state;

    switch (format) {
      case 'table':
        return (
          <div>
            <Container fluid>
              <Row>
                <Col>{NavBlock('Root resources', 'Navigation page')}</Col>
              </Row>
              <Row>
                <Col>
                  <Navigation
                    upLink={false}
                    ahref="/"
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
                          <Table striped bordered hover>
                            <thead>
                              <tr>
                                {Object.entries(item0).map(([key]) => (
                                  <th key={key}>{key}</th>
                                ))}
                              </tr>
                            </thead>
                            <tbody>
                              {root.map((rootItem) => (
                                <tr key={rootItem.key}>
                                  <td>
                                    <a href={`${rootItem.name}`}>
                                      {rootItem.name}
                                    </a>
                                  </td>
                                  <td>{rootItem.description}</td>
                                </tr>
                              ))}
                            </tbody>
                          </Table>
                        </div>
                      </div>
                    </article>
                  </Col>
                  <Col xs={6} md={8} lg={8}> 
                
                    <aside>
                      <div className="text">
                        <p>
                          This is an example of a &apos;router&apos; page that
                          serves as a jumping-off point to child resources.
                        </p>
                        <p>
                          Resources can be nested arbitrarily deep through
                          router pages.
                        </p>
                        <p>
                          Note the
                          {' '}
                          <span>
                            <a className="link" href="/options">
                              options
                              {' '}
                            </a>
                          </span>
                          {' '}
                          link provided that lets you see the generated swagger
                          doc for this page.
                        </p>
                        <p>
                          Also note the
                          {' '}
                          <span>
                            <a
                              className="link"
                              href="https://github.com/apache/juneau/blob/master/juneau-examples/juneau-examples-rest/src/main/java/org/apache/juneau/petstore/rest/RootResources.java"
                            >
                              {' '}
                              sources
                            </a>
                          </span>
                          {' '}
                          link on these pages to view the source code for the
                          page.
                        </p>
                        <p>
                          All content on pages in the UI are serialized POJOs.
                          In this case, it&apos;s a serialized array of beans
                          with 2 properties, &apos;name&apos; and
                          &apos;description&apos;.
                        </p>
                        <p>
                          Other features (such as this aside) are added through
                          annotations.
                        </p>
                      </div>
                    </aside>
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

export default Root;
