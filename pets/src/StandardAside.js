import React from 'react';
import './App.css';

const StandardAside = () => (
  <aside>
    <div className="text">
      <p>This page shows a standard nested REST resource.</p>
      <p>
      It shows how different properties can be rendered on the same bean
      in different views.
      </p>
      <p>
      It also shows examples of HtmlRender classes and
      @BeanProperty(format) annotations.
      </p>
      <p>
      It also shows how the Queryable converter and query widget can be
      used to create searchable interfaces.
      </p>
    </div>
  </aside>
  );
  
  export default StandardAside;
