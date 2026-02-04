package com.sistemagestionestudiantes.response;

import jakarta.persistence.Column;

public record StudentDetail(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String cell
) {
}
