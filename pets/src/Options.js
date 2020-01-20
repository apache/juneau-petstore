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
