package ru.practicum.ewm.event.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.dto.search.AdminSearchParameters;
import ru.practicum.ewm.event.dto.search.PublicSearchParameters;
import ru.practicum.ewm.event.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMATTER;
import static ru.practicum.ewm.event.model.EventState.PUBLISHED;

@Repository
@RequiredArgsConstructor
public class EventRepoSearch {

    private final EntityManager manager;

    public List<Event> findAllForAdmin(AdminSearchParameters parameters) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        Predicate criteria = builder.conjunction();
        if (parameters.getUsers() != null && !parameters.getUsers().isEmpty()) {
            criteria = builder.and(criteria, root.get("initiator").in(parameters.getUsers()));
        }
        if (parameters.getStates() != null && !parameters.getStates().isEmpty()) {
            criteria = builder.and(criteria, root.get("state").in(parameters.getStates()));
        }
        if (parameters.getCategories() != null && !parameters.getCategories().isEmpty()) {
            criteria = builder.and(criteria, root.get("category").in(parameters.getCategories()));
        }
        if (parameters.getRangeStart() != null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class),
                    LocalDateTime.parse(parameters.getRangeStart().format(DEFAULT_DATE_FORMATTER), DEFAULT_DATE_FORMATTER)));
        }
        if (parameters.getRangeEnd() != null) {
            criteria = builder.and(criteria, builder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class),
                    LocalDateTime.parse(parameters.getRangeEnd().format(DEFAULT_DATE_FORMATTER), DEFAULT_DATE_FORMATTER)));
        }
        query.select(root).where(criteria);
        return manager.createQuery(query).setFirstResult(parameters.getFrom()).setMaxResults(parameters.getSize()).getResultList();
    }

    public List<Event> findAllForPublic(PublicSearchParameters parameters) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        Predicate criteria = builder.conjunction();
        if (parameters.getText() != null && !parameters.getText().isBlank()) {
            criteria = builder.and(criteria, builder.or(builder.like(builder.lower(root.get("annotation")), "%" + parameters.getText().toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("description")), "%" + parameters.getText().toLowerCase() + "%")));
        }
        if (parameters.getCategories() != null && !parameters.getCategories().isEmpty()) {
            criteria = builder.and(criteria, root.get("category").in(parameters.getCategories()));
        }
        if (parameters.getPaid() != null) {
            criteria = builder.and(criteria, root.get("paid").in(parameters.getPaid()));
        }
        if (parameters.getRangeStart() != null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class),
                    LocalDateTime.parse(parameters.getRangeStart().format(DEFAULT_DATE_FORMATTER), DEFAULT_DATE_FORMATTER)));
        }
        if (parameters.getRangeEnd() != null) {
            criteria = builder.and(criteria, builder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class),
                    LocalDateTime.parse(parameters.getRangeEnd().format(DEFAULT_DATE_FORMATTER), DEFAULT_DATE_FORMATTER)));
        }
        if (parameters.getRangeStart() == null && parameters.getRangeEnd() == null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class),
                    LocalDateTime.parse(LocalDateTime.now().format(DEFAULT_DATE_FORMATTER), DEFAULT_DATE_FORMATTER)));
        }
        criteria = builder.and(criteria, root.get("state").in(PUBLISHED));
        query.select(root).where(criteria);
        return manager.createQuery(query).setFirstResult(parameters.getFrom()).setMaxResults(parameters.getSize()).getResultList();
    }
}
