<img width="905" alt="image" src="https://github.com/user-attachments/assets/e1bc1283-cb16-4731-9ffa-6771694f075f">**Blogging Platform API
**

**Overview**

This project is a RESTful API backend for a blogging platform, built with Java, Spring Boot, Maven, MySQL, and JPA. The API supports CRUD operations for posts, user management, and tagging. It includes authentication and authorization mechanisms, pagination, and filtering.

**Features**

User creation with username and password which will be encoded and saved.
Authentication and authorization for secure access in every request by sending username and password to maintain RESTful properties.
Create, Read, Update, Delete (CRUD) operations for posts.
Filter posts by author or creation date or id or all posts.
Pagination for retrieving posts.
Tag posts with multiple tags.

**Prerequisites**

Java 17
Maven
MySQL
Spring Boot 3.3.3


**Steps to run project locally**
- Clone the repo
- Create a DB server in mySQL with the name 'Blog Application or something similar"
- Update the properties in the application.proeprties as necessary. Update the database connection url (same as the name provided in the previous step), user name and password.
- Run the mySQL server locally on your machine.
- Run the spring boot application locally with the command -  mvn clean package spring-boot:run
- Use the terminal and run the curl commands or use postman and hit the api end points


**Schemas-**

User Table

user_id (Primary Key, Auto-increment)
username (Unique, Not Null)
password (Not Null)
bio (Nullable)


Post Table

post_id (Primary Key, Auto-increment)
author_id (Foreign Key, Not Null, References User(user_id))
title (Not Null, Unique)
content (Not Null)
creation_date (Not Null)


Tag Table

tag_id (Primary Key, Auto-increment)
keyword (Not Null)
Post_Tags Table (Join Table for Many-to-Many Relationship)
post_id (Foreign Key, References Post(post_id))
tag_id (Foreign Key, References Tag(tag_id))
Endpoints



**Post Controller Endpoints**
Create a Post
Endpoint: POST /posts/create
cURL Command:
        curl -X POST http://localhost:8080/posts/create \
          -H "Content-Type: application/x-www-form-urlencoded" \
          -d "authorName=authorUsername&title=Post Title&content=Post Content"

Get a Post by ID
Endpoint: GET /posts/{id}
cURL Command:
        curl -X GET http://localhost:8080/posts/1

Get All Posts with Pagination
Endpoint: GET /posts
cURL Command:
        curl -X GET "http://localhost:8080/posts?page=0&size=10"

Get Posts by Username with Pagination
Endpoint: GET /posts/user/{username}
cURL Command:
        curl -X GET "http://localhost:8080/posts/user/authorUsername?page=0&size=10"

Get Posts by Date Range with Pagination
Endpoint: GET /posts/date
cURL Command:
        curl -X GET "http://localhost:8080/posts/date?startDate=2024-08-25T00:00:00&endDate=2024-08-26T00:00:00&page=0&size=10"

Update a Post
Endpoint: PUT /posts/{id}
cURL Command:
        curl -X PUT http://localhost:8080/posts/1 \
          -H "Content-Type: application/json" \
          -d '{"title": "Updated Post Title", "content": "Updated Post Content"}'

Delete a Post
Endpoint: DELETE /posts/{id}
cURL Command:
        curl -X DELETE http://localhost:8080/posts/1


**User Controller endpoints**

Create a User
Endpoint: POST /users/create
cURL Command:
        curl -X POST http://localhost:8080/users/create \
          -H "Content-Type: application/x-www-form-urlencoded" \
          -d "username=newUser&password=newPassword"

          
Get a User by ID
Endpoint: GET /users/{id}
cURL Command:
        curl -X GET http://localhost:8080/users/1

Update a User
Endpoint: PUT /users/{id}
cURL Command:
        curl -X PUT http://localhost:8080/users/1 \
          -H "Content-Type: application/json" \
          -d '{"username": "updatedUsername", "password": "updatedPassword"}'

Delete a User
Endpoint: DELETE /users/{id}
cURL Command:
        curl -X DELETE http://localhost:8080/users/1



**Tag Controller endpoints**

Create a Tag
Endpoint: POST /tags/create
cURL Command:
        curl -X POST http://localhost:8080/tags/create \
          -H "Content-Type: application/x-www-form-urlencoded" \
          -d "keyword=tagKeyword"

Get a Tag by Name
Endpoint: GET /tags/name/{tagName}
cURL Command:
        curl -X GET http://localhost:8080/tags/name/tagName


Delete a Tag
Endpoint: DELETE /tags/{id}
cURL Command:
        curl -X DELETE http://localhost:8080/tags/1


Note: further optimizations could be added by using either a H2 server for faster access within small projects or by introducing the mapper classes and DTO objects for more smoothness.




