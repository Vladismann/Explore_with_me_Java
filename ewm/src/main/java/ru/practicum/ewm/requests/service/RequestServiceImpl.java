package ru.practicum.ewm.requests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.CommonMethods;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repo.EventRepo;
import ru.practicum.ewm.exceptions.NotFoundException;
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
import static ru.practicum.ewm.requests.model.StateParticipation.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RequestServiceImpl implements RequestService {

    private final EventRepo eventRepo;
    private final UserRepo userRepo;
    private final RequestRepo requestRepo;

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        CommonMethods.checkObjectIsExists(eventId, eventRepo);
        Event event = eventRepo.getReferenceById(eventId);
        if (requestRepo.findByRequesterIdAndEventId(userId, eventId) != null) {
            throw new WrongConditionsException("Request already exists.");
        }
        if (event.getInitiator().getId() == userId) {
            throw new WrongConditionsException("Can't made request on your own event.");
        }
        if (!event.getState().equals(PUBLISHED)) {
            throw new WrongConditionsException("The event not published.");
        }
        if (event.getParticipantLimit() != 0) {
            if (event.getParticipantLimit() == requestRepo.countByEventIdAndStatus(eventId, CONFIRMED)) {
                throw new WrongConditionsException("Limit of Participant =" + event.getParticipantLimit());
            }
        }
        User user = userRepo.getReferenceById(userId);
        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
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
    public ParticipationRequestDto updateRequest(Long userId, Long requestId) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        CommonMethods.checkObjectIsExists(requestId, requestRepo);
        Request request = requestRepo.getReferenceById(requestId);
        if (request.getRequester().getId() != userId) {
            throw new NotFoundException(String.format("Object with id=%s was not found", requestId));
        }
        if (request.getStatus().equals(REJECTED) || request.getStatus().equals(CANCELED)) {
            throw new WrongConditionsException("Reject is not required");
        }
        request.setStatus(CANCELED);
        requestRepo.save(request);
        log.info("Отменен запрос на участие: {}", requestId);
        return RequestMapper.requestToParticipationRequestDto(request);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParticipationRequestDto> getAll(Long userId) {
        CommonMethods.checkObjectIsExists(userId, userRepo);
        List<Request> requests = requestRepo.findAllByRequesterId(userId);
        log.info("Получен список запросов пользователя с id={}", userId);
        return RequestMapper.requestToParticipationRequestDto(requests);
    }
}
