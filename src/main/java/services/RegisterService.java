package services;

import services.requests.RegisterRequest;
import services.response.RegisterResponse;

/**
 * RegisterService receives a RegisterRequest from the handler
 * Returns a response containing the authtoken of the new user
 */

public class RegisterService {
    /**
     * Creates a new user account
     * Generates 4 generations of ancestor data for the new user
     * Logs in the user
     * @param registerRequest Object containing the new user data
     * @return RegisterResponse, an object containing the authtoken
     */

    public RegisterResponse register(RegisterRequest registerRequest) {

        return null;
    }
}
