package cinema.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllBySeanceId(long seanceId);
    List<Ticket> findAllByUserId(long userId);
    int countByUserId(long userId);
}

