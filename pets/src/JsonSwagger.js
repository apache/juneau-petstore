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
