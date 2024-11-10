package org.craftedsw.tripservicekata.trip;

import org.junit.jupiter.api.Test;

public class TripServiceSignatureTest {

    @Test
    void hasNoArgumentsConstructor() {
        new TripService();
    }

    @Test
    void getTripsByUserSignature() {
        // Just checking that it compiles
        try {
            new TripService().getTripsByUser(null);
        } catch (Exception ex) {

        }
    }
}
