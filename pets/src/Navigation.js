import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';

class Navigation extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  render() {
    const {
      upLink,
      ahref,
      changeType    
    } = this.props;

    return (
      <div>
        
        <Navbar expand="lg">
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
              {upLink ? <Nav.Link href={ahref}>Up</Nav.Link> : null}

              <Nav.Link href="/options">Options</Nav.Link>

              <NavDropdown title="Content-type" id="basic-nav-dropdown">
                <NavDropdown.Item onClick={() => changeType('json')}>
                  APPLICATION/JSON
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('schema')}>
                  APPLICATION/JSON+SCHEMA
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('urlEncoded')}>
                  APPLICATION/X-WWW-FORM-URLENCODED
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('octal')}>
                  OCTAL/MSGPACK
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('texthtml')}>
                  TEXT/HTML
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('htmlschema')}>
                  TEXT/HTML+SCHEMA
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('htmlstripped')}>
                  TEXT/HTML+STRIPPED
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('openapi')}>
                  TEXT/OPENAPI
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('textplain')}>
                  TEXT/PLAIN
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('textuon')}>
                  TEXT/UON
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('textxml')}>
                  TEXT/XML
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('textxmlschema')}>
                  TEXT/XML+SCHEMA
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => changeType('textxmlsoap')}>
                  TEXT/XML+SOAP
                </NavDropdown.Item>
              </NavDropdown>
              <Nav.Link href="https://github.com/apache/juneau/blob/master/juneau-examples/juneau-examples-rest/src/main/java/org/apache/juneau/petstore/rest/RootResources.java">
                Source
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
      </div>
    );
  }
}

Navigation.propTypes = {
  upLink: PropTypes.bool.isRequired,
  ahref: PropTypes.string.isRequired,
  changeType: PropTypes.func.isRequired,
  
};

export default Navigation;
