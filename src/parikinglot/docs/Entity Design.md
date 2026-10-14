Parking Lot
Parking Floor
Parking Slots
Vehicle
Vehicle type
Admin
Slot Assignment Strategy
Pricing Strategy
Token
Bill
User
Payment Mode



Entity :
Parking Lot
Parking Floor
Parking Slot
Vehicle


# Class Diagram
```mermaid
classDiagram
    class ParkingLot {
        -List~ParkingFloor~ floors
        -List~Gate~ gates
        -Set~Vehicle~ allowed Vehicle
        -ParkingLotStatus status

        -PricingStrategy pricingStrategy
        -AllocationStrategy allocationStrategy
    }
 
    class ParkingFloor {
        -int id
        -List~ParkingSlot~ slots
        -String floor

    }

    class ParkingSlot {
        -String slotNumber
        -VehicleType VehicleType
        -SlotStatus status
        -Vehicle practice.vehicle
        -floor
    }

    class Gate {
        -GateType gateType
        -String gateNumber
        -String operator
        -gateStatus gateStatus
    }

    class GateType {
        <<enumeration>>
    }

    class Vehicle {
        -vehicleType
        -vehicleNumber
        -ownerName
        -token
    }

    class Token {
        -tokenNumber
        -entryTime
        -practice.vehicle
        -Gate gate
        -slot
    }

    class Bill {
        -id
        -amount
        -exitTime
        -token
        -operator
        -payment

    }

    note for Bill "Payment can be list<Payment> as well, in case suppose if we do half payment through upi and half through the cash"

    class Payment {
        -paymentMode
        -status
        -amountPaid
        -time
        -bill
    }

    class Operator {
        -empId
        -gate
    }

    class User {
        -id
        -name
        -contact
        -List<Vehicle>
    }

%% Operator is for generating bills etc
%% we are not going to code for user in our scenario as it is not much require

```