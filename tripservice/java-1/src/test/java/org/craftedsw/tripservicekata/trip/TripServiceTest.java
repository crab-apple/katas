package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        TripService tripService = new TripService(userSession);

        // Then
        assertThrows(
                UserNotLoggedInException.class,
                // When
                () -> tripService.getTripsByUser(new User())
        );
    }

    @Test
    void ifUserIsNull_throwsNPE() {
        // Given
        User loggedUser = new User();
        UserSession userSession = mock(UserSession.class);
        when(userSession.getLoggedUser()).thenReturn(loggedUser);
        TripService tripService = new TripService(userSession);

        // Then
        assertThrows(
                NullPointerException.class,
                // When
                () -> tripService.getTripsByUser(null)
        );
    }

    @Test
    void ifUserIsNotFriendOfLoggedUser_returnsEmptyList() {
        // Given
        User loggedUser = new User();
        User queriedUser = new User();
        queriedUser.addTrip(new Trip());
        UserSession userSession = mock(UserSession.class);
        when(userSession.getLoggedUser()).thenReturn(loggedUser);
        TripService tripService = new TripService(userSession);

        // When
        List<Trip> trips = tripService.getTripsByUser(queriedUser);

        // Then
        assertTrue(trips.isEmpty());
    }

}
