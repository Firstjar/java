import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Product {
    String name;
    int price;
    int stock;

    Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}

public class Minimarket {
    private static Map<String, Product> products = new HashMap<>();
    private static Map<String, Product> cart = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inisialisasi produk
        products.put("001", new Product("Susu", 10000, 10));
        products.put("002", new Product("Roti", 5000, 20));
        products.put("003", new Product("Telur", 20000, 15));
        products.put("004", new Product("Beras", 15000, 5));
        products.put("005", new Product("Gula", 8000, 30));

        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│              MINIMARKET                   │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.println("1. Lihat Produk");
            System.out.println("2. Tambah Produk ke Keranjang");
            System.out.println("3. Lihat Keranjang");
            System.out.println("4. Hapus Produk dari Keranjang");
            System.out.println("5. Pembayaran");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    addToCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    removeFromCart();
                    break;
                case 5:
                    checkout();
                    break;
                case 6:
                    System.out.println("Terima kasih telah berbelanja!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void displayProducts() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│            Daftar Produk                          │");
        System.out.println("└─────────────────────────────────────┘");
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            String code = entry.getKey();
            Product product = entry.getValue();
            System.out.printf("%s: %s - Rp%d (Stok: %d)%n", code, product.name, product.price, product.stock);
        }
        System.out.println("└─────────────────────────────────────┘");
    }

    private static void addToCart() {
        System.out.print("Masukkan kode produk: ");
        String productCode = scanner.nextLine();
        if (products.containsKey(productCode)) {
            System.out.print("Masukkan jumlah: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            if (quantity > 0 && quantity <= products.get(productCode).stock) {
                Product product = products.get(productCode);
                if (cart.containsKey(productCode)) {
                    cart.get(productCode).stock += quantity;
                } else {
                    cart.put(productCode, new Product(product.name, product.price, quantity));
                }
                product.stock -= quantity;
                System.out.printf("%d %s telah ditambahkan ke keranjang.%n", quantity, product.name);
            } else {
                System.out.printf("Stok tidak mencukupi. Stok tersedia: %d. Silakan coba lagi.%n", products.get(productCode).stock);
            }
        } else {
            System.out.println("Produk tidak ditemukan. Silakan masukkan kode produk yang valid.");
        }
    }

    private static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Keranjang Anda kosong.");
            return;
        }
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("    │            Keranjang Belanja       │");
        System.out.println("└─────────────────────────────────────┘");
        int total = 0;
        for (Product item : cart.values()) {
            int itemTotal = item.price * item.stock;
            total += itemTotal;
            System.out.printf("%s - Rp%d x %d = Rp%d%n", item.name, item.price, item.stock, itemTotal);
        }
        System.out.printf("Total: Rp%d%n", total);
        System.out.println("└─────────────────────────────────────┘");
    }

    private static void removeFromCart() {
        if (cart.isEmpty()) {
            System.out.println("Keranjang Anda kosong. Tidak ada yang bisa dihapus.");
            return;
        }
        viewCart();
        System.out.print("Masukkan nama produk yang ingin dihapus: ");
        String productName = scanner.nextLine();

        String productCodeToRemove = null;
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            if (entry.getValue().name.equalsIgnoreCase(productName)) {
                productCodeToRemove = entry.getKey();
                break;
            }
        }

        if (productCodeToRemove != null && cart.containsKey(productCodeToRemove)) {
            Product product = cart.get(productCodeToRemove);
            products.get(productCodeToRemove).stock += product.stock;
            cart.remove(productCodeToRemove);
            System.out.println("Produk telah dihapus dari keranjang.");
        } else {
            System.out.println("Produk tidak ditemukan di keranjang.");
        }
    }

    private static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Keranjang Anda kosong. Tidak ada yang bisa dibayar.");
            return;
        }
        int total = 0;
        for (Product item : cart.values()) {
            total += item.price * item.stock;
        }
        System.out.printf("Total yang harus dibayar: Rp%d%n", total);
        System.out.print("Masukkan jumlah uang: Rp");
        int payment = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character

        if (payment >= total) {
            int change = payment - total;
            System.out.printf("Pembayaran berhasil! Kembalian: Rp%d%n", change);
            cart.clear(); // Mengosongkan keranjang setelah pembayaran
        } else {
            System.out.println("Uang tidak cukup untuk melakukan pembayaran atau input tidak valid.");
        }
    }
}