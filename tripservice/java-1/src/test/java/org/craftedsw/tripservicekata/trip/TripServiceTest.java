package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripServiceTest {

    private UserSession userSession;
    private TripService tripService;

    @BeforeEach
    void setup() {
        userSession = mock(UserSession.class);
        tripService = new TripService(userSession);
    }

    @Test
    void ifNotLoggedIn_throwsNotLoggedInException() {

        // Given
        when(userSession.getLoggedUser()).thenReturn(null);

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
        when(userSession.getLoggedUser()).thenReturn(loggedUser);

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
        when(userSession.getLoggedUser()).thenReturn(loggedUser);

        // When
        List<Trip> trips = tripService.getTripsByUser(queriedUser);

        // Then
        assertTrue(trips.isEmpty());
    }

}
