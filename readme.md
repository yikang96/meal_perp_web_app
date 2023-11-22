# Sage Cuisine

Sage Cuisine is a web application for food enthusiasts who love to explore and share their favorite meals.

## Project Overview
![Alt text](/src/main/resources/static/images/image.png)
![Alt text](/src/main/resources/static/images/image-1.png)
![Alt text](/src/main/resources/static/images/image-3.png)
![Alt text](/src/main/resources/static/images/image-4.png)
![Alt text](/src/main/resources/static/images/image-5.png)
![Alt text](/src/main/resources/static/images/image-2.png)
![Alt text](/src/main/resources/static/images/image-6.png)
![Alt text](/src/main/resources/static/images/image-7.png)
![Alt text](/src/main/resources/static/images/image-8.png)
![Alt text](/src/main/resources/static/images/image-9.png)
![Alt text](/src/main/resources/static/images/image-10.png)


## Project Structure

The project is structured as follows:

- `src/main/java/com/example/sagecuisine`: This is the main package for the application. It contains the following sub-packages:
  - `controllers`: This package contains all the controller classes that handle incoming HTTP requests.
  - `models`: This package contains all the model classes that represent the data in the application.
  - `repositories`: This package contains all the repository interfaces that handle database operations.
  - `services`: This package contains all the service classes that contain business logic.
- `src/main/resources`: This directory contains resources such as templates and property files.
- `src/test`: This directory contains all the test classes for the application.

## Function of the Packages

- `controllers`: The controllers handle incoming HTTP requests and return responses. They call methods from the service classes to perform operations.
- `models`: The models represent the data in the application. They are used by the repository interfaces to map the data in the database to Java objects and vice versa.
- `repositories`: The repositories handle all database operations. They use Spring Data JPA to automatically generate queries.
- `services`: The services contain the business logic of the application. They call methods from the repository interfaces to interact with the database and return the results to the controllers.

## Features

- Browse meals: Explore a variety of meals that can delivered right to your door.
- User authentication: Sign up, log in, and manage your account.
- Shopping Cart: add your favorite meals
- Admin panel: Admin users can manage all meals.

## Test the Application
1. Ensure to update the 'username' and 'password' fields in the application.properties file to configure the connection for your specific database.
2. Dataset has already been prepared (in DataLoader.java class), to test the application performance, you can run the application directly.
3. There is one account for role "user" and one for "admin". To see the difference of user control, please use different login info accordingly.

## Installation

1. Git repository: `https://github.com/yikang96/sage_cuisine_meal_prep.git`
2. Navigate into the project directory: `cd sage-cuisine`
3. Install the dependencies: `mvn install`
4. Run the application: `mvn spring-boot:run`

## Usage

Open your web browser and visit `http://localhost:8080`.


## Additional Features in the future
 - User Dashboard
 - User message box
 - community interactions
 - implementing tools that calculates nutrition fact that users need based on their diet goals
 - blog feature for sharing meal prep tips
