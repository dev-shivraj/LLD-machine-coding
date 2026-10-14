# BookMyShow: System Design Documentation

## Step 1: Alignment

**Goal:** Confirm that everyone shares the same understanding of the system before designing it.

We are designing **BookMyShow**, a movie booking platform where a user can:

- Search for movies and theaters in their city
- Book tickets for a particular movie

**Confirmed:** Yes, this is the system we are designing.

### Clarifying Questions

| # | Question | Answer |
|---|----------|--------|
| 1 | Is it a working system or just an entity design? | **Working system** |
| 2 | Are we going to persist the data? | **Yes** |
| 3 | How will the user interact with it (CLI, hardcoding, REST API, etc.)? | **CLI-based** |

---

## Step 2: Requirement Gathering

Requirements can be gathered through **visualization**, **user journey**, or a combination of both.

> **Key insight:** We never book a ticket for a *movie*; we book a ticket for a *show*. For example, we book the 09:12 show, not the movie itself.

### User Journey

```
User → Select City → View Movies → View Theaters → View Shows → Select Seats → Payment
```

Before finalizing, check whether any requirement is missing from this journey and clarify it.

---

## Requirements

### Booking and Scope

1. Users can book movies. Only movie booking is supported.
2. Users book tickets for a **show**.
3. In one booking, a user can select a **maximum of 10 seats**.
4. No two users can book the same seat for the same show.

### Venue Structure

5. The platform supports multiple cities, and each city can have multiple theaters.
6. Each theater has multiple screens, and each screen has multiple seats.
7. A screen can run only one movie at a time.

### Shows and Seats

8. A **show** is a particular movie running at a particular date, time, and screen.
9. Multiple seat types are supported: `GOLD`, `SILVER`, `PLATINUM`, `RECLINER`, etc.
10. Price is a function of **show + seat type**.

### Payments

11. Only online payment is supported.
12. Partial payments are supported, so a single booking can be paid through a mix of methods (coupon, wallet, UPI, etc.).

### Movie Information

13. For every movie, we store details such as **cast**, **genre**, and **language**.
14. Each movie supports multiple formats: `2D`, `3D`, `IMAX`.