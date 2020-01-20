import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { BrowserRouter } from 'react-router-dom';
import App from './App';

window.$backendUrl = 'http://localhost:5000';
window.$frontendUrl = 'http://localhost:3000';

ReactDOM.render(<BrowserRouter><App /></BrowserRouter>, document.getElementById('root'));
