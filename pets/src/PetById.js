import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {
  Table, Col, Row, Container 
 } from 'react-bootstrap';
 import NavBlock from './NavBlock';
import StandardAside from './StandardAside';
import Navigation from './Navigation';
import FetchTypes from './FetchTypes';

class PetById extends Component {
  constructor(props) {
    super(props);
    const {
      match: { params }
    } = this.props; 
   
    this.state = {
      pet: {},   
      tags: [],
      format: 'table',
      restUrl: `${window.$backendUrl}/petstore/pet/${params.id}`
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
          pet: data,
          tags: data.tags
        }));
  }

  changeType= (param) => {
    this.setState({
       format: param
     });
 }
  
  render() {
    const { 
      pet, 
      tags, 
      format, 
      restUrl
    } = this.state;
    
    switch (format) {
      case 'table':
        return (
          <div>
            <Container fluid>
              <Row> 
                <Col> 
                  {NavBlock('Petstore application', 'Find pet by ID')}
                </Col>
              </Row>
              <Row> 
                <Col>
                  <Navigation
                    upLink 
                    ahref="/petstore/pet" 
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
                      
                              <tr>
                                <th>{Object.keys(pet)[0]}</th>
                                <td>{Object.values(pet)[0]}</td>
                              </tr>

                              <tr>
                                <th>{Object.keys(pet)[1]}</th>
                                <td>{Object.values(pet)[1]}</td>
                              </tr>

                              <tr>
                                <th>{Object.keys(pet)[2]}</th>
                                <td>{Object.values(pet)[2]}</td>
                              </tr>
                              <tr>
                                <th>{Object.keys(pet)[3]}</th>
                                <td>
                                  <ul>
                                    {tags.map((value, index) => (
                            // eslint-disable-next-line react/no-array-index-key
                                      <li key={index}>
                                        {value}
                                      </li>
                          ))}
                                  </ul>
                                </td>
                              </tr>
                              <tr>
                                <th>{Object.keys(pet)[4]}</th>
                                <td>{Object.values(pet)[4]}</td>
                              </tr>
                              <tr>
                                <th>{Object.keys(pet)[5]}</th>
                                <td>{Object.values(pet)[5]}</td>
                              </tr>
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

PetById.propTypes = {
 
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.node
    }).isRequired
  }).isRequired
};

export default PetById;
