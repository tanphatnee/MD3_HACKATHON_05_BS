package ra.service;

import ra.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductService implements IService<Product, String> {
    private List<Product> products;

    public ProductService() {
        products = new ArrayList<>();
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public Product findById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void delete(String productId) {
        Product product = findById(productId);
        if (product != null) {
            products.remove(product);
        }
    }

    public List<Product> sortByPriceDescending() {
        List<Product> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, Comparator.comparingDouble(Product::getProductPrice).reversed());
        return sortedProducts;
    }

    public List<Product> searchByName(String name) {
        List<Product> searchResults = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(name)) {
                searchResults.add(product);
            }
        }
        return searchResults;
    }

    public void updateProductInfo(String productId, String newProductName, double newProductPrice, String newDescription, int newStock) {
        Product product = findById(productId);
        if (product != null) {
            product.setProductName(newProductName);
            product.setProductPrice(newProductPrice);
            product.setDescription(newDescription);
            product.setStock(newStock);
        }
    }
}
