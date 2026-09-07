# DOPO-Proyecto-Inicial
Ciclo 1
1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

Crear la máquina y administrar ruedas (SlotMachine(), addWheel, delWheel) — se hizo primero porque todo lo demás depende de tener ruedas para operar sobre ellas.
Administrar símbolos (addSymbol, delSymbol) — se separó del mini-ciclo anterior porque es una responsabilidad distinta (afecta a todas las ruedas a la vez, no a una sola).
Girar y consultar (spin, symbols, distinctSymbols, configuration, isJackpot) — se agrupó porque estos métodos dependen de que ya existan ruedas y símbolos funcionando.
Representación visual (MachineDisplay, integración con el paquete shapes) — se dejó al final a propósito, para que la lógica del juego quedara probada de forma independiente antes de conectarla con la parte gráfica.
Visibilidad y cierre (makeVisible, makeInvisible, exit, ok()) — al final, porque depende de que ya exista MachineDisplay.

2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los miniciclos quedaron completados ya que cumplen con lo que nos pedía el proyecto.

4. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

Cada uno invirtio entre 12 y 14 horas.

4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

Ser capaces de empezar un proyecto con el uso de las diferentes herramientas necesarias para poder completarlo como es astah y el uso del lenguaje de JAVA ya que es nuestro primer contacto con este.

5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

La asociación de las clases del proyecto shapes para poder crear el proyecto, en gran parte con el requerimiento del uso de los colores.

6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Nos comprometemos a manejar mejor nuestros tiempos.

7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

La del segundo laboratorio ya que nos permitió entender como podemos realizar pruebas sin estar realizando prints innecesarios y sin tener que estar corrigiendo de manera manual todo el codigo.

8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.

https://docs.oracle.com/javase/8/docs/api/

Ciclo 2 — Refactoring y Extensión
1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

Fijar y soltar ruedas (lock, unlock, atributo locked en Wheel) — se hizo primero porque los demás mini-ciclos de este ciclo (intercambiar, rotar, fijar configuración) necesitan poder preguntar si una rueda está fija antes de actuar sobre ella.
Intercambiar ruedas (swap) — se apoya directamente en el mini-ciclo anterior (no se puede intercambiar una rueda fija).
Rotar una rueda un número de pasos, con animación (spin(wheel, steps), rotateOneStep en Wheel) — se separó como su propio mini-ciclo porque agrega un requisito de usabilidad nuevo (mostrar el giro paso a paso si el simulador está visible).
Dejar la máquina en una configuración dada (spin(String[])) — se dejó al final porque reutiliza la validación de "existe el símbolo" y el respeto a las ruedas fijas que ya se habían resuelto en los mini-ciclos anteriores.

2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los miniciclos están completados ya que realizamos lo que nos pedía el extendido del proyecto.

3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

cada uno invirtió al rededor de 10 horas

4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

Ser capaces de extender nuestro proyecto sin tener que empezarlo de 0 o sin romper algo de manera permanente

5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

La rotación de la rueda para que simulara una rueda real sin generar un indice negativo.
lo resolvimos buscando en internet como funcionaba ese tipo de algoritmo.

6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Nos comprometemos a manejar mejor nuestros tiempos.

7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

La del segundo laboratorio ya que nos permitió entender como podemos realizar pruebas sin estar realizando prints innecesarios y sin tener que estar corrigiendo de manera manual todo el codigo.

8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.

https://docs.oracle.com/javase/8/docs/api/
https://stackoverflow.com/questions/4412179/best-way-to-make-javas-modulus-behave-like-it-should-with-negative-numbers
