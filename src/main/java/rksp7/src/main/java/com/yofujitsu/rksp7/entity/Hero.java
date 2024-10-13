package com.yofujitsu.rksp7.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Table("hero")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Hero {
    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("attribute")
    private String attribute;

    @Column("spells_count")
    private int spellsCount;

    @Column("is_in_cm_mode")
    private boolean isInCmMode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return Objects.equals(id, hero.id) &&
                Objects.equals(name, hero.name) &&
                Objects.equals(attribute, hero.attribute) &&
                Objects.equals(spellsCount, hero.spellsCount) &&
                Objects.equals(isInCmMode, hero.isInCmMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, attribute, spellsCount, isInCmMode);
    }

}
