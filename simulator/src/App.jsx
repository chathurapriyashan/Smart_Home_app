import React, { useState } from 'react';
import './App.scss';

import image1 from './1.png';
import image2 from './2.png';
import image3 from './3.png';
import lightsLines from './lights-lines.png';
import switchingLines from './switching-lines.png';
import outdoorLines from './outdoor-lines.png';
import { IoMdSwitch } from 'react-icons/io';
import { RxSwitch } from 'react-icons/rx';
import { BiCctv } from 'react-icons/bi';
import { FaSnowflake } from 'react-icons/fa';
import { MdIron } from 'react-icons/md';

function App() {
  const [started, setStarted] = useState(false);
  const [isWirelinesHovered, setIsWirelinesHovered] = useState(false);
  const [mainSwitchOn, setMainSwitchOn] = useState(true);
  const [lights, setLights] = useState({
    livingRoom: false, // z-4
    playRoom: false, // z-5
    kitchen: false, // z-6
    bedRoom: false, // z-1
    clothingRoom: false, // z-2
    guestBedRoom: false, // z-3
    outdoor: false // z-7
  });
  const [wallSwitches, setWallSwitches] = useState({
    9: false, 10: false, 11: false, 12: false, 13: false, 14: false, 15: false, 16: false,
    17: false, 18: false, 19: false, 21: false
  });
  const [savedState, setSavedState] = useState({ lights: null, wallSwitches: null });

  const handleLightToggle = (key, value) => {
    if (mainSwitchOn) {
      setLights(prev => ({ ...prev, [key]: value }));
    }
  };

  const handleWallSwitchToggle = (id, value) => {
    if (mainSwitchOn) {
      setWallSwitches(prev => ({ ...prev, [id]: value }));
    }
  };

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
        {[
          { id: 1, key: 'bedRoom', label: 'Bed Room' },
          { id: 2, key: 'clothingRoom', label: 'Clothing Room' },
          { id: 3, key: 'guestBedRoom', label: 'Guest Room' },
          { id: 4, key: 'livingRoom', label: 'Living Room' },
          { id: 5, key: 'playRoom', label: 'Play Room' },
          { id: 6, key: 'kitchen', label: 'Kitchen' },
          { id: 7, key: 'outdoor', label: 'Outdoor' }
        ].map(badge => (
          <div key={badge.id} className={`status-badge status-badge-${badge.id}`}>
            <div className="badge-header">
              <div className={`status-dot ${lights[badge.key] ? 'flashing-green' : ''}`}></div>
              <div className="status-text">
                <svg className="bulb-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <path d="M15 14c.2-1 .7-1.7 1.5-2.5 1-.9 1.5-2.2 1.5-3.5A6 6 0 0 0 6 8c0 1 .2 2.2 1.5 3.5.7.9 1.3 1.5 1.5 2.5"/>
                  <path d="M9 18h6"/>
                  <path d="M10 22h4"/>
                </svg>
                {lights[badge.key] ? 'On' : 'Off'}
              </div>
            </div>
            <div className="badge-content">
              <label className="switch">
                <input type="checkbox" checked={lights[badge.key]} onChange={(e) => handleLightToggle(badge.key, e.target.checked)} />
                <span className="slider round"></span>
              </label>
              <span className="toggle-label">{badge.label}</span>
            </div>
          </div>
        ))}

        {[
          { id: 9, label: 'Bed room Switch', Icon: RxSwitch },
          { id: 10, label: 'Cloths room Switch', Icon: RxSwitch },
          { id: 11, label: 'Guest room Switch 1', Icon: RxSwitch },
          { id: 12, label: 'Guest room Switch 2', Icon: RxSwitch },
          { id: 13, label: 'Guest room Switch 2', Icon: RxSwitch },
          { id: 14, label: 'Kitchen Switch', Icon: RxSwitch },
          { id: 15, label: 'Play Room Switch', Icon: RxSwitch },
          { id: 16, label: 'Living Room Switch', Icon: RxSwitch },
          { id: 17, label: 'CCTV', Icon: BiCctv },
          { id: 18, label: 'AC 1', Icon: FaSnowflake },
          { id: 19, label: 'AC 2', Icon: FaSnowflake },
          { id: 21, label: 'Iron', Icon: MdIron }
        ].map(badge => (
          <div key={badge.id} className={`status-badge status-badge-${badge.id}`}>
            <div className="badge-header">
              <div className={`status-dot ${wallSwitches[badge.id] ? 'flashing-green' : ''}`}></div>
              <div className="status-text">
                <badge.Icon className="bulb-icon" />
                {wallSwitches[badge.id] ? 'On' : 'Off'}
              </div>
            </div>
            <div className="badge-content">
              <label className="switch">
                <input type="checkbox" checked={wallSwitches[badge.id]} onChange={(e) => handleWallSwitchToggle(badge.id, e.target.checked)} />
                <span className="slider round"></span>
              </label>
              <span className="toggle-label">{badge.label}</span>
            </div>
          </div>
        ))}

        {/* Wirelines / Master Badge */}
        <div 
          className="status-badge status-badge-8" 
          onMouseEnter={() => setIsWirelinesHovered(true)}
          onMouseLeave={() => setIsWirelinesHovered(false)}
        >
          <div className="badge-header">
            <div className={`status-dot ${mainSwitchOn ? 'flashing-green' : ''}`}></div>
            <div className="status-text">
              <IoMdSwitch className="bulb-icon" />
              {mainSwitchOn ? 'On' : 'Off'}
            </div>
          </div>
          <div className="badge-content">
            <label className="switch">
              <input 
                type="checkbox" 
                checked={mainSwitchOn} 
                onChange={(e) => {
                  const isOn = e.target.checked;
                  setMainSwitchOn(isOn);
                  if (!isOn) {
                    setSavedState({ lights, wallSwitches });
                    setLights({
                      livingRoom: false,
                      playRoom: false,
                      kitchen: false,
                      bedRoom: false,
                      clothingRoom: false,
                      guestBedRoom: false,
                      outdoor: false
                    });
                    setWallSwitches({ 
                      9: false, 10: false, 11: false, 12: false, 13: false, 14: false, 15: false, 16: false,
                      17: false, 18: false, 19: false, 21: false 
                    });
                  } else {
                    if (savedState.lights && savedState.wallSwitches) {
                      setLights(savedState.lights);
                      setWallSwitches(savedState.wallSwitches);
                    }
                  }
                }} 
              />
              <span className="slider round"></span>
            </label>
            <span className="toggle-label">Main Tip Switch</span>
          </div>
        </div>

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
        <div className="image-layer z-8" style={{ opacity: isWirelinesHovered ? 1 : 0, transition: 'opacity 0.4s ease' }}>
          <img src={lightsLines} alt="Lights Lines" />
        </div>
        <div className="image-layer z-9" style={{ opacity: isWirelinesHovered ? 1 : 0, transition: 'opacity 0.4s ease' }}>
          <img src={switchingLines} alt="Switching Lines" />
        </div>
        <div className="image-layer z-10" style={{ opacity: isWirelinesHovered ? 1 : 0, transition: 'opacity 0.4s ease' }}>
          <img src={outdoorLines} alt="Outdoor Lines" />
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
                  <input type="checkbox" checked={lights.livingRoom} onChange={(e) => handleLightToggle('livingRoom', e.target.checked)} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Living room light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.playRoom} onChange={(e) => handleLightToggle('playRoom', e.target.checked)} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Play room light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.kitchen} onChange={(e) => handleLightToggle('kitchen', e.target.checked)} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Kitchen Light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.bedRoom} onChange={(e) => handleLightToggle('bedRoom', e.target.checked)} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Bed Room Light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.clothingRoom} onChange={(e) => handleLightToggle('clothingRoom', e.target.checked)} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Clothing Room Light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.guestBedRoom} onChange={(e) => handleLightToggle('guestBedRoom', e.target.checked)} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Guest Bed Room Light</span>
              </div>

              <div className="toggle-item">
                <label className="switch">
                  <input type="checkbox" checked={lights.outdoor} onChange={(e) => handleLightToggle('outdoor', e.target.checked)} />
                  <span className="slider round"></span>
                </label>
                <span className="toggle-label">Outdoor Lighting</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
