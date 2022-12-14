package com.uce.p4.service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uce.p4.repository.IProductoRepo;
import com.uce.p4.repository.modelo.Producto;

@Service
public class ProductoServiceImpl implements IProductoService {

    private static Logger LOG = LogManager.getLogger(GestorServiceImpl.class);

    @Autowired
    private IProductoRepo productoRepo;

    @Override
    @Transactional(value = TxType.REQUIRED)
    public void ingresarProductos(Producto p) {
        try {
			Producto productoBuscado = this.productoRepo.buscarCodigo(p.getCodigoBarras());
			// En el caso de que exista actualizo
            if (productoBuscado.getCodigoBarras() != null) {
                Integer nuevoStock = p.getStock() + productoBuscado.getStock();
				productoBuscado.setStock(nuevoStock);
                this.productoRepo.actualizar(productoBuscado);

			}
		} catch (Exception e) {
			this.productoRepo.insertar(p);
		}

        
    }

    @Override
    public Producto consultarStock(String codigo) {
       return this.productoRepo.buscarCriteriaCodigo(codigo);
        
        
    }
    
}
