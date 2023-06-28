package ra.run;

import ra.config.InputMethod;
import ra.model.Catalog;
import ra.model.Product;
import ra.service.CatalogService;
import ra.service.ProductService;


import java.util.Scanner;

public class BookManagement {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CatalogService catalogService = new CatalogService();
    private static final ProductService productService = new ProductService();

    public static void main(String[] args) {
        int choice;
        do {
            displayMainMenu();
            choice = InputMethod.getInteger();
            switch (choice) {
                case 1:
                    catalogManagement();
                    break;
                case 2:
                    productManagement();
                    break;
                case 3:
                    System.out.println("Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại.");
                    break;
            }
        } while (choice != 3);
    }

    private static void displayMainMenu() {
        System.out.println("**************************BASIC-MENU**************************");
        System.out.println("1. Quản lí danh mục");
        System.out.println("2. Quản lí sản phẩm");
        System.out.println("3. Thoát");
    }

    private static void catalogManagement() {
        int choice;
        do {
            displayCatalogManagementMenu();
            choice = InputMethod.getInteger();
            switch (choice) {
                case 1:
                    addNewCatalog();
                    break;
                case 2:
                    displayAllCatalogs();
                    break;
                case 3:
                    updateCatalogName();
                    break;
                case 4:
                    deleteCatalog();
                    break;
                case 5:
                    System.out.println("Đang quay lại menu chính");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                    break;
            }
        } while (choice != 5);
    }

    private static void displayCatalogManagementMenu() {
        System.out.println("********************CATALOG-MANAGEMENT********************");
        System.out.println("1. Thêm danh mục mới");
        System.out.println("2. Hiển thị tất cả các danh mục");
        System.out.println("3. Cập nhật tên danh mục theo ID danh mục");
        System.out.println("4. Xóa danh mục theo ID danh mục (chỉ khi không có sản phẩm nào được liên kết)");
        System.out.println("5. Quay lại menu chính");
    }

    private static void addNewCatalog() {
        System.out.print("Nhập số lượng danh mục để thêm: ");
        int count = InputMethod.getInteger();
        for (int i = 0; i < count; i++) {
            System.out.println("Nhập thông tin cho danh mục " + (i + 1) + ":");
            System.out.print("ID danh mục: ");
            int catalogId = InputMethod.getInteger();
            System.out.print("Tên danh mục: ");
            String catalogName = scanner.nextLine();
            Catalog catalog = new Catalog(catalogId, catalogName);
            catalogService.save(catalog);
            System.out.println("\n" +
                    "Đã thêm danh mục thành công.");
        }
    }

    private static void displayAllCatalogs() {
        System.out.println("Tất cả danh mục: ");
        for (Catalog catalog : catalogService.getAll()) {
            System.out.println(catalog);
        }
    }

    private static void updateCatalogName() {
        System.out.print("Nhập ID danh mục để cập nhật tên của nó: ");
        int catalogId = InputMethod.getInteger();
        Catalog catalog = catalogService.findById(catalogId);
        if (catalog != null) {
            System.out.print("Nhập tên danh mục mới: ");
            String newCatalogName = scanner.nextLine();
            catalog.setCatalogName(newCatalogName);
            System.out.println("Cập nhật thành công");
        } else {
            System.out.println("Danh mục không tồn tại");
        }
    }

    private static void deleteCatalog() {
        System.out.print("Nhập ID danh mục cần xóa:  ");
        int catalogId = InputMethod.getInteger();
        Catalog catalog = catalogService.findById(catalogId);
        if (catalog != null) {
            if (isCatalogEmpty(catalog)) {
                catalogService.delete(catalogId);
                System.out.println("Xóa danh mục thành công. ");
            } else {
                System.out.println("Không thể xóa danh mục vì danh mục có các sản phẩm được liên kết.");
            }
        } else {
            System.out.println("Danh mục không tồn tại");
        }
    }

    private static boolean isCatalogEmpty(Catalog catalog) {
        for (Product product : productService.getAll()) {
            if (product.getCatalog().equals(catalog)) {
                return false;
            }
        }
        return true;
    }

    private static void productManagement() {
        int choice;
        do {
            displayProductManagementMenu();
            choice = InputMethod.getInteger();
            switch (choice) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    displayAllProducts();
                    break;
                case 3:
                    searchProductsByName();
                    break;
                case 4:
                    updateProductInfo();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    sortProductsByPriceDescending();
                    break;
                case 7:
                    System.out.println("Đang quay lại menu chính...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                    break;
            }
        } while (choice != 7);
    }

    private static void displayProductManagementMenu() {
        System.out.println("********************PRODUCT-MANAGEMENT********************");
        System.out.println("1. Thêm mới sản phẩm");
        System.out.println("2. Hiển thị tất cả sản phẩm");
        System.out.println("3. Tìm kiếm sản phẩm bằng tên");
        System.out.println("4. Cập nhật thông tin sản phẩm theo ID sản phẩm");
        System.out.println("5. Xóa sản phẩm theo ID sản phẩm");

        System.out.println("6. Sắp xếp sản phẩm theo giá (thứ tự giảm dần)");
        System.out.println("7. Quay lại menu chính");
    }

    private static void addNewProduct() {
        System.out.print(" Nhập số lượng sản phẩm cần thêm: ");
        int count = InputMethod.getInteger();
        for (int i = 0; i < count; i++) {
            System.out.println("Nhập thông tin cho sản phẩm " + (i + 1) + ":");
            System.out.print("ID sản phẩm: ");
            String productId = scanner.nextLine();
            System.out.print("Tên sản phẩm: ");
            String productName = scanner.nextLine();
            System.out.print("Giá sản phẩm: ");
            double productPrice = InputMethod.getDouble();
            System.out.print("Miêu tả: ");
            String description = scanner.nextLine();
            System.out.print("Số lượng: ");
            int stock = InputMethod.getInteger();
            System.out.print("ID danh mục: ");
            int catalogId = InputMethod.getInteger();
            Catalog catalog = catalogService.findById(catalogId);
            if (catalog != null) {
                Product product = new Product(productId, productName, productPrice, description, stock, catalog);
                productService.save(product);
                System.out.println("Đã thêm sản phẩm thành công.");
            } else {
                System.out.println("Không tìm thấy danh mục SP.");
            }
        }
    }

    private static void displayAllProducts() {
        System.out.println("Tất cả sản phẩm:");
        for (Product product : productService.getAll()) {
            System.out.println(product);
        }
    }

    private static void searchProductsByName() {
        System.out.print(" Nhập tên sản phẩm để tìm kiếm: ");
        String productName = scanner.nextLine();
        System.out.println("Kết quả tìm kiếm:");
        for (Product product : productService.searchByName(productName)) {
            System.out.println(product);
        }
    }

    private static void updateProductInfo() {
        System.out.print("Nhập ID sản phẩm để cập nhật thông tin: ");
        String productId = scanner.nextLine();
        Product product = productService.findById(productId);
        if (product != null) {
            System.out.print("Nhập tên sản phẩm mới: ");
            String newName = scanner.nextLine();
            System.out.print("Nhập giá sản phẩm mới: ");
            double newPrice = InputMethod.getDouble();
            System.out.print("Nhập mô tả mới: ");
            String newDescription = scanner.nextLine();
            System.out.print("Nhập số lượng : ");
            int newStock = InputMethod.getInteger();
            productService.updateProductInfo(productId, newName, newPrice, newDescription, newStock);
            System.out.println("Thông tin sản phẩm được cập nhật thành công.");
        } else {
            System.out.println("Sản phẩm không tồn tại");
        }
    }

    private static void deleteProduct() {
        System.out.print("Nhập ID sản phẩm để xóa: ");
        String productId = scanner.nextLine();
        Product product = productService.findById(productId);
        if (product != null) {
            productService.delete(productId);
            System.out.println("Đã xóa sản phẩm thành công.");
        } else {
            System.out.println("Không tìm thấy sản phẩm");
        }
    }

    private static void sortProductsByPriceDescending() {
        productService.sortByPriceDescending();
        System.out.println("Danh sách sản phẩm đã được sắp xếp theo giá (giảm dần):");
        displayAllProducts();
    }
}
