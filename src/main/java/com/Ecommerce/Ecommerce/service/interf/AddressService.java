package com.Ecommerce.Ecommerce.service.interf;


import com.Ecommerce.Ecommerce.DTOs.AddressDto;
import com.Ecommerce.Ecommerce.DTOs.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);

}
