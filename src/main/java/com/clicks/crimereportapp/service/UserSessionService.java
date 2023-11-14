package com.clicks.crimereportapp.service;

import com.clicks.crimereportapp.enums.PageType;
import com.clicks.crimereportapp.model.UserSession;
import com.clicks.crimereportapp.repository.UserSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    public void resetPageCount(String phone) {

        UserSession userSessionCrime = getByPhone(phone, PageType.CRIME);
        userSessionCrime.setLastPage(-1);
        userSessionCrime.setCrimeCode(-1);
        userSessionCrime.setCrimeSceneCode(-1);

        UserSession userSessionScene = getByPhone(phone, PageType.SCENE);
        userSessionScene.setLastPage(-1);
        userSessionScene.setCrimeSceneCode(-1);
        userSessionScene.setCrimeCode(-1);
    }

    public int getLastPage(String phone, PageType pageType) {
        UserSession userSession = getByPhone(phone, pageType);
        return userSession.getLastPage();
    }

    public void increasePageCount(String phone, PageType pageType) {
        UserSession userSession = getByPhone(phone, pageType);
        userSession.setLastPage(userSession.getLastPage() + 1);
    }

    public UserSession getByPhone(String phone, PageType pageType) {
        Optional<UserSession> byPhoneAndPageType = userSessionRepository.findByPhoneAndPageType(phone, pageType.name());
        return byPhoneAndPageType.orElseGet(() -> userSessionRepository.save(
                UserSession.builder()
                        .phone(phone)
                        .ended(true)
                        .crimeCode(-1)
                        .crimeSceneCode(-1)
                        .pageType(pageType.name())
                        .lastPage(-1)
                        .build()));
    }
}
