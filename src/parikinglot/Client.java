package parikinglot;

import parikinglot.controller.TokenController;
import parikinglot.dto.request.IssueTokenRequest;
import parikinglot.dto.response.IssueTokenResponse;
import parikinglot.model.enums.VehicleType;
import parikinglot.repository.GateRepository;
import parikinglot.repository.ParkingLotRepo;
import parikinglot.repository.VehicleRepository;
import parikinglot.service.TokenService;

public class Client {

    public static void main(String[] args) {
        GateRepository gateRepository = new GateRepository();
        ParkingLotRepo parkingLotRepo = new ParkingLotRepo();
        VehicleRepository vehicleRepository = new VehicleRepository();
        TokenService tokenService = new TokenService(gateRepository,parkingLotRepo,vehicleRepository);
        TokenController tokenController = new TokenController(tokenService);

        IssueTokenRequest issueTokenRequest = new IssueTokenRequest("Virat","DL08GH3", VehicleType.FOUR_WHEELER,1,10);
        IssueTokenResponse response = tokenController.issueToken(issueTokenRequest);

        System.out.println(response.getMessage());
        System.out.println(response.getStatus());
    }
}