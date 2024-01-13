package com.aaredo.app.items.controller;

import com.aaredo.app.items.models.Item;
import com.aaredo.app.items.models.Producto;
import com.aaredo.app.items.service.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ItemController {

    private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment env;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private IItemService itemService;

    @Value("${configuracion.texto}")
    private String texto;


    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre, @RequestHeader(name = "token-request", required = false) String token){
        System.out.println(nombre);
        System.out.println(token);
        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
        return circuitBreakerFactory.create("items")
                .run(() -> itemService.findById(id, cantidad),
                        alternative -> metodoAlternativo(id, cantidad, alternative)
                );
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo") //Agregando esta anotacion ya no se necesita crear una clase de configuracion, toma todo del arhcivo yml o properties
    @GetMapping("/ver2/{id}/cantidad/{cantidad}")
    public Item detallev2(@PathVariable Long id, @PathVariable Integer cantidad){
        return itemService.findById(id, cantidad);

    }

    //SE PUEDEN COMBINAR CON  @CIRCUITEBREAKER
    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
    @TimeLimiter(name = "items") //ASi se deberia usar un TimeLimeter (si se usa solo definimos el metodo alternativo)
    @GetMapping("/ver3/{id}/cantidad/{cantidad}")
    public CompletableFuture<Item> detallev3(@PathVariable Long id, @PathVariable Integer cantidad){
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, cantidad));

    }

    public Item metodoAlternativo(Long id, Integer cantidad, Throwable alternative){
        LOGGER.info(alternative.getMessage());
        Item item = new Item();
        Producto producto = new Producto();
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("G840");
        producto.setPrecio(180.00);
        item.setProducto(producto);
        return item;
    }

    public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable alternative){
        LOGGER.info(alternative.getMessage());
        Item item = new Item();
        Producto producto = new Producto();
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("G840");
        producto.setPrecio(180.00);
        item.setProducto(producto);
        return CompletableFuture.supplyAsync(() -> item);
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfiguracion(@Value("${server.port}") String puerto){
        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto", puerto);
        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0] .equals("dev")){
            json.put("autorName", env.getProperty("configuracion.autor.nombre"));
            json.put("autorEmail", env.getProperty("configuracion.autor.email"));
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto){
        return itemService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id){
        return itemService.update(producto, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        itemService.delete(id);
    }
}