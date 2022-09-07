package com.stdio.esm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor

public class EsmToken implements Serializable {
    private static final long serialVersionUID = 163733234593344866L;
    private final String username;
    private final String accesstoken;
    private final String refreshToken;
    private final Date issueDate;
    private final Date expireDate;
}
