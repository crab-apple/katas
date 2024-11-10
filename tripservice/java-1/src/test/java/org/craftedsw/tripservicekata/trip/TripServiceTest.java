package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripServiceTest {

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

    @Test
    void ifNotLoggedIn_throwsNotLoggedInException() {

        // Given
        UserSession userSession = mock(UserSession.class);
        when(userSession.getLoggedUser()).thenReturn(null);
        TripService tripService = new TripService();
        tripService.setInjectedSession(userSession);

        // Then
        assertThrows(
                UserNotLoggedInException.class,
                // When
                () -> tripService.getTripsByUser(new User())
        );
    }
}
