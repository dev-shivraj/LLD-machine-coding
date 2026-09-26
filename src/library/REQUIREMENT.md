# Library Management System — Requirements

## 1. Problem Statement

Design a basic **Library Management System** that manages a collection of books and provides basic operations to add, remove, search, and list books.

The first version focuses only on **book management**.

---

## 2. Book Details

Each book contains:

| Field | Notes |
|---|---|
| ISBN | Uniquely identifies a logical book |
| Title | — |
| Author | — |
| Publication Year | — |

**ISBN**
- Uniquely identifies a logical book.
- Two different books cannot have the same ISBN.
- ISBN search is based on an **exact match**.

---

## 3. Functional Requirements

### 3.1 Add a Book

**Rules**
- The book's ISBN must be unique.
- If the ISBN does not already exist, the book should be added.
- If the ISBN already exists, the operation should be **rejected** and the system should report that the ISBN already exists.
- The existing book must **not** be overwritten.

**Example**

| | ISBN | Title |
|---|---|---|
| Existing | 12345 | Clean Code |
| Attempt | 12345 | Some Other Book |

**Result:** Reject — ISBN already exists.

---

### 3.2 Remove a Book

**Rules**
- Removal is based on the book's ISBN.
- Removing a book means removing the entire logical book record associated with that ISBN.
- Physical copies are not modeled in this version.
- If the ISBN does not exist, the operation should be **rejected** and the system should report that the book does not exist.

**Example**

```
Remove: ISBN 12345
Result: The logical book with ISBN 12345 is removed.
```

---

### 3.3 Search Book by ISBN

**Rules**
- ISBN search uses exact matching.
- Partial ISBN searches are not supported.
- Since ISBN is unique, an ISBN search can identify at most one logical book.

**Example**

```
Search: 9780132350884  →  Clean Code
Search: 978013          →  no match (partial ISBNs don't match)
```

---

### 3.4 Search Books by Title

**Rules**
- Title search is **case-insensitive**.
- Title search supports **partial/substring** matching.
- Multiple books can match the same title search.
- Fuzzy search and typo correction are not required.

**Example**

Given the library contains:
- Clean Code
- Clean Architecture
- Effective Java
- Java Concurrency in Practice

| Search term | Result |
|---|---|
| `clean` | Clean Code, Clean Architecture |
| `Java` | Effective Java, Java Concurrency in Practice |
| `CLEAN` | same as `clean` (case-insensitive) |

---

### 3.5 List All Books

**Rules**
- Return all books currently present in the library.
- No particular ordering is required.
- Insertion order does not need to be preserved.

**Example:** if the library contains `Book A, Book B, Book C`, both `[A, B, C]` and `[C, A, B]` are valid results.

---

## 4. Business Rules

1. ISBN must be unique.
2. A book cannot be added if its ISBN already exists.
3. Adding a duplicate ISBN must not overwrite the existing book.
4. A book is removed using its ISBN.
5. Removing a non-existent ISBN should be rejected/reported.
6. ISBN search requires an exact match.
7. Title search is case-insensitive.
8. Title search supports partial/substring matching.
9. Title search can return multiple books.
10. The ordering of `listAllBooks()` is not important.

---

## 5. Out of Scope

The following are intentionally excluded from the current version:

| Area | Excluded items |
|---|---|
| **Physical Copies** | Individual copies (e.g. ISBN → 5 physical copies) — we model only a single logical book record |
| **Members / Users** | Library members, user accounts, membership IDs, authentication |
| **Borrowing & Returning** | Borrow/return book, availability, due dates, borrowing history |
| **Reservations** | Book reservation/waitlist functionality |
| **Fines** | Late fees, fine calculation, fine payment |
| **Notifications** | Email, SMS, push notifications, etc. |
| **Persistence** | Database, file storage, Redis/cache, external storage |
| **API / UI** | REST APIs, controllers, frontend/UI, CLI |
| **Concurrency** | Concurrent access and thread-safety |

The current version focuses on the domain/problem itself.

---

## 6. Example Scenario

**Books:**

| ISBN | Title | Author | Publication Year |
|---|---|---|---|
| 111 | Clean Code | Robert C. Martin | 2008 |
| 222 | Clean Architecture | Robert C. Martin | 2017 |
| 333 | Effective Java | Joshua Bloch | 2018 |

**Add**
```
addBook(111)
→ if ISBN 111 does not exist:  Book added
→ if ISBN 111 already exists:  Reject — ISBN already exists
```

**Search by ISBN**
```
searchByISBN(111)  →  Clean Code
```

**Search by Title**
```
searchByTitle("clean")  →  Clean Code, Clean Architecture
```

**Remove**
```
removeBook(111)  →  Clean Code is removed
```

**List**
```
listAllBooks()  →  Clean Architecture, Effective Java   (ordering not important)
```

---

## 7. Requirement Summary

> Build a basic Library Management System that manages logical books identified uniquely by ISBN. The system should allow books to be added and removed, searched by exact ISBN, searched by case-insensitive partial title, and listed. Duplicate ISBNs and removal of non-existent books should be rejected/reported. Physical copies, users, borrowing, reservations, fines, persistence, APIs, UI, and concurrency are outside the scope of this version.

---

## 8. Current Scope

```
Library Management System
│
├── Book Management
│     ├── Add Book
│     ├── Remove Book
│     ├── Search by ISBN
│     ├── Search by Title
│     └── List All Books
│
├── ISBN
│     ├── Unique
│     └── Exact Search
│
└── Title Search
      ├── Case-insensitive
      ├── Partial Match
      └── Multiple Results
```

