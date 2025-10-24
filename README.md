# Rick and Morty Android App

## 📱 Overview

This Android application displays a list of characters from the **Rick and Morty** universe, fetched from the public [Rick and Morty API](https://rickandmortyapi.com/api/).  
The project is developed in **Kotlin** using **Jetpack Compose**, **Room**, **Hilt**, and the **MVVM** pattern, following a **Layered Architecture** to ensure clean, maintainable, and scalable code.

---

## 🚀 Features

### 🔹 Character List
- Displays a list of characters retrieved from the API.  
- Each item shows the **image**, **name**, and **status** (alive, dead, unknown) of the character.  

### 🔹 Character Detail
- When a character is selected, a detail screen shows additional information.  
- Data is displayed reactively through `StateFlow` defined in `ViewModel`.

### 🔹 Local Storage
- Uses **Room Database** to cache the characters fetched from the API.  
- The app first loads data from Room and fetches from the API if local data is unavailable.  
- Allows access to information even when offline.

### 🔹 Dependency Injection
- **Hilt** is used to manage dependency injection efficiently.  
- Enables easy injection into `ViewModels`, `Repositories`, and `UseCases`, reducing coupling between components.

---

## 🧱 Architecture

The project follows a basic **Layered Architecture** combined with the **MVVM (Model-View-ViewModel)** pattern.

---

## 🧰 Technologies Used

| Technology                       | Description |
|----------------------------------|-----------------------------------------|
| **Kotlin**                       | Main programming language               |
| **Jetpack Compose**              | Modern declarative UI toolkit           |
| **Hilt**                         | Dependency injection framework          |
| **Room**                         | Local data persistence (cache)          |
| **Retrofit**                     | API communication                       |
| **Coroutines + Flow**            | Asynchronous and reactive data handling |
| **Layered Architecture**         | Core architecture                       |
| **MVVM**                         | Core design pattern                     |

---

## 🧪 Validation and Testing

To ensure quality and robustness, the following validation steps were performed:

- **API Testing:** Verified correct data retrieval and error handling.  
- **Local Cache:** Confirmed proper saving and loading of cached data from Room.  
- **State Management:** Tested UI behavior for loading, success, and error states.  
- **Manual Testing:** Full navigation tests, orientation changes, and data reload scenarios.
