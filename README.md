# Courses Management

Questo è un progetto di tesi triennale di Ingegneria Informatica.

## Avvio applicazione

Si tratta di un progetto Maven standard. Per avviarlo digitare  `mvnw` (Windows), o `./mvnw` (Mac & Linux), e aprire
la pagina http://localhost:8080 del proprio browser. È necessario avere preconfigurato una base di dati compatibile con 
lo script presente nella cartella `database` e aver modificato correttamente i file `applications.properties` in
`src/main/resources` e `pom.xml` per la corretta esecuzione.

## Distribuzione applicazione

Per creare una build è necessario usare il comando `mvnw clean package -Pproduction` (Windows)
oppure `./mvnw clean package -Pproduction` (Mac & Linux).
Questo comando creerà un file JAR contenente tutte le dipendenze e il frontend all'interno della cartella `target`.

Una volta finita la costruzione del file, si potrà avviare l'applicazione con il seguente comando:
`java -jar target/coursesmanagement-(versione).jar`

## Struttura progetto

- `MainLayout.java` in `src/main/java` contiene i metodi per la creazione della navbar e utilizza l'
- [App Layout](https://vaadin.com/components/vaadin-app-layout) di Vaadin come base.
- `views` in `src/main/java` contiene tutte le viste server-side scritte in Java.
- `data` in `src/main/java` contiene tutte le classi utili per interfacciarsi con la base di dati.
- `generated` in `src/main/java` contiene le classi generate da JOOQ.
- `security` in `src/main/java` contiene le classi per gestire l'accesso alle varie viste.
- `views` in `frontend/` contiene tutte le viste client-side scritte in Javascript.
- `themes` in `frontend/` contiene gli eventuali temi CSS personalizzati.


## Distribuzione con Docker compose

All'interno del progetto è già presente un file per avviare uno stack di container chiamato `docker-compose.yml`.
Per eseguirlo digitare sul terminale:

```
docker compose up
```

Una volta che i container saranno completamente inizializzati (ci potrebbero volere diversi minuti), l'applicazione sarà
pronta all'uso e con un set di dati precaricato. Per eseguire l'accesso usare admin@admin.

## Testing

Sono stati implementati pochi test e tutti di tipo E2E per testare la potenzialità del TestBench di Vaadin. Per usare
questa funzionalità è richiesta una [licenza](https://vaadin.com/pricing).
Per eseguire inserire nel terminale:

```
./mvnw verify -Pit -Dvaadin.proKey=xxxxxx-xxxxxx-xxxxxx-xxxxxx
```