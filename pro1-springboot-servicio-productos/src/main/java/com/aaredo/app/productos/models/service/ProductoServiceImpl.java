package com.aaredo.app.productos.models.service;

import com.aaredo.app.productos.models.entity.Producto;
import com.aaredo.app.productos.models.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductoServiceImpl implements IProductoService{

    @Autowired
    private IProductoRepository productoRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return StreamSupport.stream(productoRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}
