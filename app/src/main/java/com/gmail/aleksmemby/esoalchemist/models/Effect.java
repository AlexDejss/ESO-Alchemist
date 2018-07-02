package com.gmail.aleksmemby.esoalchemist.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aleksey Kapura
 *         29.06.2018.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Effect {

    private Integer id;

    private String name;

    private String type;
}
