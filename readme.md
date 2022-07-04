# Examen Capitole Zara

En la base de datos de comercio electrónico de la compañía disponemos de la tabla PRICES que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas. A continuación se muestra un ejemplo de la tabla con los campos relevantes:

### PRICES

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|---------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14-00.00.00 | 2020-12-31-23.59.59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14-15.00.00 | 2020-06-14-18.30.00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15-00.00.00 | 2020-06-15-11.00.00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15-16.00.00 | 2020-12-31-23.59.59 | 4          | 35455      | 1        | 38.95 | EUR  |

#### Campos:

BRAND_ID: foreign key de la cadena del grupo (1 = ZARA).
START_DATE , END_DATE: rango de fechas en el que aplica el precio tarifa indicado.
PRICE_LIST: Identificador de la tarifa de precios aplicable.
PRODUCT_ID: Identificador código de producto.
PRIORITY: Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor prioridad (mayor valor numérico).
PRICE: precio final de venta.
CURR: iso de la moneda.

### Se pide:

Construir una aplicación/servicio en SpringBoot que provea una end point rest de consulta  tal que:

Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.

Se debe utilizar una base de datos en memoria (tipo h2) e inicializar con los datos del ejemplo, (se pueden cambiar el nombre de los campos y añadir otros nuevos si se quiere, elegir el tipo de dato que se considere adecuado para los mismos).

Desarrollar unos test al endpoint rest que  validen las siguientes peticiones al servicio con los datos del ejemplo:
 
#### Test 1 
```
petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
```
#### Test 2
```
petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
```
#### Test 3
```
petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
```
#### Test 4
```
petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
```
#### Test 5 
```
petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
```

#Resolucion

## Docker
Podes levantar la app sin configurar nada, solo teniendo docker instalado, corriendo:
```
    docker-compose up
```

## Diseño
Se decidió por un una arquitectura limpia, representada por una aplicacion Maven multimodular. 
La explicacion sobre cada modulo, puede verse en la imagen que acompaña esta seccion.

Para mas informacion sobre arquitectura limpia: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

![modules](https://github.com/hernandezed/capitole-zara-exam/blob/master/docs/modules.png)

## Implementacion de la capa de acceso a datos
Por limitacion respecto a las relaciones que tiene R2DBC, no pueden mapearse las relaciones. Aun asi, la tabla de brands se creo meramente para asegurar la necesidad de que exista el brand al cual se le crea una tarifa, por lo que no es completamente necesaria en este punto.

En caso de necesitarlo deberia de obtener ambas entidades por medio de su propio repositorio.

Para evitar la creacion de un metodo con un nombre excesivamente largo, y con dos parametros con el mismo valor, se utiliza @Query.

Es decir, se reemplaza:
```
    Mono<Price> findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(Long brandId, Long productId, LocalDateTime date, LocalDateTime date2);
```
Por: 
```
    @Query("select * from price  where brand_id = :brandId and product_id = :productId " +
            "and start_date <= :date and end_date >= :date order by priority desc fetch " +
            "first 1 rows only")
    Mono<Price> findApplicable(Long brandId, Long productId, LocalDateTime date);
```
Otra consideracion es que por limitaciones de R2DBC, no es posible utilizar JPQL/HQL en @Query

## Jacoco
Se cuentan con reportes de coverage de Jacoco. Los mismos excluyen clases que no tiene sentido aporten al coverage (Clases con solo setters y getters o configuracion). Se debe ejecutar
```
mvn clean verify
```
Los mismos se encuentran en /target/size/index.html
```
/target/size/index.html
```

## Swagger
La aplicación disponibiliza (mediante Spring docs) una vista para acceder a la documentación de swagger generada.

[Swagger](http://localhost:8080/swagger-ui.html)