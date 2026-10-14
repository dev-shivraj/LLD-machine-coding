package parikinglot.repository;

import parikinglot.model.Gate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GateRepository {

    private Map<Integer,Gate> gateDb;
    private static int gateId = 0;

    public GateRepository() {
        this.gateDb = new HashMap<>();
    }

    public Optional<Gate> findById(int gateId) {
        Gate gate = gateDb.get(gateId);
        if(gate==null) {
            return Optional.empty();
        }
        return Optional.of(gate);
    }

    // String fun() { retrn 5
}