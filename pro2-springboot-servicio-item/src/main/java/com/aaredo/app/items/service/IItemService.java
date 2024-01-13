package com.aaredo.app.items.service;

import com.aaredo.app.items.models.Item;
import com.aaredo.app.items.models.Producto;

import java.util.List;

public interface IItemService {
    List<Item> findAll();
    Item findById(Long id, Integer cantidad);
    Producto save(Producto producto);

    Producto update(Producto producto, Long id);

    void delete(Long id);

}