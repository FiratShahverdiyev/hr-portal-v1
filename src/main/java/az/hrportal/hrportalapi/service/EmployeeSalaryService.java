package az.hrportal.hrportalapi.service;

import az.hrportal.hrportalapi.dto.PaginationResponseDto;
import az.hrportal.hrportalapi.dto.employee.response.EmployeeSalaryResponseDto;
import az.hrportal.hrportalapi.mapper.employee.EmployeeSalaryMapper;
import az.hrportal.hrportalapi.repository.employee.EmployeeSalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeSalaryService {
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeeSalaryMapper employeeSalaryMapper;

    public PaginationResponseDto<List<EmployeeSalaryResponseDto>> getAll(int page, int size) {
        log.info("getAll service started");
        List<EmployeeSalaryResponseDto> data = employeeSalaryMapper
                .toEmployeeSalaryResponseDtos(employeeSalaryRepository.findAllByBackupIsFalse());
        PagedListHolder<EmployeeSalaryResponseDto> listHolder = new PagedListHolder<>(data);
        listHolder.setPage(page);
        listHolder.setPageSize(size);
        List<EmployeeSalaryResponseDto> response = listHolder.getPageList();
        log.info("********** getAll service completed **********");
        return new PaginationResponseDto<>(response, response.size(), data.size());
    }
}
