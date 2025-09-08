# 📚 Building Scalable Systems with CQRS and Kafka

A microservices-based **Course Management System** built with **Spring Boot**, **Apache Kafka**, and **CQRS (Command Query Responsibility Segregation)**.  
This project demonstrates how to separate **command (write)** and **query (read)** responsibilities across two microservices, with **Kafka** handling reliable event delivery between them.

---

## 🚀 Features

- **CQRS Pattern** – clean separation of write and read responsibilities.  
- **Command Service** – handles create, update, and delete operations for courses.  
- **Query Service** – optimized for fast reads, keeps its data in sync via Kafka events.  
- **Kafka Integration** – durable, ordered, fault-tolerant event streaming.  
- **Microservices Architecture** – independently deployable command and query services.  
- **Eventual Consistency** – query DBs may lag briefly, but always catch up via Kafka.  

---

## 🏗️ Architecture

- **Command Service**  
  - Receives client requests (create/update/delete).  
  - Stores data in its own database.  
  - Publishes **events** to Kafka.  

- **Kafka**  
  - Stores and delivers events reliably.  
  - Ensures ordering, durability, and replayability.  

- **Query Service**  
  - Listens to events from Kafka.  
  - Updates its **read-optimized database**.  
  - Serves fast queries via REST API.  

---


## ⚙️ Tech Stack

- **Java 17**  
- **Spring Boot 3**  
- **Apache Kafka** (event streaming backbone)  
- **Spring Kafka** (producer & consumer integration)  
- **PostgreSQL** (for persistence – depending on setup)  
- **Docker** (optional for Kafka setup)  

---

## ▶️ Running the Application

### Prerequisites
- Java 17+
- Docker (for running Kafka/Zookeeper)
- Maven

### 1. Start Kafka
You can run Kafka and Zookeeper using Docker:

```bash
docker-compose up -d
