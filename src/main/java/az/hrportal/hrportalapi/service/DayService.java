package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.domain.Day;
import az.hrportal.hrportalapi.dto.DayRequestDto;
import az.hrportal.hrportalapi.dto.DayResponseDto;
import az.hrportal.hrportalapi.error.exception.InvalidTokenException;
import az.hrportal.hrportalapi.mapper.DayMapper;
import az.hrportal.hrportalapi.repository.DayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DayService {
    private final DayRepository dayRepository;
    private final DayMapper dayMapper;

    @Value("${security.day-init-key}")
    private String dayInitKey;

    @Transactional
    public void initializeWorkDaysOfYear(String key) {
        if (!key.equals(dayInitKey)) {
            throw new InvalidTokenException(key);
        }
        final int year = 2021;
        for (int i = 1; i <= LocalDate.MAX.getDayOfYear(); i++) {
            Day day = new Day();
            LocalDate date = LocalDate.ofYearDay(year, i);
            day.setDay(date);
            if (!date.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                day.setJobDay(true);
            }
            dayRepository.save(day);
        }
    }

    @Transactional
    public void update(Integer id, DayRequestDto requestDto) {
        Day day = dayRepository.getById(id);
        day.setTitle(requestDto.getText());
        day.setJobDay(requestDto.getJobDay());
        dayRepository.save(day);
    }

    public List<DayResponseDto> getAllByYearAndMonth(int year, int month) {
        List<Day> days = dayRepository.findAll();
        List<DayResponseDto> response = new ArrayList<>();
        for (Day day : days) {
            if (day.getDay().getYear() == year && day.getDay().getMonthValue() == month)
                response.add(dayMapper.toDayResponseDto(day));
        }
        return response;
    }
}
