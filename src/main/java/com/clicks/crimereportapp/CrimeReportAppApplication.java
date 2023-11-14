package com.clicks.crimereportapp;

import com.clicks.crimereportapp.model.AppUser;
import com.clicks.crimereportapp.model.Crime;
import com.clicks.crimereportapp.model.CrimeScene;
import com.clicks.crimereportapp.model.ReportedCrime;
import com.clicks.crimereportapp.repository.AppUserRepository;
import com.clicks.crimereportapp.repository.CrimeRepository;
import com.clicks.crimereportapp.repository.CrimeSceneRepository;
import com.clicks.crimereportapp.repository.ReportedCrimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class CrimeReportAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrimeReportAppApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            CrimeRepository crimeRepository,
            CrimeSceneRepository crimeSceneRepository,
            ReportedCrimeRepository reportedCrimeRepository,
            AppUserRepository userRepository
    ) {
        return args -> {

            Crime crime1 = crimeRepository.save(Crime.builder()
                    .name("Rape")
                            .code(crimeRepository.count() + 1)
                    .description("Rape crime on a female lady")
                    .build());

            Crime crime2 = crimeRepository.save(Crime.builder()
                    .name("Murder")
                    .code(crimeRepository.count() + 1)
                    .description("Student killed by a student")
                    .build());

            Crime crime3 = crimeRepository.save(Crime.builder()
                    .name("Assault")
                    .code(crimeRepository.count() + 1)
                    .description("An attack on a student")
                    .build());

            Crime crime4 = crimeRepository.save(Crime.builder()
                    .name("Assault 4")
                    .code(crimeRepository.count() + 1)
                    .description("An attack on a student 4")
                    .build());

            for(int i = 0; i < 20; i++ ) {

                crimeRepository.save(Crime.builder()
                        .name("Assault " + i)
                        .code(crimeRepository.count() + 1)
                        .description("An attack on a student " + i)
                        .build());
            }

            CrimeScene crimeScene1 = crimeSceneRepository.save(
                    CrimeScene.builder()
                            .lodgeName("Family and friends")
                            .roomNo("23")
                            .code(crimeSceneRepository.count() + 1)
                            .area("Gandu")
                            .build());

            CrimeScene crimeScene2 = crimeSceneRepository.save(
                    CrimeScene.builder()
                            .lodgeName("African Giant lodge")
                            .roomNo("23B")
                            .code(crimeSceneRepository.count() + 1)
                            .area("Akunza Kasa")
                            .build());

            for (int i = 0; i < 20; i++) {
                crimeSceneRepository.save(
                        CrimeScene.builder()
                                .lodgeName("African Giant lodge " + i)
                                .roomNo("23B"  + i)
                                .code(crimeSceneRepository.count() + 1)
                                .area("Akunza Kasa " + i)
                                .build());
            }

            reportedCrimeRepository.save(
                    ReportedCrime.builder()
                            .crime(crime1)
                            .reporter("+2347035002627")
                            .resolved(false)
                            .createdAt(LocalDateTime.now())
                            .scene(crimeScene1)
                            .build());

            reportedCrimeRepository.save(
                    ReportedCrime.builder()
                            .crime(crime2)
                            .reporter("+2347035006666")
                            .resolved(false)
                            .createdAt(LocalDateTime.now())
                            .scene(crimeScene2)
                            .build());

            reportedCrimeRepository.save(
                    ReportedCrime.builder()
                            .crime(crime2)
                            .reporter("+2347037263567")
                            .resolved(false)
                            .createdAt(LocalDateTime.now())
                            .scene(crimeScene2)
                            .build());

            reportedCrimeRepository.save(
                    ReportedCrime.builder()
                            .crime(crime3)
                            .reporter("+2347037263567")
                            .resolved(false)
                            .createdAt(LocalDateTime.now())
                            .scene(crimeScene2)
                            .build());

            userRepository.save(
                    AppUser.builder()
                            .name("Raphael Eze")
                            .username("+2347035002025")
                            .password("password")
                            .build()
            );
        };
    }
}
