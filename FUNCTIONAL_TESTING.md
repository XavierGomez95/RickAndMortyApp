# Testing

This document specifies the testing approach used for the Rick and Morty API Android project, detailing which components are covered by unit tests and the rationale behind the testing decisions.


## Unit Testing Scope

Unit tests focus on the ViewModel, Repository, and Utils, as these contain the main logic of the app.

### 1. ViewModels

**Located in ui/viewmodel/ directory:**

- **CharacterViewModel.kt**
    - Validate data loading logic from the repository.
    - Ensure LiveData or StateFlow updates correctly on success and failure.
    - Mock repository responses to test UI state changes.

- **EpisodeViewModel.kt**
    - Test episode retrieval and state updates.
    - Verify proper error handling and empty results.
    - Mock dependencies to simulate API and database behavior.


### 2. Repositories and related interface

**Located in data/repository/ directory:**

- **CharacterRepository.kt** and **EpisodeRepository.kt**
    - Test retrieve data flow from the network (via Retrofit) and from local cache (Room).
    - Ensure proper handling of successful, failed, and empty API responses.
    - Use Mockito or MockK to mock DAO and API service interactions.

- **RickAndMortyApiService.kt** (interface)
    - Mocked in tests.
    - Used to verify repository calls and responses.


### 3. API Service

**Located in data/retrofit/ directory:**

- **RickAndMortyApiService.kt**
    - This is an interface defining Retrofit endpoints.
    - It will be mocked during repository tests; no direct unit tests are required.
    - Its mocked responses simulate API JSON payloads like the examples provided (for Character and Episode endpoints).


### 4. Utils

**Located in ui/utils/ directory:**

- **CharacterUtils.kt**
    - Validate transformation logic (strings conversions and model mapping)

- **EpisodeUtils.kt**
    - Validate transformation logic (model mapping)

**Located in `data/utils` directory:**

- **Resource.kt**
    - Tests confirm that each `Resource` state (`Init`, `Loading`, `Success`, `Failure`) behaves as expected.