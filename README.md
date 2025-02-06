# Spring Boot AWS SQS & LocalStack Integration Project

*[Türkçe versiyonu için tıklayınız / Click for Turkish version](#türkçe)*


![Java](https://img.shields.io/badge/Java-21-yellow)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green)
![AWS SQS](https://img.shields.io/badge/AWS%20SQS-FIFO%20Queue-orange)
![LocalStack](https://img.shields.io/badge/LocalStack-4.1.0-blue)

This project is a Spring Boot application demonstrating the use of AWS SQS with LocalStack in a local environment.

## 📌 Features
- LocalStack setup with Docker Compose
- SQS FIFO Queue creation
- Message sending via REST API
- Automatic message listening

## 🛠 Technologies Used
| Component | Version |
|--------------------|----------|
| Java | 21 |
| Spring Boot | 3.4.1 |
| AWS Spring Cloud | 3.3.0 |
| SQS FIFO Queues | - |
| Docker | - |
| LocalStack | 4.1.0 |
| PostgreSQL | 11.5 |

### 1. Prerequisites
- **Docker & Docker Compose**: Install according to your operating system from [Official Docker Documentation](https://docs.docker.com/get-docker/)
- **Java 21**: Ensure you have [OpenJDK 21](https://openjdk.org/projects/jdk/21/) or [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) installed
- **Gradle 8.x**: Project includes Gradle wrapper, no separate installation needed. Manual installation optional

### 2. Cloning the Project
```bash
git clone https://github.com/alisimsekk/AwsSqsApp.git
cd AwsSqsApp/docker/local
```

### 3. Starting LocalStack with Docker
```bash
# Grant execution permission to AWS init script
chmod +x aws-init.sh

# Start Docker containers in background
docker-compose up -d

# Check if containers are running healthy
docker ps | grep localstack

# Create queues
./aws-init.sh
```

### 4. Installing Dependencies and Building the Project
```bash
# Download dependencies and build project
./gradlew build
```

### 5. Running the Application
```bash
# Start Spring Boot application with docker-local profile
./gradlew bootRun --args='--spring.profiles.active=docker-local'
```

## 📂 Project Structure
```
src/
├── main/
│ ├── java/com/simsekali/awssqsdemo/
│ │ ├── aws/
│ │ │ └── Config/
│ │ │ └── listener/
│ │ ├── controller/
│ │ │ └── User.java
│ │ ├── entity/
│ │ │ └── User.java
│ │ ├── exception/
│ │ ├── repository/
│ │ │ └── UserRepository.java
│ │ ├── service/
│ │ │ └── UserService.java
│ └── resources/
│ └── application.yml
│ └── application-docker-local.yml
docker/
├── local/
│ └── docker-compose.yml
│ └── aws-init.sh
```

## 📡 API Endpoints
### Send Message
```bash
- POST http://localhost:8088/api/users/register
Request body:
{
    "email": "test@example.com",
    "username": "testuser",
    "password": "Test1234!",
    "deadLetterQueueTest": true
}

- GET http://localhost:8088/api/users?email=test@example.com
```

### 🔍 Test Scenario
1. User registration (DLQ test):
```bash
curl -X POST http://localhost:8088/api/users/register \
-H "Content-Type: application/json" \
-d '{
    "email": "test@example.com",
    "username": "testuser",
    "password": "Test1234!",
    "deadLetterQueueTest": true
}'
```

2. Activation check:
```bash
curl http://localhost:8088/api/users?email=test@example.com
```

### 💡 Core Features
- FIFO Queue with message deduplication
- Dead-Letter Queue after 3 retries
- Transactional message handling
- Local AWS environment simulation

---

# Türkçe

# Spring Boot AWS SQS & LocalStack Entegrasyon Projesi

![Java](https://img.shields.io/badge/Java-21-yellow)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green)
![AWS SQS](https://img.shields.io/badge/AWS%20SQS-FIFO%20Queue-orange)
![LocalStack](https://img.shields.io/badge/LocalStack-4.1.0-blue)

Bu proje, AWS SQS'in LocalStack ile lokal ortamda kullanımını gösteren bir Spring Boot uygulamasıdır.

## 📌 Özellikler
- Docker Compose ile LocalStack kurulumu
- SQS FIFO Queue oluşturma
- REST API üzerinden mesaj gönderme
- Otomatik mesaj dinleme

## 🛠 Kullanılan Teknolojiler
| Bileşen            | Versiyon |
|--------------------|----------|
| Java               | 21       |
| Spring Boot        | 3.4.1    |
| AWS Spring Cloud   | 3.3.0    |
| SQS FIFO Kuyruklar | -        |
| Docker             | -        |
| LocalStack         | 4.1.0    |
| PostgreSQL         | 11.5     |

### 1. Ön Gereksinimler
- **Docker & Docker Compose**  
  [Resmi Docker Dokümanları](https://docs.docker.com/get-docker/) üzerinden işletim sisteminize uygun kurulumu yapın.

- **Java 21+**  
  [OpenJDK 21](https://openjdk.org/projects/jdk/21/) veya [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) kurulu olduğundan emin olun.

- **Gradle 8.x**  
  Proje Gradle wrapper içerdiği için ayrıca kurulum gerekmez. Ancak manuel kurulum yapılabilir.

### 2. Projeyi Klonlama
```bash
git clone https://github.com/alisimsekk/AwsSqsApp.git
cd AwsSqsApp/docker/local
```

### 3. Docker ile LocalStack'i Başlatma
```bash
# AWS başlatma script'ine çalıştırma izni verin
chmod +x aws-init.sh

# Docker konteynırlarını arka planda başlatın
docker-compose up -d

# Konteynırların sağlıklı çalıştığını kontrol edin
docker ps | grep localstack

# Queue'ların oluşturun
./aws-init.sh
```

### 4. Bağımlılıkları Yükleme ve Projeyi Derleme
```bash
# Bağımlılıkları indirin ve projeyi derleyin
./gradlew build
```

### 5. Uygulamayı Çalıştırma
```bash
# Spring Boot uygulamasını docker-local profili ile başlatın
./gradlew bootRun --args='--spring.profiles.active=docker-local'
```

## 📂 Proje Yapısı
```
src/
├── main/
│   ├── java/com/simsekali/awssqsdemo/
│   │   ├── aws/
│   │   │   └── Config/
│   │   │   └── listener/
│   │   ├── controller/
│   │   │   └── User.java
│   │   ├── entity/
│   │   │   └── User.java
│   │   ├── exception/
│   │   ├── repository/
│   │   │   └── UserRepository.java
│   │   ├── service/
│   │   │   └── UserService.java
│   └── resources/
│       └── application.yml
│       └── application-docker-local.yml

docker/
├── local/
│   └── docker-compose.yml
│   └── aws-init.sh
```

## 📡 API Endpointleri

### Mesaj Gönder
```bash
- POST http://localhost:8088/api/users/register
Request body: 
    {
       "email": "test@example.com",
       "username": "testuser",
       "password": "Test1234!",
       "deadLetterQueueTest": true
     }
     
- GET http://localhost:8088/api/users?email=test@example.com
```

### 🔍 Test Senaryosu
1. Kullanıcı kaydı (DLQ testi):
```bash
   curl -X POST http://localhost:8088/api/users/register \
     -H "Content-Type: application/json" \
     -d '{
       "email": "test@example.com",
       "username": "testuser",
       "password": "Test1234!",
       "deadLetterQueueTest": true
     }'
```
2. Aktivasyon kontrolü:
```bash
   curl http://localhost:8088/api/users?email=test@example.com
```
### 💡 Temel Özellikler
- Mesaj tekilleştirmeli FIFO Kuyruk
- 3 denemeden sonra Dead-Letter Queue
- İşlemsel mesaj yönetimi
- Yerel AWS ortam simülasyonu

