package ru.practicum.ewm.requests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.exceptions.WrongConditionsException;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.dto.RequestMapper;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.repo.RequestRepo;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.event.model.EventState.PUBLISHED;
import static ru.practicum.ewm.requests.model.StateParticipation.CONFIRMED;
import static ru.practicum.ewm.requests.model.StateParticipation.PENDING;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RequestServiceImpl implements RequestService {

    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final RequestRepo requestRepo;

    @Override
    public ParticipationRequestDto create(Long userId, Long eventId) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        if (requestRepo.findByRequestorIdAndEventId(userId, eventId) != null) {
            throw new WrongConditionsException("Request already exists.");
        }
        if (event.getInitiator().getId() == userId) {
            throw new WrongConditionsException("Can't made request on your own event.");
        }
        if (!event.getState().equals(PUBLISHED)) {
            throw new WrongConditionsException("The event not published.");
        }
        if (event.getParticipantLimit() == requestRepo.countByEventIdAndStatus(eventId, CONFIRMED)) {
            throw new WrongConditionsException("Limit of Participant =" + event.getParticipantLimit());
        }
        User user = userRepo.getReferenceById(userId);
        Request request = new Request();
        request.setRequestor(user);
        request.setEvent(event);
        if (!event.getRequestModeration()) {
            request.setStatus(CONFIRMED);
        } else {
            request.setStatus(PENDING);
        }
        request.setCreated(LocalDateTime.now());
        requestRepo.save(request);
        log.info("Создан запрос на участие: {}", request);
        return RequestMapper.requestToParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto update(Long userId, Long requestId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getAll(Long userId) {
        return null;
    }
}
