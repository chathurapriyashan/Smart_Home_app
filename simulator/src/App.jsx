import React, { useState, useEffect, useRef } from 'react';
import './App.scss';

import image1 from './1.png';
import image2 from './2.png';
import image3 from './3.png';
import image4 from './4.png';
import lightsLines from './lights-lines.png';
import switchingLines from './switching-lines.png';
import outdoorLines from './outdoor-lines.png';
import { IoMdSwitch } from 'react-icons/io';
import { RxSwitch } from 'react-icons/rx';
import { BiCctv } from 'react-icons/bi';
import { FaSnowflake } from 'react-icons/fa';
import { MdIron } from 'react-icons/md';
import { db } from './firebase';
import { doc, onSnapshot, setDoc } from "firebase/firestore";
import toast, { Toaster } from 'react-hot-toast';

const documentId = "rPZCGX1DhxBMFmmk8cOH";
const documentCollection = "Devices";

function App() {
  const [started, setStarted] = useState(false);
  const [isWirelinesHovered, setIsWirelinesHovered] = useState(false);
  const [appStatus, setAppStatus] = useState(null);

  // Iron Timer State
  const [ironTimeLeft, setIronTimeLeft] = useState(0);
  const [isIronTimerHovered, setIsIronTimerHovered] = useState(false);
  const [editTimeValue, setEditTimeValue] = useState("");
  const wasIronOn = useRef(false);

  useEffect(() => {
    const unsub = onSnapshot(doc(db, documentCollection, documentId), (docSnapshot) => {
      const source = docSnapshot.metadata.hasPendingWrites ? "Local" : "Server";
      console.log(source, " data: ", docSnapshot.data());
      setAppStatus(docSnapshot.data());
    });
    return () => unsub();
  }, []);

  // Initialize Iron Timer on toggle
  useEffect(() => {
    if (appStatus) {
      if (appStatus.f_cloth_rm_iron && !wasIronOn.current) {
        setIronTimeLeft(appStatus.iron_safe_max_duration || 60);
      } else if (!appStatus.f_cloth_rm_iron) {
        setIronTimeLeft(appStatus.iron_safe_max_duration || 60); // Reset display to max when off
      }
      wasIronOn.current = appStatus.f_cloth_rm_iron;
    }
  }, [appStatus?.f_cloth_rm_iron, appStatus?.iron_safe_max_duration]);

  // Countdown effect
  useEffect(() => {
    let interval;
    if (appStatus?.f_cloth_rm_iron && ironTimeLeft > 0) {
      interval = setInterval(() => {
        setIronTimeLeft((prev) => prev - 1);
      }, 1000);
    } else if (appStatus?.f_cloth_rm_iron && ironTimeLeft === 0 && wasIronOn.current) {
      // Auto turn off
      handleStatusChange('f_cloth_rm_iron', false);
    }
    return () => clearInterval(interval);
  }, [appStatus?.f_cloth_rm_iron, ironTimeLeft]);

  const formatTime = (seconds) => {
    const m = Math.floor(seconds / 60).toString().padStart(2, '0');
    const s = (seconds % 60).toString().padStart(2, '0');
    return `${m}:${s}`;
  };

  const handleSaveIronTime = async () => {
    const val = parseInt(editTimeValue);
    if (!isNaN(val) && val > 0) {
      try {
        const deviceRef = doc(db, documentCollection, documentId);
        await setDoc(deviceRef, { iron_safe_max_duration: val }, { merge: true });
        setIronTimeLeft(val);
        setIsIronTimerHovered(false);
        toast.success("Timer updated!");
      } catch (error) {
        console.error(error);
        toast.error("Failed to update timer!");
      }
    }
  };

  const handleStatusChange = async (key, value) => {
    if (!appStatus) return;
    
    // Safety check: if main switch is off, only allow turning on the main switch
    if (key !== 'main_switch' && !appStatus.main_switch) {
      toast.error("Main Tip Switch is turned off!");
      return;
    }

    try {
      const deviceRef = doc(db, documentCollection, documentId);
      
      if (key === 'main_switch' && !value) {
        // Turning off main switch: set all lights and switches to false
        await setDoc(deviceRef, {
          ...appStatus,
          main_switch: false,
          f_bed_rm_ac: false,
          f_bed_rm_light: false,
          f_bed_rm_switch: false,
          f_cloth_rm_iron: false,
          f_cloth_rm_light: false,
          f_cloth_rm_switch: false,
          f_guest_rm_light: false,
          f_guest_rm_switch_1: false,
          f_guest_rm_switch_2: false,
          g_kitchen_rm_light: false,
          g_kitchen_rm_switch: false,
          g_living_rm_ac: false,
          g_living_rm_light: false,
          g_living_rm_switch: false,
          g_play_rm_switch: false,
          g_playing_rm_light: false,
          outdoor_light: false,
          cctv: false
        }, { merge: true });
      } else {
        // Normal toggle
        await setDoc(deviceRef, { [key]: value }, { merge: true });
      }
    } catch (error) {
      console.error(error);
      toast.error("Operation failed!");
    }
  };

  return (
    <div className="app-container">
      <Toaster position="top-right" />
      {/* Loading Screen */}
      <div className={`loading-screen ${started ? 'started' : ''}`}>
        <div className="panel panel-1"></div>
        <div className="panel panel-2"></div>
        <div className="panel panel-3"></div>
        <div className="panel panel-4"></div>
        <div className="panel panel-5"></div>
        <div className="panel panel-6"></div>

        {!appStatus ? (
          <div style={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', textAlign: 'center', zIndex: 10 }}>
            <div className="loading-spinner"></div>
            <div style={{ color: '#fff', fontSize: '1.4rem' }}>Connecting to Home Server...</div>
          </div>
        ) : (
          !started && (
            <button className="start-button" onClick={() => setStarted(true)}>
              Start Simulator
            </button>
          )
        )}
      </div>

      {/* Simulator Section */}
      <div className="simulator-section">
        {appStatus && [
          { id: 1, key: 'f_bed_rm_light', label: 'Bed Room' },
          { id: 2, key: 'f_cloth_rm_light', label: 'Clothing Room' },
          { id: 3, key: 'f_guest_rm_light', label: 'Guest Room' },
          { id: 4, key: 'g_living_rm_light', label: 'Living Room' },
          { id: 5, key: 'g_playing_rm_light', label: 'Play Room' },
          { id: 6, key: 'g_kitchen_rm_light', label: 'Kitchen' },
          { id: 7, key: 'outdoor_light', label: 'Outdoor' }
        ].map(badge => (
          <div key={badge.id} className={`status-badge status-badge-${badge.id}`}>
            <div className="badge-header">
              <div className={`status-dot ${appStatus[badge.key] ? 'flashing-green' : ''}`}></div>
              <div className="status-text">
                <svg className="bulb-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <path d="M15 14c.2-1 .7-1.7 1.5-2.5 1-.9 1.5-2.2 1.5-3.5A6 6 0 0 0 6 8c0 1 .2 2.2 1.5 3.5.7.9 1.3 1.5 1.5 2.5" />
                  <path d="M9 18h6" />
                  <path d="M10 22h4" />
                </svg>
                {appStatus[badge.key] ? 'On' : 'Off'}
              </div>
            </div>
            <div className="badge-content">
              <label className="switch">
                <input type="checkbox" checked={appStatus[badge.key] || false} onChange={(e) => handleStatusChange(badge.key, e.target.checked)} />
                <span className="slider round"></span>
              </label>
              <span className="toggle-label">{badge.label}</span>
            </div>
          </div>
        ))}

        {appStatus && [
          { id: 9, label: 'Bed room Switch', Icon: RxSwitch, key: 'f_bed_rm_switch' },
          { id: 10, label: 'Cloths room Switch', Icon: RxSwitch, key: 'f_cloth_rm_switch' },
          { id: 11, label: 'Guest room Switch 1', Icon: RxSwitch, key: 'f_guest_rm_switch_1' },
          { id: 12, label: 'Guest room Switch 2', Icon: RxSwitch, key: 'f_guest_rm_switch_2' },
          { id: 14, label: 'Kitchen Switch', Icon: RxSwitch, key: 'g_kitchen_rm_switch' },
          { id: 15, label: 'Play Room Switch', Icon: RxSwitch, key: 'g_play_rm_switch' },
          { id: 16, label: 'Living Room Switch', Icon: RxSwitch, key: 'g_living_rm_switch' },
          { id: 17, label: 'CCTV', Icon: BiCctv, key: 'cctv' },
          { id: 18, label: 'AC 1', Icon: FaSnowflake, key: 'f_bed_rm_ac' },
          { id: 19, label: 'AC 2', Icon: FaSnowflake, key: 'g_living_rm_ac' }
        ].map(badge => (
          <div key={badge.id} className={`status-badge status-badge-${badge.id}`}>
            <div className="badge-header">
              <div className={`status-dot ${appStatus[badge.key] ? 'flashing-green' : ''}`}></div>
              <div className="status-text">
                <badge.Icon className="bulb-icon" />
                {appStatus[badge.key] ? 'On' : 'Off'}
              </div>
            </div>
            <div className="badge-content">
              <label className="switch">
                <input type="checkbox" checked={appStatus[badge.key] || false} onChange={(e) => handleStatusChange(badge.key, e.target.checked)} />
                <span className="slider round"></span>
              </label>
              <span className="toggle-label">{badge.label}</span>
            </div>
          </div>
        ))}

        {/* Special Iron Badge (Badge 21) */}
        {appStatus && (
          <div className="status-badge status-badge-21">
            <div className="badge-header">
              <div className={`status-dot ${appStatus.f_cloth_rm_iron ? 'flashing-green' : ''}`}></div>
              <div className="status-text">
                <MdIron className="bulb-icon" />
                {appStatus.f_cloth_rm_iron ? 'On' : 'Off'}
              </div>
            </div>
            <div className="badge-content">
              <label className="switch">
                <input type="checkbox" checked={appStatus.f_cloth_rm_iron || false} onChange={(e) => handleStatusChange('f_cloth_rm_iron', e.target.checked)} />
                <span className="slider round"></span>
              </label>
              <span className="toggle-label">Iron</span>
            </div>
            <div 
              className="iron-timer-container"
              onMouseEnter={() => {
                setEditTimeValue(appStatus.iron_safe_max_duration || 60);
                setIsIronTimerHovered(true);
              }}
              onMouseLeave={() => setIsIronTimerHovered(false)}
            >
              {formatTime(ironTimeLeft)}
              
              {isIronTimerHovered && (
                <div className="iron-timer-popup">
                  <input 
                    type="number" 
                    value={editTimeValue} 
                    onChange={(e) => setEditTimeValue(e.target.value)}
                    placeholder="Secs"
                  />
                  <button onClick={handleSaveIronTime}>Save</button>
                </div>
              )}
            </div>
          </div>
        )}

        {/* Wirelines / Master Badge */}
        {appStatus && (
          <div
            className="status-badge status-badge-8"
            onMouseEnter={() => setIsWirelinesHovered(true)}
            onMouseLeave={() => setIsWirelinesHovered(false)}
          >
            <div className="badge-header">
              <div className={`status-dot ${appStatus.main_switch ? 'flashing-green' : ''}`}></div>
              <div className="status-text">
                <IoMdSwitch className="bulb-icon" />
                {appStatus.main_switch ? 'On' : 'Off'}
              </div>
            </div>
            <div className="badge-content">
              <label className="switch">
                <input
                  type="checkbox"
                  checked={appStatus.main_switch || false}
                  onChange={(e) => handleStatusChange('main_switch', e.target.checked)}
                />
                <span className="slider round"></span>
              </label>
              <span className="toggle-label">Main Tip Switch</span>
            </div>
          </div>
        )}

        <div className="image-layer z-0">
          <img src={image1} alt="Layer 0" />
        </div>
        <div className="image-layer z-1" style={{ opacity: appStatus?.f_bed_rm_light ? 1 : 0 }}>
          <img src={image2} alt="Bed Room Light" />
        </div>
        <div className="image-layer z-2" style={{ opacity: appStatus?.f_cloth_rm_light ? 1 : 0 }}>
          <img src={image2} alt="Clothing Room Light" />
        </div>
        <div className="image-layer z-3" style={{ opacity: appStatus?.f_guest_rm_light ? 1 : 0 }}>
          <img src={image2} alt="Guest Bed Room Light" />
        </div>
        <div className="image-layer z-4" style={{ opacity: appStatus?.g_living_rm_light ? 1 : 0 }}>
          <img src={image2} alt="Living Room Light" />
        </div>
        <div className="image-layer z-5" style={{ opacity: appStatus?.g_playing_rm_light ? 1 : 0 }}>
          <img src={image2} alt="Play Room Light" />
        </div>
        <div className="image-layer z-6" style={{ opacity: appStatus?.g_kitchen_rm_light ? 1 : 0 }}>
          <img src={image2} alt="Kitchen Light" />
        </div>
        <div className="image-layer z-7" style={{ opacity: appStatus?.outdoor_light ? 1 : 0 }}>
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
        <div className="image-layer z-11" style={{ opacity: appStatus?.f_bed_rm_ac ? 0.3 : 0, transition: 'opacity 0.4s ease' }}>
          <img src={image4} alt="Layer 11" />
        </div>
        <div className="image-layer z-12" style={{ opacity: appStatus?.g_living_rm_ac ? 0.3 : 0, transition: 'opacity 0.4s ease' }}>
          <img src={image4} alt="Layer 12" />
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

            {appStatus && (
              <div className="toggles-section">
                <div className="toggle-item">
                  <label className="switch">
                    <input type="checkbox" checked={appStatus.g_living_rm_light || false} onChange={(e) => handleStatusChange('g_living_rm_light', e.target.checked)} />
                    <span className="slider round"></span>
                  </label>
                  <span className="toggle-label">Living room light</span>
                </div>

                <div className="toggle-item">
                  <label className="switch">
                    <input type="checkbox" checked={appStatus.g_playing_rm_light || false} onChange={(e) => handleStatusChange('g_playing_rm_light', e.target.checked)} />
                    <span className="slider round"></span>
                  </label>
                  <span className="toggle-label">Play room light</span>
                </div>

                <div className="toggle-item">
                  <label className="switch">
                    <input type="checkbox" checked={appStatus.g_kitchen_rm_light || false} onChange={(e) => handleStatusChange('g_kitchen_rm_light', e.target.checked)} />
                    <span className="slider round"></span>
                  </label>
                  <span className="toggle-label">Kitchen Light</span>
                </div>

                <div className="toggle-item">
                  <label className="switch">
                    <input type="checkbox" checked={appStatus.f_bed_rm_light || false} onChange={(e) => handleStatusChange('f_bed_rm_light', e.target.checked)} />
                    <span className="slider round"></span>
                  </label>
                  <span className="toggle-label">Bed Room Light</span>
                </div>

                <div className="toggle-item">
                  <label className="switch">
                    <input type="checkbox" checked={appStatus.f_cloth_rm_light || false} onChange={(e) => handleStatusChange('f_cloth_rm_light', e.target.checked)} />
                    <span className="slider round"></span>
                  </label>
                  <span className="toggle-label">Clothing Room Light</span>
                </div>

                <div className="toggle-item">
                  <label className="switch">
                    <input type="checkbox" checked={appStatus.f_guest_rm_light || false} onChange={(e) => handleStatusChange('f_guest_rm_light', e.target.checked)} />
                    <span className="slider round"></span>
                  </label>
                  <span className="toggle-label">Guest Bed Room Light</span>
                </div>

                <div className="toggle-item">
                  <label className="switch">
                    <input type="checkbox" checked={appStatus.outdoor_light || false} onChange={(e) => handleStatusChange('outdoor_light', e.target.checked)} />
                    <span className="slider round"></span>
                  </label>
                  <span className="toggle-label">Outdoor Lighting</span>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
