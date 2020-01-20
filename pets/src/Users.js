import React, { Component } from 'react';
import {
 Table, Col, Row, Container 
} from 'react-bootstrap';
import NavBlock from './NavBlock';
import StandardAside from './StandardAside';
import Navigation from './Navigation';
import FetchTypes from './FetchTypes';

class Users extends Component {
  constructor(props) {
    super(props);

    this.state = {
      users: [],
      user0: {},
      format: 'table',
      restUrl: `${window.$backendUrl}/petstore/user`
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
          users: data,
          user0: data[0]
        }));
  }

  changeType = (param) => {
    this.setState({
      format: param
    });
  };

  render() {
    const { 
      users, 
      user0, 
      format, 
      restUrl 
    } = this.state;

    switch (format) {
      case 'table':
        return (
          <div>
            <Container fluid>
              <Row>
                <Col>{NavBlock('Petstore application', 'Petstore users')}</Col>
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
                                {Object.entries(user0).map(([key]) => (
                                  <th key={key}>{key}</th>
                                ))}
                              </tr>
                            </thead>
                            <tbody>
                              {users.map((user) => (
                                <tr>
                                  <td>
                                    <a href={`/petstore/user/${user.username}`}>
                                      {user.username}
                                    </a>
                                  </td>
                                  <td>{user.firstName}</td>
                                  <td>{user.lastName}</td>
                                  <td>{user.userStatus}</td>
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

export default Users;
