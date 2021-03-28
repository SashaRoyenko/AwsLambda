package com.robosh.service;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.robosh.entity.Product;

import java.util.HashMap;
import java.util.List;

public class ProductService {

    private static final String PRODUCTS_TABLE_NAME = "products";

    private DynamoDBMapper mapper;

    public ProductService() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(PRODUCTS_TABLE_NAME))
                .build();
        DynamoDBAdapter dynamoDBAdapter = DynamoDBAdapter.getInstance();
        mapper = dynamoDBAdapter.createDbMapper(mapperConfig);
    }


    public Product save(Product product) {
        this.mapper.save(product);
        return product;
    }

    public List<Product> findAll() {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        return mapper.scan(Product.class, scanExp);
    }


    public Product findById(String id) {
        HashMap<String, AttributeValue> attributeValue = new HashMap<>();
        attributeValue.put(":v1", new AttributeValue().withS(id));

        DynamoDBQueryExpression<Product> queryExp = new DynamoDBQueryExpression<Product>()
                .withKeyConditionExpression("product_id = :v1")
                .withExpressionAttributeValues(attributeValue);

        PaginatedQueryList<Product> result = this.mapper.query(Product.class, queryExp);

        if (!result.isEmpty()) {
            return result.get(0);
        }

        return null;
    }

    public String delete(String id) {
        Product product = findById(id);
        if (product != null) {
            mapper.delete(product);
            return "Deleted!";
        }
        return "Not found!";
    }

    public Product update(Product product) {
        Product dbProduct = findById(product.getId());
        if (dbProduct != null) {
            DynamoDBMapperConfig dynamoDBMapperConfig = DynamoDBMapperConfig
                    .builder()
                    .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                    .build();
            mapper.save(product, dynamoDBMapperConfig);
            return product;
        }
        return null;
    }

}
