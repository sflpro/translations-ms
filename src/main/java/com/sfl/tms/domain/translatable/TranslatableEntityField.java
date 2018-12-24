package com.sfl.tms.domain.translatable;

import com.sfl.tms.domain.AbstractEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Set;

/**
 * User: Vazgen Danielyan
 * Date: 12/4/18
 * Time: 5:21 PM
 */
@Entity
@Table(
        name = "translatable_entity_field",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tef_name_entity_id", columnNames = {"name", "entity_id"})
        }
)
@SequenceGenerator(name = "sequence_generator", sequenceName = "translatable_entity_field_seq", allocationSize = 1)
public class TranslatableEntityField extends AbstractEntity {

    private static final long serialVersionUID = -7215198975946459345L;

    //region Properties

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tef_entity_id"))
    private TranslatableEntity entity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "field")
    private Set<TranslatableEntityFieldTranslation> translations;

    //endregion

    //region Getters and setters

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public TranslatableEntity getEntity() {
        return entity;
    }

    public void setEntity(final TranslatableEntity entity) {
        this.entity = entity;
    }

    public Set<TranslatableEntityFieldTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(final Set<TranslatableEntityFieldTranslation> translations) {
        this.translations = translations;
    }

    //endregion

    //region Equals, Hashcode and toString

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        TranslatableEntityField rhs = (TranslatableEntityField) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.name, rhs.name)
                .append(getIdOrNull(this.entity), getIdOrNull(rhs.entity))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(name)
                .append(getIdOrNull(entity))
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", name)
                .append("entity", getIdOrNull(entity))
                .toString();
    }

    //endregion
}