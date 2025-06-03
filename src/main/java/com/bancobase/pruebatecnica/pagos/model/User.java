package com.bancobase.pruebatecnica.pagos.model;

import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String account;
    private String name;
    private String status;
    private List<Payment> payments;

  
}