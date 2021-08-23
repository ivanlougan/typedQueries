package pl.joboboss.typedQueries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.joboboss.typedQueries.Dao.ProductDao;
import pl.joboboss.typedQueries.model.Product;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TypedQueriesApplication {

	public static void main(String[] args) throws InterruptedException{
		ConfigurableApplicationContext ctx = SpringApplication.run(TypedQueriesApplication.class, args);

		List<Product> products = new ArrayList<>();
		products.add(new Product("Telewizor", "Samsung", 4500.0));
		products.add(new Product("Opiekacz", "Opiex", 120.0));
		products.add(new Product("Laptop", "Samsung", 3599.0));
		products.add(new Product("Gaming Laptop", "Samsung", 7599.0));
		products.add(new Product("Kino domowe", "Yamaha", 2600.0));
		products.add(new Product("Smartfon", "Sony", 2100.0));

		ProductDao productDao = ctx.getBean(ProductDao.class);
		products.forEach(productDao::save);

		System.out.println("All Products: ");
		List<Product> allProducts = productDao.getAll();
		allProducts.forEach(System.out::println);

		System.out.println("Products more expensive than 3000:");
		List<Product> expensiveProducts = productDao.customGet("SELECT p FROM Product p WHERE p.price > 3000");
		expensiveProducts.forEach(System.out::println);

		System.out.println("All products ordered by price:");
		List<Product> orderedProducts = productDao.customGet("SELECT p FROM Product p ORDER BY p.price ASC");
		orderedProducts.forEach(System.out::println);

		System.out.println("Expensive Samsung products:");
		List<Product> expensiveSamsungProducts = productDao.customGet("SELECT p FROM Product p WHERE p.name = 'Samsung' AND p.price > 4000");
		expensiveSamsungProducts.forEach(System.out::println);

		productDao.deleteAll();

		ctx.close();

	}

}
