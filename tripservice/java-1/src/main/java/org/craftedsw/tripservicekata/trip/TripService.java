package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private final UserSession userSession;
    private final ITripDAO tripDAO;

    public TripService() {
        this.userSession = null;
        this.tripDAO = TripDAO::findTripsByUser;
    }

    public TripService(UserSession userSession, ITripDAO tripDAO) {
        this.userSession = userSession;
        this.tripDAO = tripDAO;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {

        User loggedUser = getUserSession().getLoggedUser();
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }

        if (user.getFriends().contains(loggedUser)) {
            return tripDAO.findTripsByUser(user);
        }

        return new ArrayList<>();
    }

    private UserSession getUserSession() {
        if (userSession != null) {
            return userSession;
        }
        return UserSession.getInstance();
    }
}
