package enpointHit.service;

import enpointHit.model.EndpointHitMapper;
import lombok.RequiredArgsConstructor;
import enpointHit.repo.EndpointHitRepo;
import org.springframework.http.ResponseEntity;
import EndpointHitDto.AddEndpointHitDto;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class EndpointHitService {

    private final EndpointHitRepo endpointHitRepo;

    public AddEndpointHitDto addEndpointHit(AddEndpointHitDto addEndpointHitDto, HttpServletRequest httpServletRequest) {
        addEndpointHitDto.setIp(httpServletRequest.getRemoteAddr());
        addEndpointHitDto.setUri(httpServletRequest.getRequestURI());
        return EndpointHitMapper.toAddEndpointHitDto(endpointHitRepo.save(EndpointHitMapper.toEndpointHit(addEndpointHitDto)));
    }
}
