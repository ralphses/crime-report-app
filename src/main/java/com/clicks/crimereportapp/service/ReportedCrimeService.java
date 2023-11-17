package com.clicks.crimereportapp.service;

import com.clicks.crimereportapp.dto.CrimeDto;
import com.clicks.crimereportapp.dto.requests.CrimeSceneDto;
import com.clicks.crimereportapp.dto.requests.NewCrimeReport;
import com.clicks.crimereportapp.dto.requests.ReportedCrimeDto;
import com.clicks.crimereportapp.dto.requests.UssdRequest;
import com.clicks.crimereportapp.enums.PageType;
import com.clicks.crimereportapp.exceptions.InvalidParamsException;
import com.clicks.crimereportapp.exceptions.ResourceNotFoundException;
import com.clicks.crimereportapp.model.Crime;
import com.clicks.crimereportapp.model.CrimeScene;
import com.clicks.crimereportapp.model.ReportedCrime;
import com.clicks.crimereportapp.repository.ReportedCrimeRepository;
import com.clicks.crimereportapp.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.clicks.crimereportapp.enums.PageType.CRIME;
import static com.clicks.crimereportapp.enums.PageType.SCENE;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportedCrimeService {

    private final ReportedCrimeRepository reportedCrimeRepository;
    private final UserSessionService userSessionService;
    private final CrimeService crimeService;
    private final CrimeSceneService crimeSceneService;
    private final DtoMapper mapper;

    public String handle(UssdRequest ussdRequest) {

        String text = ussdRequest.text();
        String phone = ussdRequest.phoneNumber();

        if (text.matches("")) {
            return welcomePage();
        }

        else if(text.startsWith("1")) {

            if(text.equals("1")) {
                userSessionService.increasePageCount(phone, SCENE);
                return selectPage(SCENE, phone);
            }
            else if(text.startsWith("1*00") && text.split("\\*").length > 2) {
                List<String> parts = getParts(text);
                if (parts.size() == 4) {
                    int sceneCode = Integer.parseInt(parts.get(2));
                    int crimeCode = Integer.parseInt(parts.get(3));

                    saveNewCrimeReport(crimeCode,sceneCode, phone);
                    return prepareResponse(responseBuilder(
                            "Crime reported", "Response on the way"), true);
                }
                else if (parts.size() == 3) {
                    userSessionService.increasePageCount(phone, CRIME);
                    return nextPage(CRIME, phone);
                }

            }
            else if(text.startsWith("1*")){

                List<String> parts = getParts(text);
                String sceneCode = parts.get(1);

                if(sceneCode.equals("00")) {
                    userSessionService.increasePageCount(phone, SCENE);
                    return nextPage(SCENE, phone);
                }
                else if(parts.size() == 3) {
                   try {
                       int crimeCode = Integer.parseInt(parts.get(2));
                       Optional<Crime> crimeOptional = crimeService.getCode(crimeCode);

                       if(crimeOptional.isPresent()) {
                           saveNewCrimeReport(crimeCode, Integer.parseInt(sceneCode), phone);
                           return prepareResponse(responseBuilder(
                                   "Crime reported", "Response on the way"), true);
                       }

                   }catch (NumberFormatException | ResourceNotFoundException exception) {
                       return prepareResponse("Invalid selection", true);
                   }
                }
                else {
                    userSessionService.increasePageCount(phone, CRIME);
                    return selectPage(CRIME, phone);
                }
            }
        }
        else if(text.startsWith("2")) {

            if(text.equals("2")) {
                userSessionService.increasePageCount(phone, SCENE);
                return selectPage(SCENE, phone);
            }
            else if(text.startsWith("2*00") && text.split("\\*").length > 2) {
                List<String> parts = getParts(text);

                String sceneCode = parts.get(2);
                Optional<CrimeScene> crimeSceneOptional = crimeSceneService.getCode(Integer.parseInt(sceneCode));

                if(crimeSceneOptional.isPresent()) {
                    CrimeScene crimeScene = crimeSceneOptional.get();

                    return prepareResponse(responseBuilder(
                            "SCENE DETAILS",
                            "USSD Code: ".concat(String.valueOf(crimeScene.getCode())),
                            "Room no: ".concat(crimeScene.getRoomNo()),
                            "Lodge name: ".concat(crimeScene.getLodgeName()),
                            "Area: ".concat(crimeScene.getArea())
                    ), true);
                }

            }
            else if(text.startsWith("2*")){

                List<String> parts = getParts(text);
                String sceneCode = parts.get(1);

                if(sceneCode.equals("00")) {
                    userSessionService.increasePageCount(phone, SCENE);
                    return nextPage(SCENE, phone);
                }
                else {
                   try {
                       Optional<CrimeScene> crimeSceneOptional = crimeSceneService.getCode(Integer.parseInt(sceneCode));

                       if(crimeSceneOptional.isPresent()) {
                           CrimeScene crimeScene = crimeSceneOptional.get();

                           return prepareResponse(responseBuilder(
                                   "SCENE DETAILS",
                                   "USSD Code: ".concat(String.valueOf(crimeScene.getCode())),
                                   "Room no: ".concat(crimeScene.getRoomNo()),
                                   "Lodge name: ".concat(crimeScene.getLodgeName()),
                                   "Area: ".concat(crimeScene.getArea())
                           ), true);
                       }

                   }catch (NumberFormatException | ResourceNotFoundException exception) {
                       return prepareResponse("Invalid selection", true);
                   }

                }


            }
        }
        else if(text.startsWith("3")) {

            if(text.equals("3")) {
                userSessionService.increasePageCount(phone, CRIME);
                return selectPage(CRIME, phone);
            }
            else if(text.startsWith("3*00") && text.split("\\*").length > 2) {
                List<String> parts = getParts(text);
                String crimeCode = parts.get(2);

                Optional<Crime> crimeOptional = crimeService.getCode(Integer.parseInt(crimeCode));
                if(crimeOptional.isPresent()) {
                    Crime crime = crimeOptional.get();
                    return prepareResponse(responseBuilder(
                            "CRIME DETAILS",
                            "USSD Code: ".concat(String.valueOf(crime.getCode())),
                            "Crime name: ".concat(crime.getName()),
                            "Description: ".concat(crime.getDescription())
                    ), true);
                }

            }
            else if(text.startsWith("3*")){

                List<String> parts = getParts(text);
                String sceneCode = parts.get(1);

                if(sceneCode.equals("00")) {
                    userSessionService.increasePageCount(phone, CRIME);
                    return nextPage(CRIME, phone);
                }
                else {
                    try {
                        Optional<Crime> crimeOptional = crimeService.getCode(Integer.parseInt(sceneCode));
                        if(crimeOptional.isPresent()) {
                            Crime crime = crimeOptional.get();
                            return prepareResponse(responseBuilder(
                                    "CRIME DETAILS",
                                    "USSD Code: ".concat(String.valueOf(crime.getCode())),
                                    "Crime name: ".concat(crime.getName()),
                                    "Description: ".concat(crime.getDescription())
                            ), true);
                        }

                    }catch (NumberFormatException | ResourceNotFoundException exception) {
                        return prepareResponse("Invalid selection", true);
                    }

                }


            }
        }
        return prepareResponse("Invalid selection", true);

    }

    private List<String> getParts(String text) {
        return Arrays.asList(text.split("\\*"));
    }

    private String nextPage(PageType pageType, String phone) {

        //Get the last page
        int lastPage = userSessionService.getLastPage(phone, pageType);
        return getPageTypeForUserPhone(pageType, lastPage, phone);
    }

    private String exit(String phone) {
        userSessionService.resetPageCount(phone);
        return prepareResponse("Goodbye and stay safe", true);
    }

    private String selectPage(PageType pageType, String phone) {
        return getPageTypeForUserPhone(pageType, 0, phone);
    }

    private String getPageTypeForUserPhone(PageType pageType, int page, String phone) {
        if (pageType.equals(CRIME)) {
            List<String> crimes = crimeService
                    .get(page)
                    .stream()
                    .map(this::crimeName)
                    .collect(toList());
            if(crimes.isEmpty()) {
                return prepareResponse(responseBuilder(
                        "Sorry, no crimes available again",
                        "contact management for more info"), true);
            }
            else {
                crimes.add(0, "Select Crime");
                if(crimes.size() == 9) {
                    crimes.add(9, "00. Next page");
                }
                else userSessionService.resetPageCount(phone);
                return prepareResponse(responseBuilder(crimes.toArray(String[]::new)), false);
            }

        } else {
            List<String> crimeScenes = crimeSceneService
                    .get(page)
                    .stream()
                    .map(this::crimeSceneName)
                    .collect(toList());

            if(crimeScenes.isEmpty()) {
                return prepareResponse(responseBuilder(
                        "Sorry, no crime scenes available again",
                        "contact management for more info"), true);
            }
            else {
                crimeScenes.add(0, "Select Crime Scene");

                if(crimeScenes.size() == 9) {
                    crimeScenes.add(9, "00. Next page");
                }
                else userSessionService.resetPageCount(phone);
                return prepareResponse(responseBuilder(crimeScenes.toArray(String[]::new)), false);

            }
        }
    }

    private String crimeName(CrimeDto crime) {
        return crime.code() + ". " + crime.name();
    }

    private String crimeSceneName(CrimeSceneDto crimeScene) {
        return crimeScene.code() + ". " + crimeScene.lodgeName() + ", Room " + crimeScene.roomNumber();
    }

    private String reportCrime(String crime, String scene, String phone) {

        try {
            int crimeCode = Integer.parseInt(crime);
            int sceneCode = Integer.parseInt(scene);

            saveNewCrimeReport(crimeCode, sceneCode, phone);
            userSessionService.resetPageCount(phone);

            return prepareResponse("Report lodged successfully!", true);

        } catch (NumberFormatException e) {
            userSessionService.resetPageCount(phone);
            return prepareResponse("Network error!", true);
        }
    }

    private void saveNewCrimeReport(int crimeCode, int sceneCode, String phone) {

        CrimeScene crimeScene = crimeSceneService.getByCode(sceneCode);
        Crime crime = crimeService.getByCode(crimeCode);

        reportedCrimeRepository.save(
                ReportedCrime.builder()
                        .crime(crime)
                        .scene(crimeScene)
                        .reporter(phone)
                        .createdAt(LocalDateTime.now())
                        .resolved(false)
                        .build());
    }

    private String welcomePage() {
        return prepareResponse(responseBuilder(
                "FULafia Crime Report System",
                "select option",
                "1. Report Crime",
                "2. Get scene code",
                "3. Get crime",
                "00. Exit"), false);
    }

    private String responseBuilder(String... inputs) {
        return String.join("\n", inputs);
    }

    private String prepareResponse(String message, boolean lastResponse) {
        return (lastResponse) ?
                "END ".concat(message) :
                "CON ".concat(message);
    }

    public List<ReportedCrimeDto> getAll(Integer page) {
        return reportedCrimeRepository
                .findAll(PageRequest.of(page, 15))
                .map(mapper::reportedCrimeDto)
                .toList();
    }

    public boolean resolve(long id) {
        reportedCrimeRepository.findById(id)
                .ifPresentOrElse(reportedCrime -> reportedCrime.setResolved(true),
                        () -> {
                            throw new ResourceNotFoundException("Reported Crime with ID " + id + " Not found");
                        });
        return true;
    }

    public boolean delete(long id) {
        reportedCrimeRepository.findById(id)
                .ifPresent(reportedCrimeRepository::delete);
        return true;
    }

    public boolean report(NewCrimeReport newCrimeReport) {
        try {
            Crime crime = crimeService.getByCode(Integer.parseInt(newCrimeReport.crimeType()));

            CrimeScene crimeScene = (newCrimeReport.crimeScene().equals("0")) ?
                    crimeSceneService.add(getCrimeSceneDto(newCrimeReport)) :
                    crimeSceneService.getByCode(Integer.parseInt(newCrimeReport.crimeScene()));

            reportedCrimeRepository.save(
                    ReportedCrime.builder()
                            .scene(crimeScene)
                            .crime(crime)
                            .reporter(newCrimeReport.reporter())
                            .createdAt(LocalDateTime.now())
                            .resolved(false)
                            .build());
            return true;
        } catch (NumberFormatException exception) {
            throw new InvalidParamsException("Invalid values passed");
        }

    }

    private CrimeSceneDto getCrimeSceneDto(NewCrimeReport newCrimeReport) {
        return new CrimeSceneDto(
                newCrimeReport.roomNumber(),
                newCrimeReport.lodgeName(),
                newCrimeReport.area(),
                0
        );
    }
}
