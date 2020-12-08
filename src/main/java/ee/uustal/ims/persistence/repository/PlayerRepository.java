package ee.uustal.ims.persistence.repository;

import ee.uustal.ims.persistence.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE p.username = ?1")
    Optional<Player> findByUserName(String username);

}