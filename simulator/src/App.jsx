import React, { useState } from 'react';
import './App.scss';

import image1 from './1.png';
import image2 from './2.png';
import image3 from './3.png';

function App() {
  const [started, setStarted] = useState(false);
  const [lights, setLights] = useState({
    livingRoom: false, // z-4
    playRoom: false, // z-5
    kitchen: false, // z-6
    bedRoom: false, // z-1
    clothingRoom: false, // z-2
    guestBedRoom: false, // z-3
    outdoor: false // z-7
  });

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
        <div className="image-layer z-1" style={{ opacity: lights.bedRoom ? 1 : 0 }}>
          <img src={image2} alt="Bed Room Light" />
        </div>
        <div className="image-layer z-2" style={{ opacity: lights.clothingRoom ? 1 : 0 }}>
          <img src={image2} alt="Clothing Room Light" />
        </div>
        <div className="image-layer z-3" style={{ opacity: lights.guestBedRoom ? 1 : 0 }}>
          <img src={image2} alt="Guest Bed Room Light" />
        </div>
        <div className="image-layer z-4" style={{ opacity: lights.livingRoom ? 1 : 0 }}>
          <img src={image2} alt="Living Room Light" />
        </div>
        <div className="image-layer z-5" style={{ opacity: lights.playRoom ? 1 : 0 }}>
          <img src={image2} alt="Play Room Light" />
        </div>
        <div className="image-layer z-6" style={{ opacity: lights.kitchen ? 1 : 0 }}>
          <img src={image2} alt="Kitchen Light" />
        </div>
        <div className="image-layer z-7" style={{ opacity: lights.outdoor ? 1 : 0 }}>
          <img src={image3} alt="Outdoor Lighting" />
        </div>
      </div>

      {/* Home Setting Section */}
      <div className="setting-section">
        <div className="setting-content">
          <div className="settings-container">
            <div className="header-section">
              <h2>Light Settings</h2>
              <p>Controll your all lights in one place</p>
            </div>
            
            <div className="toggles-section">
              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.livingRoom} onChange={(e) => setLights({...lights, livingRoom: e.target.checked})} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Living room light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.playRoom} onChange={(e) => setLights({...lights, playRoom: e.target.checked})} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Play room light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.kitchen} onChange={(e) => setLights({...lights, kitchen: e.target.checked})} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Kitchen Light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.bedRoom} onChange={(e) => setLights({...lights, bedRoom: e.target.checked})} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Bed Room Light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.clothingRoom} onChange={(e) => setLights({...lights, clothingRoom: e.target.checked})} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Clothing Room Light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.guestBedRoom} onChange={(e) => setLights({...lights, guestBedRoom: e.target.checked})} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">guest bed room light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.outdoor} onChange={(e) => setLights({...lights, outdoor: e.target.checked})} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Outdoor lighting</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
