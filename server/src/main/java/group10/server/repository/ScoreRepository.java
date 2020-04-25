package group10.server.repository;

import group10.server.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>{

    @Query("select s from Score s where s.createdAt >= :date order by s.score desc")
    List<Score> findAllWeekly(
            @Param("date") LocalDate date);

    @Query("select s from Score s where s.createdAt >= :date order by s.score desc")
    List<Score> findAllMonthly(
            @Param("date") LocalDate date);

    @Query("select s from Score s order by s.score desc")
    List<Score> findAllAllTime( );
}
