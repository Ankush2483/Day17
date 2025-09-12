package com.Ecommerce.Ecommerce.repositories;

import com.Ecommerce.Ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Long> {
}
