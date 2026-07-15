import React, { useState } from 'react';
import './App.scss';

import image1 from './1.png';
import image2 from './2.png';
import image3 from './3.png';

function App() {
  const [started, setStarted] = useState(false);

  return (
    <div className="app-container">
      {/* Loading Screen */}
      <div className={`loading-screen ${started ? 'started' : ''}`}>
        <div className="panel panel-1"></div>
        <div className="panel panel-2"></div>
        <div className="panel panel-3"></div>
        <div className="panel panel-4"></div>
        <div className="panel panel-5"></div>
        <div className="panel panel-6"></div>
        
        {!started && (
          <button className="start-button" onClick={() => setStarted(true)}>
            Start
          </button>
        )}
      </div>

      {/* Simulator Section */}
      <div className="simulator-section">
        <div className="image-layer z-0">
          <img src={image1} alt="Layer 0" />
        </div>
        <div className="image-layer z-1">
          <img src={image2} alt="Layer 1" />
        </div>
        <div className="image-layer z-2">
          <img src={image2} alt="Layer 2" />
        </div>
        <div className="image-layer z-3">
          <img src={image2} alt="Layer 3" />
        </div>
        <div className="image-layer z-4">
          <img src={image2} alt="Layer 4" />
        </div>
        <div className="image-layer z-5">
          <img src={image2} alt="Layer 5" />
        </div>
        <div className="image-layer z-6">
          <img src={image2} alt="Layer 6" />
        </div>
        <div className="image-layer z-7">
          <img src={image3} alt="Layer 7" />
        </div>
      </div>

      {/* Home Setting Section */}
      <div className="setting-section">
        <div className="setting-content">
          <h1>Smart Home Settings</h1>
          <p>Configure your smart home parameters and lighting settings.</p>
          {/* Settings controls would go here */}
        </div>
      </div>
    </div>
  );
}

export default App;
