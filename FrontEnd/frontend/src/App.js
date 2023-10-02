// English comments as per your preference
import React from 'react';
import './App.css';

function App() {

  // Define the function that will handle the button click
  const handleClick = async () => {
    try {
      // Make an HTTP GET request to your backend
      const response = await fetch('http://localhost:8080/start');
      if (response.ok) {
        const data = await response.json();
        console.log('Success:', data);
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
        <p>Hello, World!</p>
        {/* Attach the handleClick function to the button's onClick event */}
        <button onClick={handleClick}>Click Me!</button>
      </header>
    </div>
  );
}

export default App;
