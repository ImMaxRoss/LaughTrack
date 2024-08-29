# LaughTrack
LaughTrack is a Java-based application designed to help users track and rate sketch comedy groups and their shows. The application provides an intuitive console-based menu for user registration/login, viewing comedy group information, rating sketches, and tracking watched content.

## Console Menu Layout

### Main Menu

1. **Login/Register**
    - Prompts the user to log in or register.
    - **If login is successful**, the user is taken to the User Tracking Menu.
    - **If login is unsuccessful**, a "Try Again..." message is displayed.
    - **If registering**, the user is taken to the registration form.
    - Allows the user to exit to the main menu without login/register.

2. **LaughTrack Summary and Info**
    - Displays a summary and information about LaughTrack.

### Sketch Comedy Groups Menu

1. **List Comedy Groups**
    - Displays a list of sketch comedy group names with their current overall (weighted) rating.
    - Allows the user to select a group to view details.

### Individual Sketch Group Menu

1. **Group Information**
    - Displays the name and summary description of the comedy group.

2. **Available Recorded Sketches**
    - Lists available recorded sketches to rate.
    - Displays the current average rating based on users who have marked them as "watched."

3. **Track Show**
    - Adds the show to the user's "Tracked Comedy Groups" if logged in and redirects to the comedy group tracking menu.
    - If the group is already being tracked, it automatically goes to the user's group tracking menu.
    - Prompts login/register if not logged in.

### User Tracking Main Menu

1. **Tracked Comedy Groups**
    - Lists current tracked sketch groups under categories "Want to Watch," "In Progress," and "Completed Watching."
    - **In Progress**: Shows the percentage of sketches marked as watched.
    - **Completed Watching**: Indicates completion.
    - **Want to Watch**: Indicates not started.
    - Display the groups' current rating based on the average of the sketches watched ratings.
    - Allows selection of a group to view tracking details.

### User Group Tracking Menu

1. **Group Information**
    - Displays the name and summary description of the comedy group.

2. **Available Recorded Sketches**
    - Lists available recorded sketches.
    - Each sketch allows the user to mark it as 'Watched' and rate it from 1-5.


## Repo Strucutre

```
LaughTrack/
│
├── src/
│   └── main/
│       ├── java/
│       |   └──com/
│       |       └── trackerapp/
│       |           ├── dao/
│       |           │   ├── UserDao.java
│       |           │   ├── ComedyGroupDao.java
│       |           │   ├── SketchDao.java
│       |           │   ├── UserRatingsDao.java
│       |           │   └── UserTrackedGroupDao.java
│       |           │
│       |           ├── models/
│       |           │   ├── User.java
│       |           │   ├── ComedyGroup.java
│       |           │   ├── Sketch.java
│       |           │   ├── UserRatings.java
│       |           │   └── UserTrackedGroup.java
│       |           │
│       |           ├── services/
│       |           │   ├── UserService.java
│       |           │   ├── ComedyGroupService.java
│       |           │   ├── SketchService.java
│       |           │   ├── UserRatingsService.java
│       |           │   └── UserTrackedGroupService.java
│       |           │
│       |           ├── utils/
│       |           │   ├── DatabaseConnection.java
│       |           │   └── ScannerInput.java
│       |           │
│       |           └── Main.java
│       │
|       ├── resources/
|       |   └── db.properties
|       |
|       └── webapp/
|
├── target/
├── pom.xml
└── README.md
```

### Database ER diagram

![Er-Diagram](./images/ER-diagram.png)

### App Flowchart
[![](https://mermaid.ink/img/pako:eNq9VcFy2jAQ_RWNeqEzYGwZMPjQDgkhySHTtNAe6nBQbGFrYiSPJJNQzL9XkiGBNAmc6pO1-3b37epJWsOYJwSGcJ7zxzjDQoHp6I4BMIyGRQEmSltmoNX6AozxbL1eYMpucUrM0nyeA3KeUrZbIwcIklKpiNiZfAeQJ6o2G5vCJqtKSQQgTKNkCLwKnEcNm-anfIl769PlbBQwCRhekI_AaAcusJSPXCSfZwZ-XlO4Zkuc0-Q5E-AvwAqcvUcWVWAUNXY9_g--o5qFKfU8W7JP8R-OfgUuoguWzPaaoNt2zSbjWOO2CUYvPdop4FwQnKzMlkkldbN7I4u5ECRW9Y5XYLxeLyl5_FYoypk8MgaDBDFfEJ07Fbws5JFR2ACluT6Q5IQI3whPlYKBnURrwY3fFtxl1DAFzi2hy6PZbQs1e0vF-xiMDsDoY7Bm7jjOkeruYXm3lsb4bYFeRY1pPbhT-2uBbQCoseAEMGWpuSBUeSTzcJmCH1hp-PukfatG473c80qSa7lpd25Ev1UB-FSB2xp7dRI2asiM7-_0iChM81esW3Xn4JrN-WvPcKnx-D4nYPJAVJwRaToxqKllMImotNHD-vBsZ7nFTGqWjFfge2RdwBACqzb7OtsDrIg-bmNjuLWGqb5msCJ1yRvCykNaWpB6qjtKhz6tv1_m-AyXROiTcDB-Qwo24YIIfVASffWvrRWqjOjrCYb6N8Hi4Q7esY3G4VLxyYrFMFSiJE2o20wzGM5xLvWqLBLNYURxKvDilfUioYqLZ2POcUL0cg3VqjBPjrnJdIGYszlNjb0UuTZnShUybLeN20mpysp7R0u_LWli3qdsOei1e6jXx8gnvcDHXd9P4ntv0J-jjjdPAtdDGG42TVhg9pvzxY64XpoiTzBEQccZdD0fdZGHXDfwmnAFQx85gx7y-36Ael4n8Ds6xR8b7zr9QTDoG2QwCNyu22tCYpu7qR9P-4Zu_gLlhBH9?type=png)](https://mermaid.live/edit#pako:eNq9VcFy2jAQ_RWNeqEzYGwZMPjQDgkhySHTtNAe6nBQbGFrYiSPJJNQzL9XkiGBNAmc6pO1-3b37epJWsOYJwSGcJ7zxzjDQoHp6I4BMIyGRQEmSltmoNX6AozxbL1eYMpucUrM0nyeA3KeUrZbIwcIklKpiNiZfAeQJ6o2G5vCJqtKSQQgTKNkCLwKnEcNm-anfIl769PlbBQwCRhekI_AaAcusJSPXCSfZwZ-XlO4Zkuc0-Q5E-AvwAqcvUcWVWAUNXY9_g--o5qFKfU8W7JP8R-OfgUuoguWzPaaoNt2zSbjWOO2CUYvPdop4FwQnKzMlkkldbN7I4u5ECRW9Y5XYLxeLyl5_FYoypk8MgaDBDFfEJ07Fbws5JFR2ACluT6Q5IQI3whPlYKBnURrwY3fFtxl1DAFzi2hy6PZbQs1e0vF-xiMDsDoY7Bm7jjOkeruYXm3lsb4bYFeRY1pPbhT-2uBbQCoseAEMGWpuSBUeSTzcJmCH1hp-PukfatG473c80qSa7lpd25Ev1UB-FSB2xp7dRI2asiM7-_0iChM81esW3Xn4JrN-WvPcKnx-D4nYPJAVJwRaToxqKllMImotNHD-vBsZ7nFTGqWjFfge2RdwBACqzb7OtsDrIg-bmNjuLWGqb5msCJ1yRvCykNaWpB6qjtKhz6tv1_m-AyXROiTcDB-Qwo24YIIfVASffWvrRWqjOjrCYb6N8Hi4Q7esY3G4VLxyYrFMFSiJE2o20wzGM5xLvWqLBLNYURxKvDilfUioYqLZ2POcUL0cg3VqjBPjrnJdIGYszlNjb0UuTZnShUybLeN20mpysp7R0u_LWli3qdsOei1e6jXx8gnvcDHXd9P4ntv0J-jjjdPAtdDGG42TVhg9pvzxY64XpoiTzBEQccZdD0fdZGHXDfwmnAFQx85gx7y-36Ael4n8Ds6xR8b7zr9QTDoG2QwCNyu22tCYpu7qR9P-4Zu_gLlhBH9)

### Mermaid Code
[mermaid flowchart code:](https://mermaid.live/edit)

```plaintext
flowchart TD
  A[App Start] --> 
  B{{mainPage
      1. login
      2. register
      3. exit}}
  B --> |user enters: 1| C[(loginUser
                          1. enter username
                          2. enter password)]
  C --> |Invalid username or password| B
  B --> |user enters: 2| D[(registerUser
                          1. enter username
                          2. enter password)]
  D --> |User registered| B
  B ---> |user enters: 3| E[End]
  B --> |invalid character| B
  D --> |username already exists| D
  C --> |correct login| F{{viewOptions
                          1. view comedy groups
                          2. view tracked groups
                          3. return mainPage}}
  F --> |user enters: 1| G[(viewComedyGroups
                            1. comedygroup1
                            2. comedygroup2
                            3. ...
                            10. comedygroup10)]
  F --> |user enters: 2| H[(TrackedComedyGroups
                          - Tracked Groups 
                          - Tracking Status
                          - Avg Rating)]
  F --> |user enters: 3| B

  G --> |user selects: listed group #| P

  H --> |user selects: listed group #| P[(showComedyGroupDetails
            - Group Info
            - Available Sketches)]

  T --> S[isGroupAlreadyTracked]

  S --> |no| Q[Track show y/n?]
  S --> |yes| F
  P --> T[(rateSketchMenu
            1. Rate Sketch
            2. View Average Rating)]
```