import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {
  Table, Col, Row, Container 
 } from 'react-bootstrap';
 import NavBlock from './NavBlock';
import StandardAside from './StandardAside';
import Navigation from './Navigation';
import FetchTypes from './FetchTypes';

class UserDetails extends Component {
  constructor(props) {
    super(props);
    const {
      match: { params }
    } = this.props;
    this.state = {
      user: {},   
      format: 'table',
      restUrl: `${window.$backendUrl}/petstore/user/${params.username}`
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
          user: data,
         
        }));
  }

  changeType= (param) => {
    this.setState({
       format: param
     });
 }
  
  render() {
    const { user, format, restUrl } = this.state;
    switch (format) {
      case 'table':
        return (
          <div>
            <Container fluid> 
              <Row> 
                <Col>
                  {NavBlock('Petstore application', 'Get user by user name')}
                </Col>
              </Row>
              <Row> 
                <Col>
                  <Navigation
                    upLink 
                    ahref="/petstore/user" 
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
                              {Object.entries(user).map(([key, value]) => (
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
            <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=application%2Fx-www-form-urlencoded`} /> 
          </div>
        );
        case 'octal':
        return (
          <div>
            <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=octal%2Fmsgpack`} /> 
          </div>
        );
        case 'texthtml':
        return (
          <div>
            <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fhtml`} /> 
          </div>
        );
        case 'htmlschema':
        return (
          <div>
            <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fhtml%2Bschema`} /> 
          </div>
        );
        case 'htmlstripped':
        return (
          <div>
            <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fhtml%2Bstripped`} /> 
          </div>
        );
        case 'openapi':
          return (
            <div>
              <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fopenapi`} /> 
            </div>
          );
          case 'textplain':
          return (
            <div>
              <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fplain`} /> 
            </div>
          );
          case 'textuon':
          return (
            <div>
              <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fuon`} /> 
            </div>
          );
          case 'textxml':
          return (
            <div>
              <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fxml`} /> 
            </div>
          );
          case 'textxmlschema':
          return (
            <div>
              <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fxml%2Bschema`} /> 
            </div>
          );
          case 'textxmlsoap':
          return (
            <div>
              <FetchTypes fetchContent={`${restUrl}?plainText=true&Accept=text%2Fxml%2Bsoap`} /> 
            </div>
          );
      default:
        return null;
    }
  }
}

UserDetails.propTypes = { 
  match: PropTypes.shape({
    params: PropTypes.shape({
      username: PropTypes.node,
    }).isRequired,
  }).isRequired  
};

export default UserDetails;
