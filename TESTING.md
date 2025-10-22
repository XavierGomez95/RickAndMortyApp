# Testing

This document specifies the testing approach used for the Rick and Morty API Android task, detailing two testing methods: Unit Testing and Instrumental Testing.




## Unit Testing Scope

Unit tests focus on the ViewModel, Repository, and Utils, as these contain the main logic of the app.

---

### 1. ViewModels

Unit tests in this section focus on verifying the **business logic and state management** within the app’s **ViewModels**.  
These components act as the bridge between the repository layer and the UI, handling data retrieval, transformation, and exposure through **StateFlow** or **LiveData**.  
To ensure isolated and deterministic testing, each ViewModel uses a **Fake Repository** that mimics real data interactions, allowing full control of success and failure scenarios without network dependencies.

Both **CharacterViewModelTest** and **EpisodeViewModelTest** use the **Kotlin Coroutines Test** library to manage asynchronous operations in a controlled environment, ensuring that state emissions (`Loading`, `Success`, `Failure`) occur as expected.

---

### CharacterViewModelTest

**Located in:** `com.rickandmortyapi.ui.viewmodel.CharacterViewModelTest`

Tests the logic inside **CharacterViewModel**, which interacts with the **CharacterRepositoryInterface** to manage character data.  
A **FakeCharacterRepository** is used to simulate API responses and error conditions.

#### Covered Scenarios

- **Test 1: fetchCharacters should emit Success with FakeRepository**  
  Simulates a successful repository call.  
  Verifies that `fetchCharacters()` emits a `Resource.Success` state containing two characters.

- **Test 2: fetchCharacters should emit Failure on error**  
  Configures the fake repository to fail (`shouldFail = true`).  
  Validates that `fetchCharacters()` emits a `Resource.Failure` state, ensuring proper error propagation.

- **Test 3: getCharacterById should emit Loading then Success**  
  Calls `getCharacterById(1)` and verifies that `singleCharacterStateFlow` emits a `Resource.Success` state with the expected character details (`id`, `name`, `status`, `species`, `gender`, `image`).

- **Test 4: getCharacterById should emit Failure when character not found**  
  Requests a non-existing ID (`999`) and asserts that the emitted state is `Resource.Failure`.

---

### EpisodeViewModelTest

**Located in:** `com.rickandmortyapi.ui.viewmodel.EpisodeViewModelTest`

Tests the logic inside **EpisodeViewModel**, which uses the **EpisodeRepositoryInterface** to retrieve and expose episode data to the UI layer.  
A **FakeEpisodeRepository** is implemented to simulate both successful and failing API responses.

#### Covered Scenarios

- **Test 1: fetchEpisodes should emit Loading then Success**  
  Invokes `fetchEpisodes()` and ensures that the flow emits a `Resource.Success` state with a list of episodes.  
  Verifies that both episodes are retrieved correctly and in the expected order (`"Pilot"`, `"Lawnmower Dog"`).

- **Test 2: fetchEpisodes should emit Failure when repository fails**  
  Simulates a repository failure by setting `shouldFail = true`.  
  Checks that the resulting emission is a `Resource.Failure`, confirming proper error handling.

- **Test 3: getEpisodeById should emit formatted UI data when Success**  
  Calls `getEpisodeById(1)` and ensures that the emitted `Resource.Success` contains formatted UI data (`uiDate`, `uiSeason`, `uiEpisode`).

- **Test 4: getEpisodeById should emit Failure when episode not found**  
  Requests a non-existing ID (`999`) and verifies that the resulting emission is a `Resource.Failure`.

---

### 2. Repositories

Unit tests validate the logic of the **CharacterRepository** and **EpisodeRepository**, ensuring that both classes correctly manage data retrieval from the **API (Retrofit)** and **local database (Room)**.  

All dependencies (`Dao`, `ApiService`, `SharedPreferences`, and `Context`) are mocked using **MockK**.

---

### CharacterRepositoryTest

**Located in:** `com.rickandmortyapi.data.repository.CharacterRepositoryTest`

Tests the logic responsible for retrieving, caching, and synchronizing character data between the API and local storage.

#### Covered Scenarios

- **Test 1: retrieveAllCharacters should return Success when API returns data**  
  Simulates a valid API response from `getCharacterBatch(1)`.  
  Verifies that the repository returns `Resource.Success` with two characters.  
  Ensures that local cache operations (`clearAllCharacters()` and `insertCharacters()`) are executed correctly.

- **Test 2: retrieveAllCharacters should return local data when cache is valid**  
  Simulates a scenario where cached data is still valid based on `SharedPreferences` timestamp.  
  Validates that the repository loads data from the local database without performing a network request.  
  Confirms that `apiService.getCharacterBatch()` is **not** called.

- **Test 3: retrieveAllCharacters should return Failure on API exception**  
  Forces an exception in `getCharacterBatch()` to simulate a network error.  
  Checks that the repository returns a `Resource.Failure` state, ensuring proper error propagation.

- **Test 4: getCharacterById should return Success from local**  
  Verifies that the repository retrieves character data directly from the local database when available.  
  Confirms that no API call is made (`apiService.getCharacterById()` not invoked).

- **Test 5: getCharacterById should fetch from API if not local**  
  Simulates a missing record in the local database.  
  Verifies that `getCharacterById()` fetches data from the API, inserts it locally with `insertCharacter()`, and returns a `Resource.Success`.

- **Test 6: getCharacterById should return Failure on exception**  
  Simulates an exception in `getCharacterById()` from the DAO and confirms that the repository emits a `Resource.Failure`.

---

### EpisodeRepositoryTest

**Located in:** `com.rickandmortyapi.data.repository.EpisodeRepositoryTest`

Tests the logic responsible for retrieving and caching episode data, ensuring consistent synchronization between API, database, and cache timestamps.

#### Covered Scenarios

- **Test 1: retrieveAllEpisodes should return Success when API returns data**  
  Simulates a successful API call to `getEpisodeBatch(1)`.  
  Verifies that the repository returns `Resource.Success` with two characters.  
  Ensures that local cache operations (`clearAllEpisodes()` and `insertEpisodes()`) are executed correctly.

- **Test 2: retrieveAllEpisodes should return local data when cache is valid**  
  Simulates a scenario where cached data is still valid based on `SharedPreferences` timestamp.  
  Validates that the repository loads data from the local database without performing a network request.  
  Confirms that `apiService.getEpisodeBatch()` is **not** called.

- **Test 3: retrieveAllEpisodes should return Failure on API exception**  
  Forces an exception in `getEpisodeBatch()` to simulate a network error.  
  Checks that the repository returns a `Resource.Failure` state, ensuring proper error propagation.

- **Test 4: getEpisodeById should return Success from local**  
  Verifies that the repository retrieves character data directly from the local database when available.  
  Confirms that no API call is made (`apiService.getEpisodeById()` not invoked).

- **Test 5: getEpisodeById should fetch from API if not local**  
  Simulates a missing record in the local database.  
  Verifies that `getEpisodeById()` fetches data from the API, inserts it locally with `insertEpisode()`, and returns a `Resource.Success`.

- **Test 6: getEpisodeById should return Failure on exception**  
  Simulates an exception in `getEpisodeById()` from the DAO and confirms that the repository emits a `Resource.Failure`.

---

### 3. Utils

Unit tests in this section validate the **data transformation logic** and **utility functions** that ensure consistent conversion between app layers (database, domain, and UI).  

All tests are executed using **JUnit4** and rely on simple model comparisons to validate mapping consistency.

---

#### CharacterUtilsTest

**Located in:** `com.rickandmortyapi.utils.CharacterUtilsTest`

Tests the transformation functions responsible for converting between **CharacterEntity** and **CharacterModel**.  
Ensures that all fields are mapped correctly and that bidirectional conversions maintain full data integrity.

##### Covered Scenarios

- **Test 1: characterEntityToModel should correctly map fields**  
  Verifies that all fields (`id`, `name`, `status`, `species`, `image`, `gender`) are mapped from `CharacterEntity` to `CharacterModel`.

- **Test 2: characterModelToEntity should correctly map fields**  
  Checks that all all fields (`id`, `name`, `status`, `species`, `image`, `gender`) are mapped from `CharacterModel` to `CharacterEntity`.

- **Test 3: entity to model and back should preserve data integrity**  
  Confirms that bidirectional conversion between entity and model does not alter any field values.

- **Test 4: model to entity and back should preserve data integrity**  
  Confirms that bidirectional conversion between model and entity does not alter any field values.

---

#### EpisodeUtilsTest

**Located in:** `com.rickandmortyapi.utils.EpisodeUtilsTest`

Tests the transformation functions responsible for converting between **EpisodeEntity** and **EpisodeModel**, and checks UI-specific formatting utilities.  
Ensures that field mapping, date parsing, and episode code extraction behave correctly under both valid and malformed inputs.

##### Covered Scenarios

- **Test 1: episodeEntityToModel should correctly map fields**  
  Verifies proper conversion from entity to model, ensuring unmapped or unused fields (like `url`, `created`) are handled consistently.

- **Test 2: episodeModelToEntity should correctly map fields**  
  Confirms that model-to-entity mapping preserves all values exactly as provided.

- **Test 3: entity to model and back should preserve data integrity**  
  Confirms that bidirectional conversion between entity and model does not alter any field values.

- **Test 4: convertAirDateForUi should return date before T**  
  Validates correct extraction of the date portion before the “T” character.

- **Test 5: convertAirDateForUi should return Unknown if string is invalid or empty**  
  Check behavior when the input string is invalid or empty, ensuring “Unknown” is returned safely.

- **Test 6: convertSeasonAndEpisodeForUi should extract season and episode correctly**  
  Confirms that valid episode codes like “S03E07” are parsed into season “03” and episode “07”.

- **Test 7: convertSeasonAndEpisodeForUi should handle malformed or empty input gracefully**  
  Ensures the function returns default placeholders (“?”) when parsing fails or input is missing.

---

#### ResourceTest

**Located in:** `com.rickandmortyapi.utils.ResourceTest`

Tests the **Resource** sealed class used for encapsulating UI state in ViewModels.  
Confirms that data is correctly stored within `Success`, and that singleton instances for `Init`, `Loading`, and `Failure` behave as expected.

##### Covered Scenarios

- **Test 1: Success should contain correct data with String**  
  Ensures that `Resource.Success` correctly stores and retrieves string data.

- **Test 2: Success should contain correct data with list of CharacterModel**  
  Verifies that lists of character models are stored intact and maintain internal consistency (e.g., all species “Human”).

- **Test 3: Success should contain correct data with list of EpisodeModel**  
  Confirms that episodes are stored accurately, preserving all key fields (`name`, `airDate`, `episode`).

- **Test 4: Failure should be singleton**  
  Validates that multiple references to `Resource.Failure` point to the same singleton instance.

- **Test 5: Loading should be singleton**  
  Validates that multiple references to `Resource.Loading` point to the same singleton instance.

- **Test 6: Init should be singleton**  
  Validates that multiple references to `Resource.Init` point to the same singleton instance.

---

### 4. UI Colors

Unit tests in this section verify the **visual consistency and color mapping logic** defined in the app’s **UI color system**.  
These tests ensure that each status color (Alive, Dead, Unknown) matches the expected hex value and that the utility method `getColor()` returns the correct color for various input cases, including null and invalid strings.

---

#### CharacterStatusTest

**Located in:** `com.rickandmortyapi.ui.colors.CharacterStatusTest`

Tests the **CharacterStatus** enum, which maps character states (“Alive”, “Dead”, “Unknown”) to specific color values used in the UI.

##### Covered Scenarios

- **Test 1: ALIVE color should match expected hex value**  
  Confirms that the assigned color for `ALIVE` corresponds to `#269246`.

- **Test 2: DEAD color should match expected hex value**  
  Ensures that the `DEAD` status maps to the correct red tone `#C14C64`.

- **Test 3: UNKNOWN color should match expected hex value**  
  Verifies that the `UNKNOWN` color matches the expected gray `#7A7A7A`.

- **Test 4: getColor should return correct color for Alive status**  
  Checks that calling `getColor("Alive")` returns the same value as `CharacterStatus.ALIVE.color`.

- **Test 5: getColor should return correct color for Dead status**  
  Checks that calling `getColor("Dead")` returns the same value as `CharacterStatus.DEAD.color`.

- **Test 6: getColor should return UNKNOWN color for null input**  
  Checks that calling `getColor(null)` returns the same value as `CharacterStatus.UNKNOWN.color`.

- **Test 7: getColor should return UNKNOWN color for empty or invalid input**  
  Checks that calling `getColor(""")` returns the same value as `CharacterStatus.UNKNOWN.color`.

---




## Instrumental Testing Scope

Instrumented tests verify the correct behavior of **Room** database operations in an Android environment.  
They are executed on an Android device or emulator using **JUnit4**, **AndroidX Test**, and **Room’s in-memory database** for isolated testing.

These tests ensure that DAOs correctly perform **CRUD operations**, **data replacement**, and proper **conflict handling**.

---

### 1. Database Configuration

All DAO tests use an **in-memory Room database** created via:

```kotlin
appDatabase = Room.inMemoryDatabaseBuilder(
  ApplicationProvider.getApplicationContext(),
  AppDatabase::class.java
)
  .allowMainThreadQueries()
  .build()
```

This configuration allows testing database logic without persisting data to disk.
Each test runs in isolation with a fresh database instance, ensuring no data leakage between test cases.

---

### 2. CharacterDaoTest

**Located in:** `com.rickandmortyapi.data.database.dao.CharacterDaoTest`

Tests the DAO responsible for managing **CharacterEntity** records.

#### Covered Scenarios

- **Test 1: Insert Single Character and Retrieve All**  
  Inserts a character and validates that `getCharacters()` returns the correct record.  
  Verifies all fields (`name`, `status`, `species`, `gender`, `image`).

- **Test 2: Insert and Retrieve Single Character**  
  Inserts a single character and validates that `getCharacterById(id)` returns the stored record.
  Verifies all fields (`name`, `status`, `species`, `gender`, `image`).

- **Test 3: Insert Multiple Characters and Retrieve All**  
  Inserts a list of characters and verifies that all are retrieved correctly and in order.
  Verifies all fields (`name`, `status`, `species`, `gender`, `image`).

- **Test 4: Replace on Conflict**  
  Validates the behavior of `OnConflictStrategy.REPLACE`, ensuring the existing record is overwritten when inserting a character with the same `id`.
  Verifies all fields (`name`, `status`, `species`, `gender`, `image`).

- **Test 5: Clear All Characters**  
  Inserts a list of characters, calls `clearAllCharacters()`, and confirms the table is empty.

- **Test 6: Retrieve Nonexistent Character**  
  Ensures that `getCharacterById(id)` returns `null` when no record exists.

---

### EpisodeDaoTest

**Located in:** `com.rickandmortyapi.data.database.dao.EpisodeDaoTest`

Tests the DAO responsible for managing **EpisodeEntity** records.

#### Covered Scenarios

- **Test 1: Insert Single Episode and Retrieve All**  
  Inserts an episode and validates that `getEpisodes()` returns the expected record.
  Verifies all fields (`name`, `airDate`, `episode`, `url`, `created`).

- **Test 2: Insert and Retrieve Single Episode**  
  Inserts a single episode and confirms `getEpisodeById(id)` returns the stored record.
  Verifies all fields (`name`, `airDate`, `episode`, `url`, `created`).

- **Test 3: Insert Multiple Episodes and Retrieve All**  
  Inserts a list of episodes and verifies that all are retrieved correctly and in order.
  Verifies all fields for each record (`name`, `airDate`, `episode`, `url`, `created`).

- **Test 4: Replace on Conflict**  
  Validates the behavior of `OnConflictStrategy.REPLACE` ensuring the existing record is overwritten when inserting an episode with the same `id`.

- **Test 5: Clear All Episodes**  
  Inserts a list of episodes, executes `clearAllEpisodes()`, and confirms the table is empty.

- **Test 6: Retrieve Nonexistent Episode**  
  Checks that `getEpisodeById(id)` returns `null` when the episode doesn’t exist.

---

### RickAndMortyApiServiceTest

**Located in:** `com.rickandmortyapi.data.retrofit.RickAndMortyApiServiceTest`

Tests the Retrofit scenario to ensure that API endpoints defined in `RickAndMortyApiService`return the expected data models.
These tests use **MockWebServer** to simulate HTTP responses.

#### Covered Scenarios

- **Test 1: GET a single character by id**  
  Simulates a successful call to `getCharacterById(id)` and verifies that the returned `CharacterModel` contains the expected values (`id`, `name`, `status`, `species`, `gender`, `image`).  
  Also checks that the request path matches `/character/1`.

- **Test 2: GET characters batch**  
  Mocks a paginated response from the `/character?page=1` endpoint and verifies that `getCharacterBatch(page)` returns a list containing the expected number of characters and their corresponding field values.

- **Test 3: GET a single episode by id**  
  Simulates a successful API call to `getEpisodeById(id)` and ensures that the parsed `EpisodeModel` includes accurate values for `id`, `name`, `air_date`, and `episode`.  
  Validates that the correct endpoint `/episode/1` was requested.

- **Test 4: GET episodes batch**  
  Mocks the `/episode?page=1` endpoint and validates that `getEpisodeBatch(page)` correctly parses multiple `EpisodeModel` entries, verifying `name` and `episode` values for each record in the returned list.