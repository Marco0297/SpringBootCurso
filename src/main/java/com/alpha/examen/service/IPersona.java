package com.alpha.examen.service;

import com.alpha.examen.response.ResponseApiRecord;
import org.springframework.http.ResponseEntity;

public interface IPersona {
    public ResponseEntity<ResponseApiRecord> createPerson(int cantidad);

}
