package dto;

import lombok.Data;

import java.util.List;

@Data
public class RandomUserDTO {
    private List<Result> results;

    @Data
    public static class Result {
        private Name name;
        private String email;
        private String phone;
        private String cell;
    }

    @Data
    public static class Name {
        private String first;
        private String last;
    }
}
