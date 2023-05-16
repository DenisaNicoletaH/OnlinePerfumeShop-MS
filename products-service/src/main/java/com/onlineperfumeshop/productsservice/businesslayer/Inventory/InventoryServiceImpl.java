package com.onlineperfumeshop.productsservice.businesslayer.Inventory;


import com.onlineperfumeshop.productsservice.Utils.Exceptions.ConflictProductException;
import com.onlineperfumeshop.productsservice.Utils.Exceptions.NotFoundException;
import com.onlineperfumeshop.productsservice.Utils.Exceptions.ProductInvalidInputException;
import com.onlineperfumeshop.productsservice.businesslayer.Discount.DiscountService;
import com.onlineperfumeshop.productsservice.datalayer.Discount.DiscountIdentifier;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.Inventory;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.InventoryIdentifier;
import com.onlineperfumeshop.productsservice.datalayer.Inventory.InventoryRepository;
import com.onlineperfumeshop.productsservice.datalayer.Product.Perfume;
import com.onlineperfumeshop.productsservice.datalayer.Product.Product;
import com.onlineperfumeshop.productsservice.datalayer.Product.ProductIdentifier;
import com.onlineperfumeshop.productsservice.datalayer.Product.ProductRepository;
import com.onlineperfumeshop.productsservice.datamapperlayer.Inventory.InventoryRequestMapper;
import com.onlineperfumeshop.productsservice.datamapperlayer.Inventory.InventoryResponseMapper;
import com.onlineperfumeshop.productsservice.datamapperlayer.Product.ProductDiscountResponseMapper;
import com.onlineperfumeshop.productsservice.datamapperlayer.Product.ProductInventoryResponseMapper;
import com.onlineperfumeshop.productsservice.datamapperlayer.Product.ProductRequestMapper;
import com.onlineperfumeshop.productsservice.datamapperlayer.Product.ProductResponseMapper;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.ProductDiscountResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.ProductInventoryResponseModel;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;

    private final DiscountService discountService;
    private final ProductResponseMapper productResponseMapper;
    private final ProductRequestMapper productRequestMapper;

    private final InventoryRepository inventoryRepository;

    private final InventoryResponseMapper inventoryResponseMapper;
    private final InventoryRequestMapper inventoryRequestMapper;

    private final ProductInventoryResponseMapper productInventoryResponseMapper;

    private final ProductDiscountResponseMapper productDiscountResponseMapper;

    public InventoryServiceImpl(ProductRepository productRepository, DiscountService discountService, ProductResponseMapper productResponseMapper, ProductRequestMapper productRequestMapper, InventoryRepository inventoryRepository, InventoryResponseMapper inventoryResponseMapper, InventoryRequestMapper inventoryRequestMapper, ProductInventoryResponseMapper productInventoryResponseMapper, ProductDiscountResponseMapper productDiscountResponseMapper) {
        this.productRepository = productRepository;
        this.discountService = discountService;
        this.productResponseMapper = productResponseMapper;
        this.productRequestMapper = productRequestMapper;
        this.inventoryRepository = inventoryRepository;
        this.inventoryResponseMapper = inventoryResponseMapper;
        this.inventoryRequestMapper = inventoryRequestMapper;
        this.productInventoryResponseMapper = productInventoryResponseMapper;
        this.productDiscountResponseMapper = productDiscountResponseMapper;
    }

    @Override
    public ProductResponseModel getProductByIdentifier(String productId) {
        Product product = productRepository.findByProductIdentifier_ProductId(productId);
        Inventory inventory = inventoryRepository.findByInventoryIdentifier_InventoryId(product.getInventoryIdentifier().getInventoryId());
        return productResponseMapper.entityToResponseModel((product));
    }

    @Override
    public List<ProductResponseModel> getProducts() {
        return productResponseMapper.entityListToResponseModelList(productRepository.findAll());
    }

    @Override
    public void deleteProducts(String productId) {
        Product existingProducts = productRepository.findByProductIdentifier_ProductId(productId);

        if (existingProducts == null) {
            throw new NotFoundException("No product was found with id : " + productId);
        }

        productRepository.delete(existingProducts);

    }

    @Override
    public ProductResponseModel updateProduct(ProductRequestModel productRequestModel, String productId) {

        Product product = productRequestMapper.entityToRequestModel(productRequestModel);
        product.setInventoryIdentifier(new InventoryIdentifier(productRequestModel.getInventoryId()));
        product.setDiscountIdentifier(new DiscountIdentifier(productRequestModel.getDiscountId()));
        Product existingProduct = productRepository.findByProductIdentifier_ProductId(productId);
        if (existingProduct == null) {
            throw new NotFoundException("No product was found with id : " + productId);
        }
        product.setId(existingProduct.getId());
        product.setProductIdentifier(existingProduct.getProductIdentifier());

        Perfume perfume = new Perfume(productRequestModel.getScentType(), productRequestModel.getDateProduced());
        product.setPerfume(perfume);

        product.setProductIdentifier(existingProduct.getProductIdentifier());
        product.setId(existingProduct.getId());

        Product updatedProduct = productRepository.save(product);
        try {
            return productResponseMapper.entityToResponseModel(updatedProduct);
        } catch (DataAccessException ex) {
            if (ex.getCause().toString().contains("ConstraintViolationException") || ex.getMessage().contains("constraint [name]")) {
                throw new ConflictProductException("Product already exists with name : " + productRequestModel.getName());
            } else throw new ProductInvalidInputException(("An unknown error has occurred"));
        }

    }


    @Override
    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel) {
        return inventoryResponseMapper.entityToResponseModel(inventoryRepository.save(inventoryRequestMapper.requestModelToEntity(inventoryRequestModel)));

    }

    @Override
    public InventoryResponseModel getInventoryByInventoryIdentifier(String inventoryId) {
        return inventoryResponseMapper.entityToResponseModel(inventoryRepository.findByInventoryIdentifier_InventoryId(inventoryId));

    }

    @Override
    public List<InventoryResponseModel> getInventories() {
        return inventoryResponseMapper.entityListToResponseModelList(inventoryRepository.findAll());

    }




    @Override
    public void deleteInventory(String inventoryId) {
        Inventory existingInventory = inventoryRepository.findByInventoryIdentifier_InventoryId(inventoryId);

        if (existingInventory == null) {
            throw new NotFoundException("No inventory was found with id : " + inventoryId);
        }

        inventoryRepository.delete(existingInventory);

    }


    @Override
    public ProductResponseModel addProductToInventory(ProductRequestModel productRequestModel, String inventoryId) {

        Inventory inventory = inventoryRepository.findByInventoryIdentifier_InventoryId(inventoryId);
        if (inventory == null) {
            throw new NotFoundException("No inventory was found with id : " + inventoryId);
        }

        ProductIdentifier productIdentifier = new ProductIdentifier();


        Perfume perfume = new Perfume(productRequestModel.getScentType(), productRequestModel.getDateProduced());

        Product product = productRequestMapper.entityToRequestModel(productRequestModel);

        product.setProductIdentifier(productIdentifier);
        product.setDiscountIdentifier(new DiscountIdentifier(productRequestModel.getDiscountId()));
        product.setInventoryIdentifier(new InventoryIdentifier(inventoryId));
        product.setPerfume(perfume);

        Product saved = productRepository.save(product);
        try {
            return productResponseMapper.entityToResponseModel(saved);
        } catch (DataAccessException ex) {
            if (ex.getCause().toString().contains("ConstraintViolationException") || ex.getMessage().contains("constraint [name]")) {
                throw new ConflictProductException("Product already exists with name : " + productRequestModel.getName());
            } else throw new ProductInvalidInputException(("An unknown error as occurred"));
        }
    }

    @Override
    public List<ProductResponseModel> getProductsByInventoryIdentifier_InventoryId(String inventoryId) {
        List<Product> products = productRepository.getProductsByInventoryIdentifier_InventoryId(inventoryId);
        return productResponseMapper.entityListToResponseModelList(products);

    }



    @Override
    public ProductDiscountResponseModel getProductByDiscountId(String discountId) {
        List<Product> products = productRepository.getProductByDiscountIdentifier_DiscountId(discountId);

        List<ProductInventoryResponseModel> productInventoryResponseModels = new ArrayList<>();
        for (Product product :
                products) {
            productInventoryResponseModels.add(productInventoryResponseMapper.entityToResponseModel(inventoryResponseMapper.entityToResponseModel(inventoryRepository.findByInventoryIdentifier_InventoryId(product.getInventoryIdentifier().getInventoryId())), productResponseMapper.entityToResponseModel(product)));
        }

        List<ProductInventoryResponseModel> listInvProducts = productInventoryResponseModels.stream().toList();
        return productDiscountResponseMapper.entitiesToResponseModel(discountService.getDiscountByIdentifier(discountId), listInvProducts);
    }


}











