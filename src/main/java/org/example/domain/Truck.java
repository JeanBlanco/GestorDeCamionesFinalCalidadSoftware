package org.example.domain;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Truck {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String plate;
    @Getter @Setter
    private String driver;
    @Getter @Setter
    private String status;

}
