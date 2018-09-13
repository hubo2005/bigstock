package com.bohu.bigstock.dao.repository;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface StockNameAndSymbolRepository<T,String extends Serializable> extends CrudRepository<T, String>{


}
