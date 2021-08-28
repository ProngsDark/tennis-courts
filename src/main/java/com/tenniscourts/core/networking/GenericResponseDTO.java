package com.tenniscourts.core.networking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponseDTO<T> {
    private T data;

    private String message;
}
