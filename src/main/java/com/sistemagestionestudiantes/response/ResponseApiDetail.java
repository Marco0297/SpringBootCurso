package com.sistemagestionestudiantes.response;

import java.util.List;

public record ResponseApiDetail
        (String msg,
         List<StudentDetail> detailsList
        ) {

}
