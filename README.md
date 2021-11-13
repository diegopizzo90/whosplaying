# Who's playing?

## User Interface
An Android app composed by two screens, in the first one is shown the results of football matches
from the 5 most important european leagues (Serie A, Liga, Premier League, Ligue 1 and Bundesliga).
It's possible to check the results of the latest matches, those ones scheduled in the next few days
and also the live score updated each 60 seconds.

In the second screen, shown after a clicks on a specific football match, it is possible to see a card
view with the list of the main events occurs during the game. For example are listed events like the
list of scorers, assists, yellow/red card, substitutions, VAR reviews and other important events.

The main purpose of this application is to experiment new library/framework like coroutine, flow and
Jetpack compose.

![App main screen](/assets/main_screen) ![App event screen 1](/assets/event_screen_1) ![App event screen 2](/assets/event_screen_2)

**Architecture:**
This project is based on MVVM pattern and written with Kotlin.

## Authentication
In order to retrieve all the necessary information related to football leagues and results, it was used
this API: https://api-football-v1.p.rapidapi.com/v3/

Please visit https://rapidapi.com/api-sports/api/api-football, subscribe and get for free the API-Key
useful to make the requests. After that please add the API-Key in your local.properties:
```bash
API_KEY_VALUE = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
```
## Third-party libraries

**Koin**  
Koin is a framework for the dependency injection. Link: https://github.com/InsertKoinIO/koin

**Retrofit**  
HTTP client used the make API Requests and retrieve the data from network.
Link: https://square.github.io/retrofit/

**Glide**  
Image loader for Android. Link: https://github.com/bumptech/glide

**ThreeTen**  
A small library that permit to use the java.time* package with sdk<26.
Link: https://github.com/JakeWharton/ThreeTenABP

**dropbox/Store**  
Library to simplify storage, and fetching of data and it is built with Coroutines. This app is using
a memory cache to hold the information that can be available when the user is offline.
Link: https://github.com/dropbox/Store
