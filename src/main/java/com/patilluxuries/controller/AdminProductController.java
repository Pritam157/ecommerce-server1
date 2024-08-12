package com.patilluxuries.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.model.Product;
import com.patilluxuries.request.CreateProductRequest;
import com.patilluxuries.responce.ApiResponse;
import com.patilluxuries.service.ProductService;


@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
		
		Product product=productService.createProduct(req);
		
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)throws ProductException{
		productService.deleteProduct(productId);
		
		ApiResponse res=new ApiResponse();
		res.setMessage("product delete successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProduct() {
		List<Product> products=productService.findAllProducts();
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> putMethodName(@PathVariable Long productId, @RequestBody Product req) throws ProductException{
		
		Product product=productService.updateProduct(productId, req);
		
		
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}
	
	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req){
		
		for(CreateProductRequest p : req) {
			System.out.println(req);
			productService.createProduct(p);
		}
		
		
		ApiResponse res=new ApiResponse("multiple product added",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.CREATED);
	}
	
	
	
}
