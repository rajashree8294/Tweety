# Tweety: A Twitter Clone

## Introduction

Tweety, being a Twitter clone, is supposed to act like a social media application that allows multiple users to communicate and interact with each other. (This application, however, is being made purely to demonstrate the features of Angular, Spring MVC, and their ability to integrate. This application is in no way a competitor or replacement for Twitter and doesn't include all the features of such a social media application.) The Tweety application must be publicly accessible via the internet, and registered users must be able to send out tweets. The main feed must be visible without any authentication and the user feed must only be visible after authentication.

## Architecture

The application architecture for Tweety will have a RESTful API backend and a model-viewviewmodel (MVVM) frontend. MVVM is a design pattern used to enable two-way data
binding between the view and the model so that one changes when the other does. The Tweety application will have the user interface (UI) implemented using Angular Material, which is a module provided out of the box to simplify UI development using custom tags and themes. Angular is a very famous framework that conforms to the MVVM design pattern, which allows highly responsive frontend applications that efficiently update when data from the server changes or a user interacts with the application. Apart from this, Angular provides routing, dependency injection, components, templates, and so on to enable flexible, modular development. 

## Application Entities

1. Tweet: This is the main domain model, which will store the actual tweet content and posted user

2. User: This is the domain model that will store the username, password, role, and bio of each user on the system

3. Reply: This is the domain model that will store reply/ comment in the tweet, tweet id and user id of the commenting user

4. Follow: This is the domain model that w ill store follower and following user id

5. Connection: This is the domain model that will store connection of two users by their id, status of their connection and date of the connection.

## Application Programming Interface (APIs)

1. List Tweet: This use case is where a user can list all the tweets available for that user. It requires the user to be authenticated

2. Reply Tweet: This use case is where a user can reply to a Tweet. It requires the user to be authenticated

3. Send Tweet: This use case is where a user can send a Tweet. It requires the user to be authenticated

4. Follow User: This use case is where a user can follow another user. It requires the user to be authenticated

5. View Profile: This use case is where a user's profile can be viewed by another. It requires the user to be authenticated

6. Logout: This use case is where a logged in user can log out

7. Connection: This use case is where a user can send connection request to another user

# Author

Rajashree Joshi
