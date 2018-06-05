package com.bankofaffiliates.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailInput {
    private String from;
    private String to;
    private String text;
    private String subject;
}
