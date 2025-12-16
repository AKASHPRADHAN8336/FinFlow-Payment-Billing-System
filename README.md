# 💳 FinFlow – Distributed Payment & Billing System (Microservices)

FinFlow is a **production-style fintech backend system** built using **Spring Boot microservices**.  
It simulates how **real digital payment platforms** (Paytm, PhonePe, Razorpay-backed apps) work internally.

The system demonstrates **user management, wallet handling, billing, payments, and external gateway integration** with **asynchronous confirmation using webhooks**.

This project is designed to showcase **backend architecture, fintech payment flows, and microservice communication**.

---

## 🧠 Problem Statement

In real-world fintech systems:

- Users receive bills (electricity, subscriptions, services)
- Wallet balances must be managed securely
- Payments should **never be marked successful immediately**
- External payment gateways confirm payments asynchronously
- Systems must handle **PENDING, SUCCESS, and FAILED** states reliably

**FinFlow** solves this by implementing a **realistic payment lifecycle** using **loosely coupled microservices**.

---

## 🏗️ System Architecture

### 🔹 Microservices Overview

| Service | Port | Description |
|-------|------|-------------|
| API Gateway | 8080 | Single entry point for all client requests |
| User Service | 8081 | User creation & management |
| Wallet Service | 8082 | Wallet balance & transactions |
| Billing Service | 8083 | Bill generation & status |
| Payment Service | 8084 | Payment orchestration & state |
| Razorpay Payment Service | 8087 | Razorpay integration |
| Config Server | — | Centralized configuration |
| Service Registry (Eureka) | — | Service discovery |

---

## 🌐 Infrastructure Components

### 🔐 API Gateway
- Routes all external requests to internal services
- Acts as a **single access point**
- Hides internal service ports

### 📡 Service Registry (Eureka)
- Enables **dynamic service discovery**
- Services register themselves automatically
- Removes hardcoded service URLs

### ⚙️ Config Server
- Centralized configuration management
- All services fetch configuration from one place
- Simplifies environment changes

---

## 🔁 High-Level Flow

```text
Client (Postman / Frontend)
        ↓
API Gateway
        ↓
User / Wallet / Billing / Payment Services
        ↓
Razorpay Payment Service
        ↓
Razorpay Gateway
        ↓
Webhook Callback
        ↓
Payment Confirmation
👤 User Service (8081)
Handles user-related operations.

📌 APIs
Method	Endpoint	Description
POST	/users/create	Create new user
GET	/users/{id}	Fetch user by ID
GET	/users/all	Get all users

💰 Wallet Service (8082)
Manages wallet balance and deductions.

📌 APIs
Method	Endpoint	Description
POST	/wallet/create/{userId}	Create wallet
GET	/wallet/balance/{userId}	Get wallet balance
POST	/wallet/add	Add money to wallet
POST	/wallet/deduct	Deduct wallet balance

📌 Wallet deduction happens only after payment confirmation

🧾 Billing Service (8083)
Responsible for generating and managing bills.

📌 APIs
Method	Endpoint	Description
POST	/billing/create	Create bill
GET	/billing/{billId}	Get bill details
PUT	/billing/updateStatus	Mark bill as PAID

💳 Payment Service (8084)
Core orchestration service that coordinates payments.

Responsibilities
Validates bill & wallet

Initiates Razorpay payment

Handles payment confirmation

Updates wallet & billing services

📌 APIs
Method	Endpoint	Description
POST	/Payment/pay	Wallet-based payment
POST	/Payment/rozarpay	Initiate Razorpay payment
POST	/Payment/confirm	Confirm payment (internal)

💸 Razorpay Payment Service (8087)
Handles direct interaction with Razorpay Gateway.

Responsibilities
Create Razorpay orders

Verify webhook signatures

Track gateway transactions

Notify Payment Service

📌 APIs
Method	Endpoint	Description
POST	/razorpay/create-order	Create Razorpay order
POST	/razorpay/webhook	Handle Razorpay webhook

🔄 Razorpay Payment Lifecycle
1️⃣ Payment Initiation
bash
Copy code
POST /Payment/rozarpay
Creates payment record with PENDING status

Requests Razorpay order creation

2️⃣ Razorpay Order Creation
sql
Copy code
POST /razorpay/create-order
Uses Razorpay Java SDK

Amount converted to paise

Order created in test mode

3️⃣ Webhook Notification
bash
Copy code
POST /razorpay/webhook
Razorpay sends payment result

Signature verification performed

Internal transaction updated

4️⃣ Payment Confirmation
bash
Copy code
POST /Payment/confirm
Wallet amount deducted

Bill marked as PAID

Payment marked SUCCESS / FAILED

📌 This ensures no fake or premature payment success

🧾 Payment States
Status	Meaning
PENDING	Order created
SUCCESS	Payment confirmed
FAILED	Payment failed

🗄️ Database Design
User Service
users

Wallet Service
wallet

wallet_transactions

Billing Service
billing

Payment Service
payment

payment_razorpay

Razorpay Service
payment_transaction

📌 Each service has its own database

🔐 Security Measures
Razorpay webhook signature verification

Payment confirmation only via webhook

No direct success manipulation

🔧 Tech Stack
Backend
Java 17

Spring Boot

Spring Data JPA

Spring Cloud (Eureka, Config Server)

WebClient

Payment Gateway
Razorpay Java SDK

Webhooks

Database
MySQL

Tools
Postman

Maven

Docker (optional)

🧪 Testing
API testing using Postman

Razorpay Test Mode

Manual webhook validation

DB state verification

🚀 Future Enhancements
JWT + Spring Security

React / Android frontend

Kafka event-driven payments

Retry & idempotency handling

Admin dashboard

Payment reconciliation

👨‍💻 Author
Akash Pradhan
M.Sc Computer Science & IT
Backend & Fintech Enthusiast
