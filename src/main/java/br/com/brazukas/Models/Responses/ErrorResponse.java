package br.com.brazukas.Models.Responses;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private int _codigo;
    private String _message;
}
