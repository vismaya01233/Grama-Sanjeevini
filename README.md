# Grama Sanjeevini – Bridging Healthcare Access in Rural Communities


In many rural areas, finding essential medicines during emergencies is still a major challenge. People often travel long distances from one pharmacy to another without knowing whether a medicine is available. This delay can become critical during medical emergencies.

Grama Sanjeevini was developed with a simple goal:

> "To make medicine availability easier, faster, and more accessible for rural communities."

This Android application acts as a digital bridge between people and nearby pharmacies. Instead of searching physically, users can quickly check medicine availability, locate pharmacies on maps, and access emergency medicine information directly through the app.

The project also supports pharmacists by providing a dedicated dashboard to manage medicine stock, update inventories, and monitor medicine expiry dates.

---

# What the Application Does

Grama Sanjeevini helps users:

* Search for medicines instantly
* Locate nearby pharmacies using Google Maps
* Check emergency medicine stock availability
* Contact pharmacies quickly during emergencies
* Access real-time medicine data using Firebase

For pharmacists, the application provides:

* Secure login and registration
* Inventory management
* Medicine stock updates
* Expiry tracking system
* Dashboard for medicine monitoring

---

# Why This Project Matters

Healthcare accessibility is not equal everywhere. Rural communities often face:

* Limited pharmacy availability
* Lack of real-time medicine information
* Transportation delays
* Difficulty locating nearby medical stores

Grama Sanjeevini attempts to solve these problems through technology.

The project focuses on:

* Faster medicine discovery
* Emergency support
* Rural healthcare assistance
* Digital pharmacy management

---

# Technologies Used

This project was developed using modern Android development technologies.

## Frontend

* Kotlin
* Android XML
* Android SDK

## Backend

* Firebase Authentication
* Firebase Firestore
* Firebase Analytics

## Architecture

* MVVM Architecture
* Repository Pattern
* LiveData
* ViewModel
* Kotlin Coroutines

## Additional Services

* Google Maps API
* Google Location Services

---

# Project Modules

## Medicine Search Module

Allows users to search medicines and check availability from pharmacy records.

## Pharmacy Locator Module

Uses Google Maps integration to display nearby pharmacy locations.

## Emergency Stock Module

Displays medicines available for emergency medical situations.

## Pharmacist Authentication Module

Provides secure pharmacist registration and login functionality.

## Pharmacist Dashboard

Allows pharmacists to manage medicines, update stock, and monitor expiry dates.

---

# Firebase Integration

The application uses Firebase services for authentication and real-time database management.

### Firebase Services Used

* Firebase Authentication
* Cloud Firestore
* Firebase Analytics

### Setup Steps

1. Create a Firebase project
2. Add the Android application
3. Download `google-services.json`
4. Place it inside:

```bash
app/google-services.json
```

5. Enable Email/Password Authentication in Firebase Console

---

# Installation Guide

## Prerequisites

* Android Studio
* Android SDK 34+
* Firebase Account
* Google Maps API Key

---
#clone the repository

## Open the Project

1. Open Android Studio
2. Click on:

```bash
Open Existing Project
```

3. Select the project folder

---

## Add Google Maps API Key

Inside `AndroidManifest.xml`:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY" />
```

---

# Key Learning Outcomes

This project helped in understanding:

* Android application development using Kotlin
* MVVM architecture implementation
* Firebase Authentication integration
* Firestore database operations
* Google Maps integration
* RecyclerView implementation
* Real-time data handling
* Healthcare-focused application development

---

# Future Enhancements

The project can be extended further with:

* AI-based medicine recommendations
* Online medicine booking system
* Barcode scanning support
* Push notifications for medicine alerts
* Multi-language support
* Offline support for rural internet limitations
* Admin monitoring dashboard

---

# Conclusion

Grama Sanjeevini is more than just an Android project.

It represents an effort to use technology for solving real-world healthcare accessibility problems in rural communities. The application combines Android development, Firebase services, and location-based technology to create a meaningful healthcare support system.

This project reflects the idea that:

> "Technology becomes valuable when it helps people during their most important moments."

---

# Author

## Vismaya

Android Developer | Kotlin Enthusiast

---


