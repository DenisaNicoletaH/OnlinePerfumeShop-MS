package com.onlineperfumeshop.productsservice.datalayer.Product;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class Perfume {


    private String scentType;

    private LocalDate dateProduced;


    @SuppressWarnings("unused")
    public Perfume() {
    }

    public Perfume(@NotNull String scentType, @NotNull LocalDate dateProduced) {

        Objects.requireNonNull(this.scentType = scentType);
        Objects.requireNonNull(this.dateProduced = dateProduced);

    }
        public @NotNull String getScentType(){return scentType;}

        public @NotNull LocalDate getDateProduced() {return dateProduced;}


}
