# MovieDB Demo App

MovieDB is a demo app which demonstrates modern Android development. It is built using Kotlin, Hilt, Coroutines, Flow, Compose UI, ViewModel, Paging 3, Room and Retrofit. In terms of architecture, this is a single activity application using the MVVM design pattern with a unidirectional data flow (UDF) to manage state and events. The packages are structured in a way that would make it easy to extract their contents into modules if the app had to be more complicated.

Functionality includes fetching a paginated grid of movies from [The Movie Database](https://www.themoviedb.org/) and caching them for offline support. Clicking individual movies loads additional information about them which is then also cached. Pull-to-Refresh can be used to fetch an updated list of movies, if available. 

## Set-up

Simply add your [TMDB](https://developer.themoviedb.org/) API Key to `secrets.properties` and compile!
