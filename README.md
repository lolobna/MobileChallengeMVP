

# Trending GitHub Repositories – Android (Java, MVP)

Android application that displays the most starred GitHub repositories created in the last 30 days using the GitHub REST API.
The app implements infinite scrolling with pagination and follows a clean MVP architecture using a single Activity and Fragments.

---

## Features

* List trending GitHub repositories
* Infinite scroll pagination
* Repository details:

  * Name
  * Description
  * Stars count
  * Owner username
  * Owner avatar
* Scroll-to-top button
* Loading footer indicator
* Clean MVP architecture
* Dependency injection with Dagger2
* ViewBinding
* REST API consumption with Retrofit

---

## Architecture

The project follows **MVP (Model-View-Presenter)** with clear separation of concerns.

```
app
 ├── data
 │    ├── model
 │    │     ├── Repo.java
 │    │     └── Owner.java
 │    ├── remote
 │    │     ├── GitHubApi.java
 │    │     └── RetrofitClient.java
 │    └── repository
 │          ├── RepoRepository.java
 │          └── RepoCallback.java
 │
 ├── ui
 │    └── TrendRepos
 │          ├── RepoFragment.java
 │          ├── RepoPresenter.java
 │          ├── RepoContract.java
 │          └── RepoAdapter.java
 │
 ├── di
 │    ├── AppComponent.java
 │    └── NetworkModule.java
 │    └── RepositryModule.java
 │
 └── MyApp.java
```

---

## MVP Flow

```
User scrolls to bottom
        ↓
RepoFragment detects end of list
        ↓
presenter.loadRepos(page)
        ↓
Repository calls API
        ↓
Presenter receives data
        ↓
view.showRepos(repos)
        ↓
Adapter updates list
        ↓
RecyclerView displays new items
```

---

## Pagination Strategy

GitHub API provides paginated results (30 items per page).

Example:

```
GET https://api.github.com/search/repositories?q=created:>2024-01-01&sort=stars&page=1
```

When the user reaches the bottom:

```java
loadPage();
```

Fragment logic:

```java
isLoading = true;
adapter.addLoadingFooter();
presenter.loadRepos(currentPage);
```

When data arrives:

```java
adapter.removeLoadingFooter();
adapter.addRepos(repos);
currentPage++;
isLoading = false;
```

---

## RecyclerView Adapter

Adapter supports two view types:

```
ITEM = repository row
LOADING = progress footer
```

Logic:

```java
@Override
public int getItemViewType(int position) {
    return (position == repos.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
}
```

---

## Tech Stack

* Java
* Android SDK
* MVP Architecture
* RecyclerView
* ViewBinding
* Retrofit
* Gson
* Dagger2
* Picasso

---

## How to Run

1. Clone repository

```
git clone https://github.com/your-username/trending-repos-mvp.git
```

2. Open in Android Studio

3. Sync Gradle

4. Run on device/emulator

---

## API

GitHub Search Repositories API:

```
https://api.github.com/search/repositories
```

Query used:

```
q=created:>DATE&sort=stars&order=desc&page=N
```








