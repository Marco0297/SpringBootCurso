package com.alpha.examen.response;

import java.util.List;


public record ResponseApiRecord(
        String msg,
        List<UserDetails> detailsList
) {
}
