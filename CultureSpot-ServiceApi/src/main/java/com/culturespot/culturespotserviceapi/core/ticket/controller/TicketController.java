package com.culturespot.culturespotserviceapi.core.ticket.controller;

import com.culturespot.culturespotdomain.core.ticket.entity.TicketSort;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketDetailResponse;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketResponse;
import com.culturespot.culturespotserviceapi.core.ticket.service.TicketService;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticketbook")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public TicketListResponse getTickets(
            @Auth User user,
            @RequestParam(required = false) TicketSort sort,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) String keyword
    ) {
        return ticketService.getTickets(user, sort, year, rating, keyword);
    }

    @GetMapping("/{ticketId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public TicketResponse getTicket(
            @Auth User user,
            @PathVariable Long ticketId
    ) {
        TicketDetailResponse detail = ticketService.getTicket(user, ticketId);
        return new TicketResponse(detail);
    }
}
