import React from 'react';
import logo from './logo.svg';
import './App.css';

function App() {

    function detectLocation() {
        navigator.geolocation.getCurrentPosition(showPosition)
    }

    function showPosition(position) {
        deltectLocation(position.coords.latitude, position.coords.longitude)
    }

    function deltectLocation(latitude, longitude) {
        return fetch(`https://192.168.99.102/api/location/save?latitude=${latitude}&longitude=${longitude}`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
        })
            .then((response) => response.json())
            .then((responseJson) => {
                document.getElementById('location_result').innerText = responseJson.location;
                return responseJson.location;
            })
            .catch((error) => {
                console.error(error);
            });
    }

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                <button
                    className="App-link"
                    onClick={detectLocation}
                    rel="noopener noreferrer"
                >
                    Detect location
                </button>

                <p id='location_result'></p>
            </header>
        </div>
    );
}

export default App;
