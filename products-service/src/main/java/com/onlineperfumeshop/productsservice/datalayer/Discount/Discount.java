package com.onlineperfumeshop.productsservice.datalayer.Discount;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "discounts")
@Data
public class Discount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer salePercent;// the sale percentage

    @Embedded
    private SalePrices salePrices;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Embedded
    private DiscountIdentifier discountIdentifier;



    public Discount(Integer salePercent,Double newPrices,SaleStatus saleStatus) {
        discountIdentifier = new DiscountIdentifier();
        this.salePercent = salePercent;
        this.saleStatus=saleStatus;
        this.salePrices=new SalePrices(newPrices);
    }
    public Discount() {
        this.discountIdentifier=new DiscountIdentifier();

    }



    public @NotNull Integer getSalePercent() {
        return salePercent;
    }


}
