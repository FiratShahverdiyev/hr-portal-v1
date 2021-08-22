package az.hrportal.hrportalapi.mapper.employee;

import az.hrportal.hrportalapi.domain.employee.Address;
import az.hrportal.hrportalapi.dto.employee.request.EmployeeGeneralInfoRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "country" , source = "addressCountry")
    @Mapping(target = "city" , source = "addressCity")
    @Mapping(target = "district" , source = "addressDistrict")
    @Mapping(target = "village" , source = "addressVillage")
    @Mapping(target = "street" , source = "addressStreet")
    @Mapping(target = "block" , source = "addressBlock")
    @Mapping(target = "apartment" , source = "addressApartment")
    @Mapping(target = "home" , source = "addressHome")
    Address toAddress(EmployeeGeneralInfoRequestDto generalInfoRequestDto);
}
