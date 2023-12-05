package com.danila.market.dto;

import com.danila.market.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private int id;
    private String username;
}
