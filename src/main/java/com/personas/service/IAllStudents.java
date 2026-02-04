package com.personas.service;

import com.personas.response.ResponseApiRecordDetails;
import org.springframework.http.ResponseEntity;

public interface IAllStudents {
    public ResponseEntity<ResponseApiRecordDetails> getAllStudents();
}
