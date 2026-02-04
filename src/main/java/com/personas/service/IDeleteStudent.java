package com.personas.service;

import com.personas.response.ResponseApiRecordDetails;
import org.springframework.http.ResponseEntity;

public interface IDeleteStudent {
    public ResponseEntity<ResponseApiRecordDetails> deleteStudent(Long id);
}
