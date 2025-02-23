package jamonodev.produits_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProduitsServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProduitsServicesApplication.class, args);
	}

}
