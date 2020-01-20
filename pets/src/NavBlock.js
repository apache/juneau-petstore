import React from 'react';
import logo from './cat.png';

const NavBlock = (title, page) => (
  <header className="App-header">
    <h1>{title}</h1> 
    <h2>{page}</h2>
    <a href="http://swagger.io">
      <img src={logo} alt="logo" className="logo" />
    </a>
  </header>
  );
  
  export default NavBlock;
