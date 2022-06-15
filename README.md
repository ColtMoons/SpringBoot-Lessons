crear todas las carpetas

- controller
- domain
- resource
- service

en domain deben haber las carpetas 
- model
- repository
- service

## Proceso 
se comienza por la carpeta domain/model

@MappedSuperclass es para que no genere persistencia pero si que los hijos tengan los atributos que posee la clase abstracta

segundo paso es ir por las interfaces del servicio que se encontrara en domain/service para despues implementarlas en la carpeta services que se encuentra a la altura de la carpeta domain