## Tinnitus CMS admin server

Spring Boot application for Tinnitus CMS granting the admin access to upload, view, edit and delete albums, presets and samples. Also the application
provides information about revenue, most rated assets and general overview related to usage of client application. Other features offered are CRUD operations for categories which divide the assets. Keep in mind that this is a personal project which aims to become a production-ready application in the near future.

## Project Status

This project is currently in development. Minimum features are implemented, but functionalities wich provide activity information about the assets usage
and other general information are still under development.

## Installation and Setup Instructions

Clone down this repository. Be sure you already have installed a newer version of JDK.  

To Start the application:

- On Windows:

`gradlew bootRun`

- On Mac or Linux:

`./gradlew bootRun`

To Build for production:

- On Windows:

`gradlew bootJar` or `gradlew build`

- On Mac or Linux:

`./gradlew bootJar` or `./gradlew build`

## Reflection  

This started as a side project, but aims to become a product. There are over 100 million people in the world which suffer from a different form of tinnitus from lightweight symptoms to the level where the patient can lose his/her hearing. Unfortunately in most cases tinnitus cannot be cured, but it can be masked with music. The type of music used in masking tinnitus slightly differs from the regular music we hear everyday. There are special frequencies and white noises which are used in different patterns to help the patient not hearing anymore the sounds that irritates him/her. Although these sounds are enough to do the work usually they are combined with ambiental noises or melodic sequences to offer more comfort.

The project was a very good opportunity for learning more about the back-end aspect of a WEB application and also to improve skills in using different frameworks. This project can help you understand more about how the authentication and authorisation processes work, how to make endpoints public to the client application and how to keep others private and only allowing access for certain levels of authorisation. It also helps you understand the required transactions to pass a JWT token after email authentication to the admin accessing the application and how data is for different assets is received and stored in the database and locally depending on each information specific location.

Firstly the server application was developed in NodeJS and it used services provided by Oracle Cloud Infrastructure. There was also an Electron and Tauri approach, but for the functionalities this application provided those technologies didn't fit well, so the last resort was to go back to the first solution, but this time using Spring Boot which proved to be the best approach for the application purpose. Sping Boot uses Java where in this context was faster and more secure and it also eliminated a lot of boilerplate configuration and code which the developer needs to handle in case they were using only the Spring framework. The front-end is developed in React (which is available in its own repository) and communicates with the server application via a proxy. The build system used is Gradle which uses a more human readable format for configuration and adding dependencies than Maven, and it's also more modern and maintained.
