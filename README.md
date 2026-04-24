# EComm

A simple ecommerce backend built with Spring Boot for learning and practice.

## Overview

This project was created to practice backend development using Spring Boot and Maven. The main focus was to understand how a basic ecommerce backend can be structured using REST APIs, entities, and layered architecture.

It is a small learning project, so the goal was not to build a complete production-ready application, but to improve hands-on understanding of backend concepts.

## Tech Stack

- Java
- Spring Boot
- Maven

## What I Practiced

Through this project, I worked on:

- Setting up a Spring Boot project structure
- Creating REST APIs
- Working with product-related endpoints
- Understanding entity modeling
- Organizing code using backend layers
- Exploring concurrency handling with versioning in the `Product` entity

## Current Features

- Basic Spring Boot backend setup
- Product-related API development
- Get single product endpoint
- Get all products endpoint
- Add product endpoint
- Product versioning for safer concurrent updates

## Project Structure

This repository includes:

- `src/` for the application source code
- `pom.xml` for Maven dependencies and project configuration
- `mvnw` and `mvnw.cmd` for running the project using Maven Wrapper [page:1]

## How to Run

Clone the repository:

```bash
git clone https://github.com/Anant-Saini/EComm.git
cd EComm
```

Run using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

## Note

This is a simple learning project built to strengthen backend fundamentals. More features and improvements can be added over time as the project grows.

## Author

Anant Saini
