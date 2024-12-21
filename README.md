# AgingCare: Mobile Application for a Local Retirement Home

## Overview
AgingCare is an IoT-based smart health monitoring system designed to improve elderly care in retirement homes. It integrates a custom Android application with an Arduino Nano-based wearable device to track residents' health metrics in real time. The system streamlines healthcare management, enhances data accessibility, and minimizes manual errors.

## Features
### Android Application
- **User Authentication:** Secure login with Firebase Authentication.  
- **Real-Time Monitoring:** Displays live heart rate data from wearable devices.  
- **Role-Based Access Control:** Healthcare staff have personalized access to manage treatments and monitor patients.  
- **Medication Tracking:** Enables staff to schedule and track residents' medication plans.  
- **Appointment Scheduling:** Tracks doctor appointments and reminders for residents.  
- **Data Visualization:** Graphical representation of health metrics for easy analysis.  
- **Notifications:** Alerts staff about abnormal health readings.  

### Wearable Device
- **Hardware:** Arduino Nano, heart rate sensor, Bluetooth HC-05 module, and a battery.  
- **Wireless Communication:** Transmits heart rate data via Bluetooth to the Android app.  
- **Portability:** Compact and lightweight design for elderly residents.  

### Database
- **Cloud-Based Storage:** Uses Firebase Realtime Database for seamless data synchronization.  
- **Secure Access:** Ensures data privacy through Firebase Authentication.  

## System Architecture
1. **Wearable Device:** Captures and transmits heart rate data to the Android app.  
2. **Android App:** Receives data, visualizes trends, and manages alerts.  
3. **Cloud Database:** Stores data securely and synchronizes it across devices.

## Installation
### Hardware Setup
1. Assemble the Arduino Nano, heart rate sensor, and Bluetooth HC-05 module.  
2. Connect the components as shown in the circuit diagram provided.  
3. Upload the Arduino code via the Arduino IDE.
![AgingCarePCB](https://github.com/Zelawon/AgingCare/blob/master/Images/AgingCarePCB_nano.jpg =400x)

### Mobile App Setup
1. Import the Android project into Android Studio.  
2. Configure Firebase Authentication and Realtime Database.  
3. Build and install the app on an Android device.

### Pairing the Device
1. Enable Bluetooth on your Android device.  
2. Pair it with the HC-05 Bluetooth module.  
3. Launch the app and connect to the wearable device.

## Usage
- Log in using role-specific credentials.  
- Pair the wearable device via Bluetooth.  
- Monitor real-time heart rate data on the dashboard.  
- Manage medical records, treatments, and appointments.  
- Track medication schedules and doctor appointments.  
- Receive alerts for abnormal readings.

## Technologies Used
- **Software:** Android Studio, Firebase Authentication, Firebase Realtime Database, Arduino IDE.  
- **Hardware:** Arduino Nano, heart rate sensor, Bluetooth HC-05, battery.  

## License
This project is licensed under the GPL-3.0 License. See the `LICENSE` file for details.
