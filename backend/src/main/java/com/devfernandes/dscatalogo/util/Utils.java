package com.devfernandes.dscatalogo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.devfernandes.dscatalogo.entities.Product;
import com.devfernandes.dscatalogo.projections.ProductProjection;

public class Utils {

	public static List<Product> replace(List<ProductProjection> ordered, List<Product> unordered) {

       Map<Long, Product> map= new HashMap<>();
       for( Product obj : unordered) {
    	   map.put(obj.getId(), obj);
       }
       List<Product> result = new ArrayList<>();
       for (ProductProjection obj : ordered) {
    	   result.add(map.get(obj.getId()));
       }
		return result;
	}

}
