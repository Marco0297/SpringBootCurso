package mx.com.oscaraparicio.estudiantes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
/**
 * ApiResponse
 * ===========
 * Esta clase representa el fomrato de respuesta de la API.
 **/

/**
 * @JsonInclude(JsonInclude.Include.NON_NULL)
 * =============
 * Si algun atributo es null, no lo incluyas en el JSON final
 **/

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String msg;
    private Object details;

    public ApiResponse() {
        //  permite crear el objeto vacio
    }

    public ApiResponse(String msg, Object details){
        this.msg = msg;
        this.details = details;
    }

}
