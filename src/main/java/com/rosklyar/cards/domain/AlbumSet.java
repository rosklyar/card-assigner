package com.rosklyar.cards.domain;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * Created by rostyslavs on 11/21/2015.
 */
public class AlbumSet {

    public final long id;
    public final String name;
    public final Set<Card> cards;

    public AlbumSet(long id, String name, Set<Card> cards) {
        this.id = id;
        this.name = name;
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumSet albumSet = (AlbumSet) o;
        return id == albumSet.id &&
                Objects.equals(name, albumSet.name) &&
                Objects.equals(cards, albumSet.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cards);
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
