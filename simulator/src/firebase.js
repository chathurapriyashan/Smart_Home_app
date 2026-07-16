// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { doc, getDoc, getFirestore } from "firebase/firestore";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyBAFEEO2jfJNOUSddEaCjG04c83fzfFhmU",
  authDomain: "smart-home-app-edfb0.firebaseapp.com",
  projectId: "smart-home-app-edfb0",
  storageBucket: "smart-home-app-edfb0.firebasestorage.app",
  messagingSenderId: "686413603040",
  appId: "1:686413603040:web:0463f29513a5e3bd2fb085",
  measurementId: "G-VNTJH73PDG"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const db = getFirestore(app);


export { app, analytics, db };
