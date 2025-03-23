Places an order for all items in the customer's cart.

Calls Customer Service to fetch customer details.

Calls Cart Service to fetch cart items by customerId.

Calls Inventory Service to:

Check available stock for each cart item.

Deduct stock for each successfully ordered item.

Finalizes the order once stock is validated and deducted.
