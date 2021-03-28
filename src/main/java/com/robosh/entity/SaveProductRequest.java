package com.robosh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductRequest {
    private String id;

    private String name;

    private Float price;

    private String imageUrl;

}
