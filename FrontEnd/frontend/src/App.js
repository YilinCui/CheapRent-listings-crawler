import React, { useState } from 'react';
import Button from './Component/Button';
import TextArea from './Component/TextArea'; 
import './App.css';
import { apiIP } from './Configuration/config';

function App() {
  const [fetchedData, setFetchedData] = useState(null);

  const handleClick = async (apiEndpoint) => {
    console.log("NODE_ENV:", process.env.NODE_ENV);
    try {
      const response = await fetch(`${apiIP}${apiEndpoint}`);
      if (response.ok) {
        const data = await response.json();
        console.log('Success:', data);
        setFetchedData(data);
      } else {
        console.log('HTTP-Error:', response.status);
      }
    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
    }
  };


  return (
    <div className="App">
      <header className="App-header">
        <div className="button-container">
          <button onClick={() => handleClick('start')}>Start Crawling</button>
          <div className="middle-buttons">
            <button onClick={() => handleClick('')}>All of the Data</button>
            <button onClick={() => handleClick('cheapest')}>Cheapest Rent</button>
            <button onClick={() => handleClick('asc')}>in ASC order</button>
            <button onClick={() => handleClick('desc')}>in DESC order</button>
            <button onClick={() => handleClick('studio')}>Search for studio</button>
          </div>
        </div>
        <div className="textarea-container">
          <textarea readOnly value={fetchedData ? JSON.stringify(fetchedData, null, 2) : ''} />
        </div>
      </header>
    </div>
  );


}

export default App;
