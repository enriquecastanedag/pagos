# API de Pagos - Documentación de Endpoints


### 1. Crear un usuario
- **Método:** POST
- **Endpoint:** `/users`
- **Descripción:** Crea un nuevo usuario en el sistema.
- **Cuerpo de la solicitud (JSON):**
```json
{
  "account": "string",
  "name": "string",
  "status": "string"
}
```
- **Respuesta:** Objeto `User` creado.

---

### 2. Obtener todos los usuarios
- **Método:** GET
- **Endpoint:** `/users`
- **Descripción:** Devuelve la lista de todos los usuarios registrados.
- **Respuesta:** Lista de objetos `User`.

---

### 3. Crear un pago para un usuario
- **Método:** POST
- **Endpoint:** `/users/{account}/payments`
- **Descripción:** Agrega un nuevo pago a un usuario específico. El monto total se calcula automáticamente a partir de los productos y cantidades.
- **Cuerpo de la solicitud (JSON):**
```json
{
  "concepto": "string",
  "senderAccount": "string",
  "receiverAccount": "string",
  "status": "string",
  "products": [
    {
      "product": {
        "id": "string",
        "description": "string",
        "price": 0.0
      },
      "quantity": 0
    }
  ]
}
```
- **Respuesta:** Objeto `User` actualizado con el nuevo pago.

---

### 4. Actualizar el estado de un pago
- **Método:** PATCH
- **Endpoint:** `/users/{account}/payments/{paymentId}/status`
- **Descripción:** Actualiza solo el estado de un pago específico de un usuario.
- **Cuerpo de la solicitud (JSON):**
```json
"NUEVO_ESTADO"
```
- **Respuesta:** Objeto `User` actualizado.

---

### 5. Consultar el estado de un pago
- **Método:** GET
- **Endpoint:** `/users/{account}/payments/{paymentId}/status`
- **Descripción:** Devuelve el estado actual de un pago específico de un usuario.
- **Respuesta:**
```json
"ESTADO_DEL_PAGO"
```

---

## Notas
- Reemplaza `{account}` por el identificador de la cuenta del usuario y `{paymentId}` por el identificador del pago.
- Todos los endpoints devuelven respuestas en formato JSON.
- El campo `amount` del pago se calcula automáticamente a partir de los productos y cantidades enviados.
