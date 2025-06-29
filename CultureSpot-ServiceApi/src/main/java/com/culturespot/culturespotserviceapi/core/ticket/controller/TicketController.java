package com.culturespot.culturespotserviceapi.core.ticket.controller;

import com.culturespot.culturespotdomain.core.ticket.entity.TicketSort;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.ticket.dto.request.TicketCreateWrapper;
import com.culturespot.culturespotserviceapi.core.ticket.dto.request.TicketUpdateWrapper;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketCreateResponse;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketDetailResponse;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketResponse;
import com.culturespot.culturespotserviceapi.core.ticket.service.TicketService;
import com.culturespot.culturespotserviceapi.core.ticket.dto.response.TicketListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "티켓북 API")
@RestController
@RequestMapping("/api/ticketbook")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_USER')")
public class TicketController {
    private final TicketService ticketService;

    @Operation(summary = "티켓 목록 조회")
    @GetMapping
    public TicketListResponse getTickets(
            @Auth User user,
            @RequestParam(required = false) TicketSort sort,
            @Parameter(description = "티켓 내 직접 설정한 방문 일자의 연도(startDate) 기준") @RequestParam(required = false) Integer year,
            @Parameter(description = "티켓 평점(1~5 사이의 정수)") @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) String keyword
    ) {
        return ticketService.getTickets(user, sort, year, rating, keyword);
    }

    @Operation(summary = "개별 티켓 조회")
    @GetMapping("/{ticketId}")
    public TicketResponse getTicket(
            @Auth User user,
            @PathVariable Long ticketId
    ) {
        TicketDetailResponse detail = ticketService.getTicket(user, ticketId);
        return new TicketResponse(detail);
    }

    @Operation(summary = "티켓 작성")
    @PostMapping
    public TicketCreateResponse createTicket(
            @Auth User user,
            @RequestBody TicketCreateWrapper requestWrapper
            ) {
        return ticketService.createTicket(user, requestWrapper.ticket());
    }

    @Operation(summary = "티켓 수정")
    @PutMapping("/{ticketId}")
    public TicketCreateResponse updateTicket(
            @Auth User user,
            @PathVariable Long ticketId,
            @RequestBody TicketUpdateWrapper requestWrapper
            ) {
        return ticketService.updateTicket(user, ticketId, requestWrapper.ticket());
    }

    @Operation(summary = "티켓 삭제")
    @DeleteMapping("/{ticketId}")
    public void deleteTicket(
            @Auth User user,
            @PathVariable Long ticketId
            ) {
        ticketService.deleteTicket(user, ticketId);
    }
}
