package ru.practicum.ewm.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.user.model.Subscription;

import java.util.List;

public interface SubscriptionsRepo extends JpaRepository<Subscription, Long> {

    Subscription findBySubscriberIdAndUserId(long subscriberId, long userId);

    List<Subscription> findBySubscriberId(long subscriberId);

    @Query("SELECT s.user.id " +
            "FROM Subscription s " +
            "WHERE s.subscriber.id = :subscriberId")
    List<Long> findAllUserSubscriptions(@Param("subscriberId") long subscriberId);
}
