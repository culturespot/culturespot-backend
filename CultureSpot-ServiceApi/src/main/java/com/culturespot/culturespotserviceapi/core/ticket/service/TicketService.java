package com.culturespot.culturespotserviceapi.core.ticket.service;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.ticket.entity.Ticket;
import com.culturespot.culturespotdomain.core.ticket.entity.TicketSort;
import com.culturespot.culturespotdomain.core.ticket.repository.TicketRepository;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.PerformanceInfoResponse;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketDetailResponse;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketListResponse;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketListResponse getTickets(User user, TicketSort sort, Integer year, Integer rating, String keyword) {

        List<Ticket> tickets = ticketRepository.findTickets(user.getId(), year, rating, keyword, (sort != null ? sort.name() : TicketSort.LATEST_CREATED.name()));

        List<Integer> years = tickets.stream()
                .map(ticket -> ticket.getStartDate().getYear())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        List<TicketSummaryResponse> ticketResponses = tickets.stream()
                .map(ticket -> new TicketSummaryResponse(
                        ticket.getId(),
                        ticket.getStartDate(),
                        ticket.getEndDate(),
                        ticket.getRating(),
                        new PerformanceInfoResponse(
                                ticket.getPerformance().getId(),
                                ticket.getPerformance().getTitle(),
                                ticket.getPerformance().getType().name(),
                                ticket.getPerformance().getCategory().name(),
                                ticket.getPerformance().getPlace(),
                                ticket.getPerformance().getPerformanceInfo().getImageUrl()
                        )
                ))
                .toList();

        return new TicketListResponse(years, ticketResponses);
    }

    public TicketDetailResponse getTicket(User user, Long ticketId) {

        Ticket ticket = ticketRepository.findByIdAndUserId(ticketId, user.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 티켓이 존재하지 않습니다"));

        Performance p = ticket.getPerformance();

        return new TicketDetailResponse(
                ticket.getId(),
                ticket.getStartDate(),
                ticket.getEndDate(),
                ticket.getRating(),
                ticket.getTicketTitle(),
                ticket.getContent(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                new PerformanceInfoResponse(
                    p.getId(),
                    p.getTitle(),
                    p.getType().name(),
                    p.getCategory().name(),
                    p.getPlace(),
                    p.getPerformanceInfo().getImageUrl()
                )
        );
    }
}
