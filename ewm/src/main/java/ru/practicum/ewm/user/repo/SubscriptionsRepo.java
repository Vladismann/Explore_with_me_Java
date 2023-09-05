package ru.practicum.ewm.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.user.model.Subscription;

public interface SubscriptionsRepo extends JpaRepository<Subscription, Long> {

    Subscription findBySubscriberIdAndUserId(long subscriberId, long userId);
}
