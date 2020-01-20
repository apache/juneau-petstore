import React, { Component } from 'react';
import PropTypes from 'prop-types';

class FetchTypes extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: ''
    };
  }

  componentDidMount() {
    this.getData();
  }

  getData() {
    const { fetchContent } = this.props;
    fetch(fetchContent)
      .then((response) => response.text())
      .then((data) =>
        this.setState({
          content: data
        }));
  }
   
  render() {
    const { content } = this.state;
    return (
      <div>
        <pre>{content}</pre>
      </div>
    );
  }
}

FetchTypes.propTypes = {
  fetchContent: PropTypes.string.isRequired
};

export default FetchTypes;
