package com.patilluxuries.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.model.Category;
import com.patilluxuries.model.Product;
import com.patilluxuries.repository.CategoryRepository;
import com.patilluxuries.repository.ProductRepository;
import com.patilluxuries.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService{
	
    @Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryRepository categoryRepository;
		

	@Override
	public Product createProduct(CreateProductRequest req) {
		
		System.out.println(req);
		
		Category topLevel=categoryRepository.findByName(req.getTopLavelCategory());
		
		
		
		if(topLevel==null) {
			Category topLevelCategory=new Category();
			topLevelCategory.setName(req.getTopLavelCategory());
//			topLevelCategory.setParentCategory(topLevel);
			topLevelCategory.setLevel(1);
			
			System.out.println(req.getTopLavelCategory());
			topLevel=categoryRepository.save(topLevelCategory);
		}
		
		Category secondLevel=categoryRepository.findByNameAndParant(req.getSecondLavelCategory(),topLevel.getName());
		
		if(secondLevel==null) {
			Category secondLavelCategory=new Category();
			secondLavelCategory.setName(req.getSecondLavelCategory());
			secondLavelCategory.setParentCategory(topLevel);
			secondLavelCategory.setLevel(2);
			
			System.out.println(req.getSecondLavelCategory());
			secondLevel=categoryRepository.save(secondLavelCategory);
		}
		
        Category thirdLevel=categoryRepository.findByNameAndParant(req.getThirdLavelCategory(),secondLevel.getName());
		
		if(thirdLevel==null) {
			Category thirdLavelCategory=new Category();
			thirdLavelCategory.setName(req.getThirdLavelCategory());
			thirdLavelCategory.setParentCategory(secondLevel);
			thirdLavelCategory.setLevel(3);
			
			System.out.println(req.getThirdLavelCategory());
			thirdLevel=categoryRepository.save(thirdLavelCategory);
		}
		
		Product product=new Product();
		
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPersent(req.getDiscountPersent());
		product.setimageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());
		
		System.out.println(product);
		Product savedProduct=productRepository.save(product);
		
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
	    
		Product product=findProductById(productId);
		
		product.getSizes().clear();
		productRepository.delete(product);
		
		return "Product deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		
		Product product=findProductById(productId);
		
		if(req.getQuantity()!=0) {
			product.setQuantity(req.getQuantity());
		}
		
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		
	   Optional<Product> opt=productRepository.findById(id);
	   
	   if(opt.isPresent()) {
		   return opt.get();
	   }
	   throw new ProductException("Product not found with id"+id);
	   
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		
		
		Pageable pageble=PageRequest.of(pageNumber, pageSize);
		
		List<Product> products=productRepository.filterProducts(category, minPrice, maxPrice, minDiscount,sort);
		
		if(!colors.isEmpty()) {
			products=products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}
		
		if(stock!=null) {
			if(stock.equals("in_stock")) {
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
				
			}else if(stock.equals("out_of_stock")){
				products=products.stream().filter(p->p.getQuantity()>1).collect(Collectors.toList());
				
			}
		}
		
		int startIndex=(int) pageble.getOffset();
		int endIndex=Math.min( startIndex + pageble.getPageSize(),products.size());
		
		List<Product> pageContent=products.subList(startIndex, endIndex);
		
		Page<Product> filteredProducts=new PageImpl<>(pageContent,pageble,products.size());
		
		
		return filteredProducts;
	}



	@Override
	public List<Product> findAllProducts() {
		
		List <Product> produtslist=productRepository.findAll();
		
		return produtslist;
	}
	
	

}
