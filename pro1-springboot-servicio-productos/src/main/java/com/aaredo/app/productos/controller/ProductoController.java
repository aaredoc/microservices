package com.aaredo.app.productos.controller;

import com.aaredo.app.productos.models.entity.Producto;
import com.aaredo.app.productos.models.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    @Autowired
    private Environment environment;
    @Autowired
    private IProductoService productoService;

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping("/listar")
    public List<Producto>listar(){
        return productoService.findAll().stream().map(producto -> {
            producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) throws InterruptedException {
        if(id.equals(10L)){
            throw new IllegalStateException("Producto no encontrado");
        }
        if(id.equals(7L)){
            TimeUnit.SECONDS.sleep(5L);
        }
        Producto producto = productoService.findById(id);
        producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        return productoService.findById(id);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto){
        LOGGER.info("LOGGER DE PRODUCTOS !!");
        return productoService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id){
        Producto getProducto = productoService.findById(id);
        getProducto.setNombre(producto.getNombre());
        getProducto.setPrecio(producto.getPrecio());

        return productoService.save(getProducto);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        productoService.deleteById(id);
    }
}
