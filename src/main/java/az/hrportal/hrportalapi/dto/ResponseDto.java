package az.hrportal.hrportalapi.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDto<T> {

    T data;
    int code;

    public static <T> ResponseDto<T> of(T data) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setData(data);
        return responseDto;
    }

    public static <T> ResponseDto<T> of(T data, int code) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setData(data);
        responseDto.setCode(code);
        return responseDto;
    }

}
