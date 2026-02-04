
package com.personas.response;

import com.personas.model.EstudianteModel;

import java.util.List;

public record ResponseApiRecordDetails(String msg, List<EstudianteModel> details) {
}
