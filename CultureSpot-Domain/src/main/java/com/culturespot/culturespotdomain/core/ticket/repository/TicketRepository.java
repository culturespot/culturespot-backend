package com.culturespot.culturespotdomain.core.ticket.repository;

import com.culturespot.culturespotdomain.core.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("""
        SELECT t FROM Ticket t
        JOIN FETCH t.performance p
        WHERE t.user.id = :userId
        AND (:year IS NULL OR FUNCTION('YEAR', t.startDate) = :year)
        AND (:rating IS NULL OR t.rating = :rating)
        AND (:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
        ORDER BY
            CASE WHEN :sort = 'LATEST_CREATED' THEN t.createdAt END DESC,
            CASE WHEN :sort = 'OLDEST_CREATED' THEN t.createdAt END ASC,
            CASE WHEN :sort = 'LATEST_VISITED' THEN t.startDate END DESC,
            CASE WHEN :sort = 'OLDEST_VISITED' THEN t.startDate END ASC
    """)
    List<Ticket> findTickets(@Param("userId") Long userId,
                                     @Param("year") Integer year,
                                     @Param("rating") Integer rating,
                                     @Param("keyword") String keyword,
                                     @Param("sort") String sort
    );
}
