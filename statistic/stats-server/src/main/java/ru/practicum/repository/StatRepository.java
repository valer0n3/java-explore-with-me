package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.StatGetModel;
import ru.practicum.model.StatModel;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<StatModel, Long> {
    @Query("SELECT new ru.practicum.model.StatGetModel(stat.app, stat.uri, count(DISTINCT stat.app))" +
            "FROM StatModel stat " +
            "WHERE stat.timestamp >= :start AND stat.timestamp <= :end " +
            "GROUP BY stat.app, stat.uri, stat.ip " +
            "ORDER BY count(stat.app) DESC")
    List<StatGetModel> getStatWithUniqIpWithoutURI(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.model.StatGetModel(stat.app, stat.uri, count(stat.app))" +
            "FROM StatModel stat " +
            "WHERE stat.timestamp >= :start AND stat.timestamp <= :end " +
            "GROUP BY stat.app, stat.uri, stat.ip " +
            "ORDER BY count(stat.app) DESC")
    List<StatGetModel> getStatWithNotUniqIpWithoutURI(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.model.StatGetModel(stat.app, stat.uri, count(DISTINCT stat.app))" +
            "FROM StatModel stat " +
            "WHERE stat.timestamp >= :start AND stat.timestamp <= :end " +
            "AND stat.uri in :uriList " +
            "GROUP BY stat.app, stat.uri,stat.ip " +
            "ORDER BY count(stat.app) DESC")
    List<StatGetModel> getStatWithUniqIpWithURI(@Param("start") LocalDateTime start,
                                                @Param("end") LocalDateTime end,
                                                @Param("uriList") List<String> uriList);

    @Query("SELECT new ru.practicum.model.StatGetModel(stat.app, stat.uri, count(stat.app))" +
            "FROM StatModel stat " +
            "WHERE stat.timestamp >= :start AND stat.timestamp <= :end " +
            "AND stat.uri in :uriList " +
            "GROUP BY stat.app, stat.uri, stat.ip " +
            "ORDER BY count(stat.app) DESC")
    List<StatGetModel> getStatWithoutUniqIpWithURI(@Param("start") LocalDateTime start,
                                                   @Param("end") LocalDateTime end,
                                                   @Param("uriList") List<String> uriList);
}
