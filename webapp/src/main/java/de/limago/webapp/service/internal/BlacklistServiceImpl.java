package de.limago.webapp.service.internal;

import de.limago.webapp.service.BlacklistService;
import de.limago.webapp.service.model.Person;

import java.util.List;

public class BlacklistServiceImpl implements BlacklistService {

    private final List<String> antipathen = List.of("Attila","Peter","Paul","Mary");
    @Override
    public boolean isBlacklisted(Person person) {
        return antipathen.contains(person.getVorname());
    }
}
