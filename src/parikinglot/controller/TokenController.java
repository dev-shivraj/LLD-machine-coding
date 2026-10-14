package parikinglot.controller;


import parikinglot.dto.request.IssueTokenRequest;
import parikinglot.dto.response.IssueTokenResponse;
import parikinglot.model.Token;
import parikinglot.model.enums.ResponseStatus;
import parikinglot.service.TokenService;

public class TokenController {

    private TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // Request
    public IssueTokenResponse issueToken(IssueTokenRequest request) {
        IssueTokenResponse response = new IssueTokenResponse();
        try {
            Token token = tokenService.issueToken(request.getGateId(),
                    request.getOwnerName(),
                    request.getVehicleNumber(),
                    request.getVehicleType(),
                    request.getParkingLotId());
            response.setTokenNo(token.getTokenNo());
            response.setSlotNo(token.getAssignedSlot().getSlotNumber());
            response.setMessage("Token issued successfully");
            response.setStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e) {
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Failed to issue ticket" + e.getMessage());
        }
        return response;
    }
}