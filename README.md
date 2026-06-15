# EcoQuizGame

Android game app in Kotlin (MVVM + Room + Firebase + Navigation + Material Design).

## Firebase setup

1. Create a Firebase project.
2. Add an Android app with package name `com.example.ecoquizgame`.
3. Download `google-services.json` and place it in `app/google-services.json`.
4. Enable **Authentication** (Email/Password).
5. Enable **Cloud Firestore** (or switch repository to Realtime Database if preferred).
6. Create a `scores` collection for online leaderboard sync.

## Notes

- Multiplayer screen currently includes placeholder sync points in repositories/fragments.
- Language is changed dynamically via `AppCompatDelegate` and persisted via DataStore.
