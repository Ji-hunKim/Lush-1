package event.dao;

import event.domain.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface EventDao {

    public List<Event> selectEventList(Connection conn, int eventStatus, int currentPage, int numberPerPage) throws SQLException;

    public List<Event> searchEventList(Connection conn, int eventStatus ,int currentPage, int numberPerPage, int condition, String key) throws SQLException;

    public int getTotalPages(Connection conn, int numberPerPage, int eventStatus) throws SQLException;
}
