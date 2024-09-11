package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            Factura factura1 = Factura.builder()
                    .fecha("09/12/19")
                    .numero(12)
                    .build();

            Domicilio dom = Domicilio.builder()
                    .nombre_calle("Vergara")
                    .numero(522)
                    .build();

            Cliente cliente = Cliente.builder()
                    .nombre("Facundo")
                    .apellido("Costella")
                    .dni(44438736)
                    .build();

            cliente.setDomicilio(dom);
            dom.setCliente(cliente);

            factura1.setCliente(cliente);

            Categoria perecederos = Categoria.builder().denominacion("perecederos").build();
            Categoria lacteos = Categoria.builder().denominacion("lacteos").build();
            Categoria limpieza = Categoria.builder().denominacion("limpieza").build();

            Articulo art1 = Articulo.builder().cantidad(200).denominacion("Yogurt Frutilla").precio(20).build();
            Articulo art2 = Articulo.builder().cantidad(80).denominacion("Detergente").precio(80).build();

            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);
            lacteos.getArticulos().add(art1);
            perecederos.getArticulos().add(art1);

            art2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(art2);

            DetalleFactura det1 = new DetalleFactura();
            det1.setArticulo(art1);
            det1.setCantidad(2);
            det1.setSubtotal(40);

            art1.getDetalle().add(det1);
            factura1.getDetalles().add(det1);
            det1.setFactura(factura1);

            DetalleFactura det2 = new DetalleFactura();
            det2.setArticulo(art2);
            det2.setCantidad(1);
            det2.setSubtotal(80);

            art2.getDetalle().add(det2);
            factura1.getDetalles().add(det2);
            det2.setFactura(factura1);

            factura1.setTotal(120);
            entityManager.persist(factura1);


            entityManager.flush();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }

        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}