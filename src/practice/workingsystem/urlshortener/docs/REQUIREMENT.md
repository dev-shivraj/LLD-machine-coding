# URL Shortener — LLD Requirements

*Low-Level Design exercise: a Bitly-style URL shortening service in Java*

**Status:** Requirement clarification  |  **Last updated:** October 24, 2026

## 1. Problem Statement

Design and implement a URL shortening service similar to Bitly.

The system converts long URLs into short, unique URLs that are easy to share. When someone accesses a short URL, the system redirects them to the original URL with low latency.

The service also supports **custom aliases**, **configurable expiration**, and **click analytics**.

> **Primary objective:** design the classes, define their relationships, and implement the core business logic using object-oriented design principles.

## 2. Goals

- Understand the functional requirements and business rules.
- Identify the core entities and their responsibilities.
- Design classes and relationships using UML class diagrams.
- Apply appropriate object-oriented design principles.
- Generate unique short codes.
- Implement URL creation and redirection.
- Support custom aliases and expiration.
- Track click counts and timestamps.
- Write a working Java implementation demonstrating the core functionality.

## 3. Functional Requirements

| ID | Requirement |
| --- | --- |
| FR-1 | Create a short URL |
| FR-2 | Generate unique short codes |
| FR-3 | Redirect to the original URL |
| FR-4 | Support custom aliases |
| FR-5 | Support configurable expiration |
| FR-6 | Track click counts |
| FR-7 | Track click timestamps |

### FR-1: Create a Short URL

The system must accept a valid long URL and generate a corresponding short URL. The generated short URL must resolve to the original URL.

**Example**

```
Input:   https://example.com/products/electronics/laptops
Output:  https://bit.ly/abc123
```

### FR-2: Generate Unique Short Codes

- Every active short URL mapping must have a unique short code.
- The system must prevent a generated code from overwriting an existing mapping.
- The generation mechanism must be designed so the strategy can be changed independently of the URL-shortening business logic.

**Candidate strategies**

| Strategy | Notes |
| --- | --- |
| Random code generation | Simple; requires collision checks |
| Sequential ID encoding (Base62) | Unique by construction |
| Hash-based generation | Requires collision handling |

The final strategy will be selected during the design and implementation stages.

### FR-3: Redirect to the Original URL

When someone accesses a short URL, the system must:

1. Extract the short code.
2. Find the corresponding URL mapping.
3. Check whether the mapping exists.
4. Check whether the mapping has expired.
5. Redirect to the original URL if the mapping is valid.
6. Reject the request if the mapping does not exist or has expired.

Additional requirements:

- The implementation must **distinguish between a nonexistent short code and an expired mapping**.
- The system should support efficient short-code lookups to enable low-latency redirects.

### FR-4: Support Custom Aliases

Users must be able to specify a custom alias instead of relying exclusively on auto-generated codes.

**Example**

```
Original URL:  https://example.com/my-portfolio
Custom alias:  myportfolio
Short URL:     https://bit.ly/myportfolio
```

**Rules the system must enforce**

- A custom alias must satisfy the configured format and length restrictions.
- An alias must not overwrite an existing mapping.
- A request for an unavailable alias must be rejected.
- Alias reuse after expiration must follow an explicitly defined policy.

> **Open item:** the exact validation rules and alias-reuse policy will be finalized during requirement clarification.

### FR-5: Support Configurable Expiration

Users must be able to configure how long a shortened URL remains valid, using a configurable time-to-live (TTL).

**Example**

```
Creation time:  October 24, 2026, 10:00 AM
TTL:            24 hours
Expiration:     October 25, 2026, 10:00 AM
```

A shortened URL must not redirect to its original destination after expiration.

The system should:

- Store the expiration timestamp.
- Determine whether a mapping has expired when processing a redirect.
- Reject redirects for expired mappings.
- Distinguish expired mappings from mappings that never existed.

For the initial implementation, expiration is checked **at access time**. A background cleanup mechanism is not required.

> **Open item:** the behavior of URLs without an explicitly configured TTL will be finalized during requirement clarification.

### FR-6: Track Click Counts

The system must track the number of successful accesses to a short URL. The click count increases when a valid short URL is successfully resolved for redirection.

**Example**

```
Short code:    abc123
Total clicks:  3
```

Requests that fail because a code is invalid or a URL has expired do **not** count as successful clicks in the initial implementation.

### FR-7: Track Click Timestamps

The system must record the timestamp of each successful redirect request. The recorded events must allow the system to determine the total number of clicks and inspect individual click times.

**Example**

```
Short code: abc123
Click timestamps:
  - 2026-10-24 10:00:01
  - 2026-10-24 10:05:12
  - 2026-10-24 10:20:45
```

For the initial implementation, click events are retained in memory.

## 4. Business Rules and Design Decisions

This section records decisions made during requirement clarification.

### BR-1: Repeated Submission of the Same Long URL

**Decision:** reuse an existing mapping only when its configuration is compatible.

Submitting the same long URL multiple times does not automatically mean every request should create a new mapping, or return the same short URL. The system should consider the requested configuration:

- Original URL
- Custom alias, if supplied
- Expiration policy

If an existing mapping is compatible with the new request, the system may reuse it. If the configuration differs, the system must apply an explicitly defined policy rather than silently reusing an incompatible mapping.

> **Open item:** the precise matching rules and behavior for conflicting requests will be finalized before implementation.

### BR-2: Short-Code Uniqueness

- A short code must identify no more than one URL mapping at a time.
- Code generation must account for collisions.
- The system must never silently replace an existing mapping when a collision occurs.

### BR-3: Custom Alias Uniqueness

- A custom alias must not overwrite an existing mapping.
- A conflicting alias request must be rejected, unless the existing mapping has become eligible for reuse under the finalized expiration policy.

### BR-4: Expired URLs

- An expired URL must not redirect to its original destination.
- Expiration is checked during redirect processing.
- Physically deleting expired mappings is not required.

### BR-5: Click Analytics

- Only successful redirect requests are recorded as click events.
- Each click event includes its timestamp.
- The total click count can be derived from the number of recorded click events.

## 5. Non-Functional Requirements

| ID | Requirement | Description |
| --- | --- | --- |
| NFR-1 | Low-latency redirection | Resolving a short code must be efficient, since redirection is the primary operation. Use a data structure that supports efficient lookup by short code. |
| NFR-2 | Correctness | Short codes and original URLs must stay consistent, and existing mappings must never be accidentally overwritten. |
| NFR-3 | Maintainability | Responsibilities are separated so that changes to code generation, URL storage, or analytics do not unnecessarily affect unrelated business logic. |
| NFR-4 | Extensibility | New features can be added without a complete redesign. |

**Potential future enhancements** (not part of the initial implementation)

- Persistent database storage
- Caching
- User accounts
- Rate limiting
- Detailed analytics
- Background cleanup of expired URLs

## 6. Scope

| In scope | Out of scope |
| --- | --- |
| Create short URLs | REST API implementation |
| Generate unique short codes | Database integration |
| Redirect to original URLs | Distributed caching |
| Support custom aliases | User authentication and authorization |
| Configure URL expiration | Rate limiting |
| Track click counts | QR-code generation |
| Record click timestamps | Geographic analytics |
| Validate relevant inputs | Background expiration cleanup |
| Handle missing and expired mappings | Distributed deployment and high availability |
| Demonstrate functionality through a Java client class |  |

The initial implementation focuses on **core business logic rather than infrastructure**.

## 7. Proposed Implementation Approach

The solution will be built incrementally.

### Step 1: Requirement Clarification

Finalize the business rules, including:

- Repeated submission of the same long URL
- Custom alias validation and conflicts
- Default expiration behavior
- TTL validation and expiration handling
- Click analytics semantics
- Invalid URL handling

### Step 2: Identify Entities and Responsibilities

Identify the classes required to represent URL mappings, coordinate shortening and redirection, generate codes, and record analytics. The final class list will be determined after requirement clarification.

### Step 3: Design the UML Class Diagram

Define:

- Classes and their responsibilities
- Attributes and methods
- Relationships between classes
- Interfaces and implementations, where justified
- Associations, aggregation, and composition, where appropriate

### Step 4: Implement the Core Logic

Implement the functionality in Java, one responsibility at a time.

### Step 5: Demonstrate the Implementation

Create a client class that demonstrates:

- Creating a short URL
- Generating a short code
- Resolving a short URL
- Using a custom alias
- Handling expired URLs
- Recording click events
- Retrieving click analytics
- Handling invalid and conflicting requests

Automated testing and external infrastructure are not required for the initial demonstration.

## 8. Acceptance Criteria

The implementation is complete when all of the following are satisfied:

- A valid long URL can be shortened.
- A generated short code is unique among existing mappings.
- A short URL resolves to the correct original URL.
- An existing mapping cannot be silently overwritten.
- A valid custom alias can be used.
- An unavailable or invalid alias is rejected.
- URL expiration is configurable.
- An expired URL cannot redirect to its original destination.
- An unknown short code is handled appropriately.
- Successful redirects increment the click count.
- Successful redirects record their timestamps.
- Click analytics can be retrieved for a short URL.
- The core functionality can be demonstrated using a Java client class.

## 9. Current Progress

**Current stage:** Requirement clarification

| Completed | Next |
| --- | --- |
| Problem statement established | Continue clarifying the remaining business rules before designing the class diagram |
| Functional requirements identified |  |
| Initial scope defined |  |
| Repeated-submission policy provisionally decided |  |

### Open Items Summary

- Alias validation rules and alias-reuse policy (FR-4)
- Default behavior for URLs without a TTL (FR-5)
- Precise matching rules for repeated submissions (BR-1)
- Invalid URL handling

## 10. Final Objective

Design a clean, maintainable, and extensible URL shortening service that satisfies the agreed requirements.

The final solution should demonstrate sound object-oriented design, clear separation of responsibilities, correct handling of URL mappings and expiration, and functional click analytics.
