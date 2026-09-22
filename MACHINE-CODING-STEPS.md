# LLD Machine Coding Interview — Step-by-Step Framework

The goal of an LLD machine coding round is **not** to immediately start writing classes and code.

The interviewer is evaluating whether you can:

- Understand an unfamiliar problem
- Clarify requirements
- Identify the right MVP scope
- Model entities and relationships
- Choose appropriate abstractions
- Decide whether persistence is required
- Design a clean interaction flow
- Implement incrementally
- Keep the code extensible and maintainable

The process below should be followed for almost every LLD machine coding problem.

---

## Overall Flow

```
Problem Statement
       ↓
Step 1: Overview / Understand
       ↓
Step 2: Gather MVP Requirements
       ↓
Step 3: Clarify Requirements & Edge Cases
       ↓
Step 4: Class Diagram
       ↓
Step 5: Schema Diagram (if required)
       ↓
Step 6: Working Code
       ↓
Test / Demonstrate / Discuss Extensions
```

---

## The 3 Questions You Must Always Answer First

Before implementation, explicitly answer these three questions.

### 1. What exactly am I building?

Define the system clearly, e.g.:

> "I am building a parking lot management system that allows users to park vehicles, generate tickets, calculate parking fees, and release parking spots."

This prevents the implementation from becoming unnecessarily large. Think in terms of:

- What is the system?
- What problem does it solve?
- Who uses it?
- What are the core operations?

### 2. Do I need to persist the data?

Determine whether data needs to survive after the application/process terminates.

| | **Option A — In-Memory** | **Option B — Database** |
|---|---|---|
| **Structure** | `Application → Java Collections (Map / List / Set)` | `Application → Repository → Database` |
| **Use when** | Interviewer doesn't require persistence; problem is focused on LLD/OOP; app exists only during execution; quick implementation expected | Requirements explicitly involve persistent data, transactions, restart recovery, historical records, large datasets, or DB-oriented queries |
| **Example** | `Map<Long, User> users;`<br>`Map<Long, ParkingSpot> spots;`<br>`List<Order> orders;` | Entities like `User`, `Order`, `Payment`, `Transaction` that need historical tracking |

**Ask the interviewer:**
> "Should the data persist after the application restarts, or is in-memory storage sufficient for this exercise?"

If they say "in-memory is enough" — don't introduce MySQL, JPA, Hibernate, etc.

### 3. How does the user interact with my application?

| Option | Flow | When to use |
|---|---|---|
| **REST API** | `Client → REST Controller → Service → Repository` | When the interviewer explicitly asks for APIs or a web-service style implementation |
| **Hard-Coded Execution** | `main() { parkingLot.parkVehicle(...); }` | Quick functionality demo, but hard to interact with dynamically |
| **Command Line Interface** | `Command Line → Command Parser → Service → Domain Objects → Repository / In-Memory Store` | **Generally preferred** when REST isn't required — demonstrates multiple scenarios without extra infra |

**CLI example:**
```
> create_user Shivraj
User created successfully
> create_order 101
Order created successfully
> cancel_order 101
Order cancelled successfully
```

**A good opening statement:**
> "Before I start designing the classes, I'll first confirm what exactly we're building, whether the data needs to persist, and how we'd like the user to interact with the system."

---

## Step 1 — Overview / Understand

Before thinking about classes, understand what the system is supposed to do.

### Situation A — I know the system
Don't assume your interpretation is correct — confirm it.

> "Let me first confirm my understanding of the system… Parking lot allows vehicles to enter, get a parking spot, pay the parking fee, and exit."

### Situation B — I don't know the system

```
Interviewer → Problem Statement → Ask Questions → Understand Domain → Confirm Understanding
```

> "I haven't worked with this domain before. Could you briefly explain how the current system is expected to work from a user's perspective?"

This is completely reasonable to ask in an interview.

### Align your understanding with the requirement

> "So, my understanding is that we are building X, which allows Y users to perform Z operations. For this exercise, I'll focus on A, B, C and leave D outside the MVP. Is that correct?"

### Entity vs. System

- **Entity** — a single thing in the domain (`User`, `Vehicle`, `ParkingSpot`, `Order`, `Product`, `Payment`, `Ticket`)
- **System** — coordinates entities and provides functionality:

```
Parking Lot System
        ├── Vehicle
        ├── ParkingSpot
        ├── Ticket
        └── Payment
```

Don't mistake an individual entity for the entire system.

---

## Step 2 — Gather Requirements

Identify MVP features. **Take ownership** rather than asking "what features should I implement?"

> "For the MVP, I suggest we support the following core features…" *(propose ~5–8 features)*

### Example — Parking Lot MVP

1. Create parking lot
2. Add parking spots
3. Park vehicle
4. Find available spot
5. Generate parking ticket
6. Calculate parking fee
7. Unpark vehicle
8. View parking availability

Then confirm:
> "Does this MVP cover the expected functionality, or would you like me to add/remove anything?"

### Why 5–8 features?

| Too few | Too many |
|---|---|
| You may miss important requirements | You may spend the whole interview on unnecessary functionality |

**Goal:** minimum functionality + meaningful domain behavior + enough complexity to demonstrate LLD.

---

## Step 3 — Clarify Requirements

For every feature, think through:

### 2.1 Happy Path

```
Vehicle arrives → Find available spot → Assign spot → Generate ticket
```

### 2.2 Edge Cases

Focus on edge cases that affect the design — you don't need to cover every possibility.

**Parking:**
- What if the parking lot is full?
- What if the vehicle is already parked?
- What if no suitable spot exists?
- What if an invalid vehicle is provided?

**Payment:**
- What if payment fails?
- What if payment is already completed?
- What if the amount is invalid?

**User:**
- What if the user doesn't exist?
- What if a duplicate user is created?

### 2.3 Future Scope

Separate MVP from possible future requirements — don't implement future scope unless asked, but design abstractions so reasonable extensions don't require a rewrite.

| MVP | Future |
|---|---|
| Car, Bike, Truck | Electric Vehicle, Handicapped Parking, Valet Parking, Reservation, Dynamic Pricing |

### Requirement Matrix (mental model)

| Feature | Happy Path | Edge Cases | Future Scope |
|---|---|---|---|
| Create User | User created | Duplicate user | User profiles |
| Create Order | Order created | Invalid product | Coupons |
| Payment | Payment succeeds | Payment failure | Multiple gateways |
| Cancel Order | Order cancelled | Already cancelled | Refund |

---

## Step 4 — Class Diagram

Only start designing classes once requirements are reasonably clear.

### Approach A — Outside-In

Start from system behavior:

```
User → System → Service → Domain Objects → Repositories
```

Example:
```
ParkingLotSystem → ParkingService → ParkingLot → ParkingSpot → Vehicle
```

### Approach B — Identify Nouns

Extract nouns from the requirements text.

> "A customer can place an order containing multiple products. The order can be paid using different payment methods."

**Nouns:** Customer, Order, Product, Payment, PaymentMethod

Then identify relationships:
```
Customer --places--> Order --contains--> Product
```

### Identify Abstractions

Ask: *"Is there a genuine variation or interchangeable behavior here?"*

```
        PaymentMethod
             ↑
      ┌──────┴──────┐
  CardPayment    UpiPayment
```

### Identify Interfaces

Use interfaces when behavior needs multiple implementations:

```java
interface PaymentProcessor {
    PaymentResult processPayment(Payment payment);
}

class CardPaymentProcessor implements PaymentProcessor { }
class UpiPaymentProcessor implements PaymentProcessor { }
```

Avoid creating an interface with only one implementation and no real variation, unless there's a clear design reason.

### Identify Abstract Classes

Use when multiple classes genuinely share **state + behavior + implementation**:

```
        Vehicle
          ↑
   ┌──────┼──────┐
  Car    Bike   Truck
```

If only behavior varies (no shared state/implementation), prefer an interface.

### Identify Enums

Use enums for a fixed set of domain states/types:

```java
enum VehicleType { CAR, BIKE, TRUCK }
enum OrderStatus { CREATED, CONFIRMED, CANCELLED, COMPLETED }
```

Prefer `OrderStatus.CANCELLED` over the string `"cancelled"`.

---

## Step 5 — Schema Diagram

Not mandatory for every LLD problem. Ask:

> "Is this primarily an object-oriented/in-memory system, or is persistent data modeling part of the requirement?"

### When schema design is useful

```
Application → Repository → Database
```

**Example tables:**

```
USER                ORDER               ORDER_ITEM
----                -----               ----------
id                  id                  id
name                user_id             order_id
email               status              product_id
                    created_at          quantity
```

**Relationships:**
```
USER  1 ──── N  ORDER
ORDER 1 ──── N  ORDER_ITEM
```

### When schema design is unnecessary

If the interviewer says "just implement this in memory" — don't spend time on MySQL, tables, indexes, foreign keys, or normalization unless specifically asked.

---

## Step 6 — Working Code

**Golden rule: implement one requirement at a time.** Don't write the entire system before testing anything.

### MVC-Oriented Structure

```
Controller → Service → Repository → Database / In-Memory Store
```

With domain objects alongside: Entities, Enums, Interfaces.

**Suggested package layout:**
```
src/main/java/com.example/
├── controller/
├── service/
├── repository/
├── model/
├── strategy/
└── exception/
```

For a pure LLD interview, don't over-engineer the package structure — match it to the problem.

### Implement Requirement-by-Requirement

Suppose the MVP contains: Create User → Create Product → Create Order → Make Payment → Cancel Order.

Don't write all five at once. Instead, cycle through:

```
Requirement 1 → Implement → Test →
Requirement 2 → Implement → Test → ...
```

This keeps a working system throughout the interview.

### Typical Implementation Flow (per requirement)

```
Requirement → Domain Model → Class/Interface → Service Logic → Repository → Interaction Layer → Test
```

**Example — "User should be able to create an order":**
```
Order → OrderService → OrderRepository → CLI / Controller
```

### Command-Line Driven Implementation

```
> create_user 1 Shivraj
User created
> create_product 101 Laptop
Product created
> create_order 1 101
Order created
> pay_order 1
Payment successful
> cancel_order 1
Order cancelled
```

---

## Final Interview Flow

```
0. UNDERSTAND       → What exactly are we building
1. GATHER           → Identify 5–8 MVP features
2. CLARIFY          → Edge cases + future scope
3. CLASS DIAGRAM    → Entities, relationships, interfaces, abstractions, enums
4. SCHEMA           → Only if persistence matters
5. IMPLEMENT        → MVC + one requirement at a time
              ↓
       TEST & DEMONSTRATE
```

---

## Interview Cheat Sheet

**1. Understand the system**
- [ ] Do I know this domain?
- [ ] If not, ask for clarification
- [ ] Explain my understanding
- [ ] Confirm scope

**2. Gather requirements**
- [ ] Suggest 5–8 MVP features
- [ ] Confirm features with interviewer

**3. Clarify**
- [ ] Happy path
- [ ] Important edge cases
- [ ] Future scope
- [ ] Explicitly separate MVP vs. future

**Always clarify**
- [ ] What exactly am I building?
- [ ] In-memory or database?
- [ ] REST / hard-coded / CLI?

**4. Class diagram**
- [ ] Identify entities
- [ ] Identify relationships
- [ ] Identify interfaces
- [ ] Identify abstract classes
- [ ] Identify enums
- [ ] Identify important behaviors

**5. Schema**
- [ ] Only if persistence is required
- [ ] Tables
- [ ] Relationships
- [ ] Important constraints

**6. Working code**
- [ ] Clean structure
- [ ] MVC where appropriate
- [ ] One requirement at a time
- [ ] Test each requirement
- [ ] Demonstrate working flow

---

## Golden Rule

**Don't start with code.**

```
Understand → Clarify → Scope → Model → Implement → Test → Extend
```

The interviewer should be able to follow your thought process from:

```
Requirement → Feature → Entity → Relationship → Design → Code
```

That traceability is one of the most important skills in an LLD machine coding interview.