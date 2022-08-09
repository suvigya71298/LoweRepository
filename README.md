# LoweRepository

URL-Shortener application helps you create a tiny URL (shortened url) which helps in giving a lot of advantages in different aspects of business like Data analytics, gather click data and moreover eases out life as they are conveniently sharable and usable in areas where there are character limits.

This is a backend application for generating tinyUrl which uses the tech stack of Java, Spring Boot, Mongodb and Docker.
This application uses its own dockerfile to build its docker image and two other images for mongodb and URL-Shortener-Web application respectively.
All the docker images will be run in conjunction using docker-compose.yaml file.


How to install and run the project.

# Prerequisite:
You should have docker-desktop or docker-agent installed on your system.
Create a docker image of the URL-Shortener-Frontend application  [https://github.com/suvigya71298/LoweRepositoryUrlShortenerFrontend] before you run this project as this project uses the image of the former to make the system up as a whole using docker-compose.
You can create the image locally or can push to any container registry.

# Steps to run:
1. Git clone the project to your local.
2. Change directory to project root.
3. Open any terminal-emulator [cmd/git-bash/powershell]
4. Run maven clean install
5. Run docker-compose build
6. Run docker-compose up -d [To run all the services in detached mode]
7. Run docker-compose down to stop all the services.
8. Launch the application in your browser: http://localhost:9092

# Code Coverage:

![Capture](https://user-images.githubusercontent.com/70306563/183610483-5f550ae6-9f47-493c-9ee2-5dab4f39b97b.PNG)
