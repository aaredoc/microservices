package com.aaredo.app.productos.models.repository;

import com.aaredo.app.productos.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;
public interface IProductoRepository extends CrudRepository<Producto, Long> {
}
