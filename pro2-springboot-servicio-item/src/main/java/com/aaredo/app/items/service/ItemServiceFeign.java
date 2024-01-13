package com.aaredo.app.items.service;

import com.aaredo.app.items.client.ProductoClienteRest;
import com.aaredo.app.items.models.Item;
import com.aaredo.app.items.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class ItemServiceFeign implements IItemService{

    @Autowired
    private ProductoClienteRest productoClienteRest;
    @Override
    public List<Item> findAll() {
        return productoClienteRest.listar().stream().map(prod -> new Item(prod, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(productoClienteRest.detalle(id), cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        return productoClienteRest.crear(producto);
    }

    @Override
    public Producto update(Producto producto, Long id) {
        return productoClienteRest.update(producto, id);
    }

    @Override
    public void delete(Long id) {
        productoClienteRest.eliminar(id);
    }
}
