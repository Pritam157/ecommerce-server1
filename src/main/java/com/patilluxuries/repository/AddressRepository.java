package com.patilluxuries.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patilluxuries.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	
}
