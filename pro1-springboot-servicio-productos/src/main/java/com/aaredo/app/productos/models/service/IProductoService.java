package com.aaredo.app.productos.models.service;

import com.aaredo.app.productos.models.entity.Producto;

import java.util.List;

public interface IProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);

    void deleteById(Long id);
}
