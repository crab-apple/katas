package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripServiceTest {

    private UserSession userSession;
    private TripService tripService;
    private ITripDAO tripDAO;

    @BeforeEach
    void setup() {
        userSession = mock(UserSession.class);
        tripDAO = mock(ITripDAO.class);
        tripService = new TripService(userSession, tripDAO);
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
    void ifThereIsNoFriendship_returnsEmptyList() {
        // Given
        User loggedUser = new User();
        when(userSession.getLoggedUser()).thenReturn(loggedUser);

        User queriedUser = new User();
        when(tripDAO.findTripsByUser(queriedUser))
                .thenReturn(List.of(
                        new Trip(),
                        new Trip()
                ));

        // When
        List<Trip> trips = tripService.getTripsByUser(queriedUser);

        // Then
        assertTrue(trips.isEmpty());
    }

    @Test
    void ifQueriedUserIsFriendOfLoggedUser_returnsEmptyList() {
        // Given
        User loggedUser = new User();
        when(userSession.getLoggedUser()).thenReturn(loggedUser);

        User queriedUser = new User();
        when(tripDAO.findTripsByUser(queriedUser))
                .thenReturn(List.of(
                        new Trip(),
                        new Trip()
                ));

        loggedUser.addFriend(queriedUser);

        // When
        List<Trip> trips = tripService.getTripsByUser(queriedUser);

        // Then
        assertTrue(trips.isEmpty());
    }

    @Test
    void ifLoggedUserIsFriendOfQueriedUser_returnsEmptyList() {
        // Given
        User loggedUser = new User();
        when(userSession.getLoggedUser()).thenReturn(loggedUser);

        User queriedUser = new User();
        when(tripDAO.findTripsByUser(queriedUser))
                .thenReturn(List.of(
                        new Trip(),
                        new Trip()
                ));

        queriedUser.addFriend(loggedUser);

        // When
        List<Trip> trips = tripService.getTripsByUser(queriedUser);

        // Then
        assertEquals(2, trips.size());
    }
}
