package mx.com.oscaraparicio.estudiantes.dto.randomuser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * RandomUserResponse
 * =================
 * DTO para mapear SOLO lo que necesitamos del JSON de randomuser.me:
 *
 * results[0].name.first
 * results[0].name.last
 * results[0].email
 * results[0].phone
 * results[0].cell
 */
@Getter
@Setter
public class RandomUserResponse {

    /**
     * results es un arreglo en el JSON.
     * Aaqui es List<Result>.
     */
    private List<Result> results;

    /**
     * Result representa cada elemento dentro de results[]
     */
    @Getter
    @Setter
    public static class Result {

        /**
         * "name" es un objeto dentro de cada result
         */
        private Name name;

        private String email;
        private String phone;
        private String cell;
    }

    /**
     * Name representa el objeto "name"
     * con first y last.
     */
    @Getter
    @Setter
    public static class Name {
        private String first;
        private String last;
    }
}
