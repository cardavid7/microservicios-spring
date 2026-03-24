# Proyecto de Microservicios con Spring Boot y Spring Cloud

Este repositorio contiene un robusto ecosistema de microservicios basado en la arquitectura de Spring Cloud y Spring Boot. Su objetivo principal es demostrar un sistema distribuido estructurado y escalable, gestionando dominios de `Productos` e `Items`, con características avanzadas de enrutamiento, descubrimiento de servicios, balanceo de carga y tolerancia a fallos.

---

## 🚀 Stack Tecnológico Principal

- **Java:** 21
- **Spring Boot:** 4.0.3
- **Spring Cloud:** 2025.1.0
- **Gestor de Dependencias:** Maven
- **Base de Datos:** MySQL
- **Resiliencia:** Resilience4j (Circuit Breaker y TimeLimiter)

---

## 🏗️ Arquitectura del Proyecto y Microservicios

El sistema está compuesto por los siguientes componentes clave, configurados con puertos y parámetros específicos para operar en conjunto:

### 1. `ms-eurekaserver-core` (Service Registry)

- **Rol:** Servidor de Registro y Descubrimiento (Eureka Server)
- **Puerto de Ejecución:** `5001`
- **Versión:** `0.0.1-SNAPSHOT`
- **Descripción:** Actúa como el directorio central de la arquitectura. Todos los microservicios se registran aquí, permitiendo la resolución de nombres transparente (Location Transparency) sin depender de direcciones IP fijas (ej. enrutamiento a través del nombre `ms-product-core`).

### 2. `ms-gatewayserver-core` (API Gateway)

- **Rol:** Puerta de enlace API reactiva (Spring Cloud Gateway WebFlux)
- **Puerto de Ejecución:** `8090`
- **Versión:** `0.0.1-SNAPSHOT`
- **Descripción:** Es el punto de entrada principal para el ecosistema, enroutando las solicitudes de los clientes hacia los microservicios correspondientes.
- **Rutas configuradas:**
  - `http://localhost:8090/v1/api/product/**` → `ms-product-core` (Soporta GET, POST. Omitiendo DELETE).
  - `http://localhost:8090/v1/api/item/**` → `ms-item-core`
- **Filtros Adicionales (Items):** Inyección de cabeceras en respuesta (`X-Response: Blue`), peticiones (`X-Request: 1234578`) y parámetros de solicitud (`X-Parameter: xyz-123`).

### 3. `ms-product-core` (Microservicio de Productos)

- **Rol:** Lógica de Negocio y Persistencia (JPA/Hibernate)
- **Puerto de Ejecución:** `5010`
- **Base de Datos:** MySQL (Driver: `com.mysql.cj.jdbc.Driver`)
- **Cadena de conexión:** `jdbc:mysql://localhost/ms_product_core` (Usuario: `root`)
- **DDL Automático:** `create-drop` (Ideal para desarrollo, recrea los esquemas cada vez que se levanta)
- **Descripción:** Maneja las operaciones de la entidad "Product", proveyendo la API REST fundamental y exponiendo conexión a la base de datos relacional MySQL.

### 4. `ms-item-core` (Microservicio de Items)

- **Rol:** Servicio Consumidor y Tolerancia a Fallos
- **Puerto de Ejecución:** `5020`
- **Integraciones:** OpenFeign para clientes HTTP declarativos.
- **Resiliencia (Resilience4j):**
  - **Circuit Breaker:** Sliding window de 10 llamadas, con umbral de fallo del 50%. En estado semi-abierto permite 4 llamadas de prueba. Transición a abierto por lentitud > 1s o tasa alta (>50%).
  - **Time Limiter:** Timeout fijado a 3 segundos.
- **Descripción:** Microservicio enfocado en los "Items", que consume la API de `ms-product-core` de forma balanceada a través del registro en Eureka, todo protegido contra fallas en cascada mediante Resilience4j.

---

## ⚙️ Requisitos para Ejecutar el Proyecto

1. **Java Development Kit (JDK):** Versión recomedada (Java 21).
2. **Maven:** Para gestión de paquetes.
3. **IDE Recomendado:** Spring Tool Suite (STS), IntelliJ IDEA, o VS Code con Extension Pack de Java.
4. **Base de Datos:** Tener un servidor **MySQL** corriendo en puerto por defecto `3306`, con una base de datos creada llamada `ms_product_core` (credenciales `root` / sin contraseña por defecto, o actualice el archivo `ms-product-core/src/main/resources/application.properties`).

---

## 🏃 Orden de Ejecución Recomendado

Para garantizar que los servicios se comuniquen y registren de forma óptima:

1. Levantar **ms-eurekaserver-core** (Puerto 5001).
   - Validar en: `http://localhost:5001/`
2. Levantar el proveedor **ms-product-core** (Puerto 5010).
3. Levantar el consumidor **ms-item-core** (Puerto 5020).
4. Finalmente, levantar la puerta de enlace **ms-gatewayserver-core** (Puerto 8090).

Una vez todos estén corriendo, todas las peticiones deben hacerse interactuando a través del **Puerto 8090** (Gateway). Por ejemplo, consultar productos mediante `http://localhost:8090/v1/api/product/...`.
