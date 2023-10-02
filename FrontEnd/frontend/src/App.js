import React, { useState } from 'react'; // Import useState hook
import './App.css';

function App() {
  // Initialize state to hold the fetched data
  const [fetchedData, setFetchedData] = useState(null);

  // Define the function that will handle the button click
  const handleClick = async () => {
    try {
      // Make an HTTP GET request to your backend
      const response = await fetch('http://localhost:8080/rental/');
      if (response.ok) {
        const data = await response.json();
        console.log('Success:', data);

        // Set the fetched data into state
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
        <p>Hello, World!</p>
        {/* Attach the handleClick function to the button's onClick event */}
        <button onClick={handleClick}>Click Me!</button>

        {/* Display the fetched data */}
        {fetchedData && (
          <div className="data-container">
            <h2>Fetched Data:</h2>
            <pre>{JSON.stringify(fetchedData, null, 2)}</pre>
          </div>
        )}
      </header>
    </div>
  );
}

export default App;
