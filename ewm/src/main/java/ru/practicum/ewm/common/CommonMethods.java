package ru.practicum.ewm.common;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.exceptions.NotFoundException;

@UtilityClass
public class CommonMethods {

    public void checkObjectIsExists(long objectId, JpaRepository<?, Long> repo) {
        if (!repo.existsById(objectId)) {
            throw new NotFoundException(String.format("Object with id=%s was not found", objectId));
        }
    }
}
