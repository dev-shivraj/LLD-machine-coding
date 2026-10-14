## Parking Lot LLD Design requirement gathering

- We are not persisting the data in db
- we can store in in-memory or MAP
- How user intraction will happen : hardcode the input : through the client class


> It is not just an entity design, we needed complete working software for this


---

## Requirement Gathering
1. **Parking lot will have multiple parking floors**
2. **Each floor will have multiple parking slots**
3. **A parking slot will accomodate one type of practice.vehicle**
4. **Vehicle can be of multiple types**
5. **A parking lot will be assigned at the time of entry**
6. **Admin should be able to decide the slot assignment strategy**
7. **Admin should be able to decide the pricing strategy**
8. **Pricing log will have multiple entry and exit gates. Exit can't happen via entry gets and vice versa**
9. **Store the information of practice.vehicle**
10. **A token is generated at the time of entry**
11. **A bill has to be generated at the time of exit**
12. **Bill will have the amount based on the pricing strategy for the duration of parking.**
13. **User has to make payment.**
14. **Payment can be done via cash or UPI(which payment gateway)**

> Payment processing information we will not be going to implement as it again comes with lot of requirement and their own implementation. So in our case we will keep it simple
