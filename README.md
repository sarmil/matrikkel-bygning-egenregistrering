# API for Bygning

Dette repoet har ansvar for ny bygningsdel som del av Eiendomsregister 2.0. I første omgang er det ønskelig at APIet
skal kunne ta i mot egenregistrert data om bygninger og bruksenheter fra Team Egenregistrering.

## Lokalt oppsett

Prosjektet er satt opp med IntelliJ på MacOS. Dersom noe ikke fungerer, med andre IDEer eller operativsystemer, gjerne
fyll ut README med informasjon om dette.

Prosjektet er bygd og kjørt med `temurin-21` JRE og IntelliJ default Kotlin SDK.


### TLDR
Vil du kjøre alt av applikasjon og database med Docker, gjør følgende:
```shell
$ export PORT=<PORT_NUMMER>          # Optional
$ export INTERNAL_PORT=<PORT_NUMMER> # Optional
$ ./gradlew build
$ docker compose build
$ docker compose up
```

### Kjøring av database

For å kjøre applikasjonen må du først starte en database. For lokal kjøring er det definert en postgres-database i
[docker-compose.yaml](docker-compose.yaml). Her blir det definert et volume under `~/apps/postgres` slik at
databasedata persisteres og overlever omstart av databaseserveren. Dersom man ønsker å fjerne lokale data kan man
fjerne denne katalogstrukturen.

Databasen kan startes med docker compose:

```sh
$ docker compose up db -d
```

Flagget `-d` gjør at containeren kjøres i detached modus og loggene fra containeren ikke skrives til stdout. Dersom du
ønsker det kan du droppe flagget.

### Kjøring av applikasjon

Når databasen kjører, kan du kjøre opp applikasjonen enten lokalt, eller som en Docker container. Før du gjør dette må
du bygge applikasjonen, dette kan gjøres med gradle:

```sh
$ ./gradlew build
```

Hvis du vil kjøre appen som en Docker container kan du kjøre:

```sh
$ docker compose build
$ docker compose up web -d
```

Ellers er det bare å kjøre opp applikasjonen som ønsket via IntelliJ eller kommandolinje. Ingen spesielle hensyn som er
nødvendig rundt miljøkonfigurasjon, det skal ha sane defaults.

#### Håndtering av portkonflikter

Applikasjonen er satt opp til å håndtere bruk av andre porter enn default 8080 og 8081. Hvis du ønsker å bruke dette kan
du opprette en fil ved navn `.env` på rot i prosjektet. Denne filen blir ignorert av git og sjekkes ikke inn. Sett så
følgende variabler:

```
PORT=<PORT_NUMMER>
INTERNAL_PORT=<PORT_NUMMER>
```

`PORT` brukes for selve applikasjonen, mens `INTERNAL_PORT` brukes for interne endepunkter som metrikker og
helsesjekker.

**NB**: `.env` for å sette disse variablene fungerer kun dersom du bruker Docker for å kjøre applikasjonen. Dersom du
ønsker å kjøre via IntelliJ eller liknende må du sette disse i runtime configurationen din.

### Integrasjon mot matrikkel APIet

Som standard brukes det en stub/mock mot matrikkel APIet når applikasjonen kjører lokalt.
For å endre til å gå mot et faktisk kjørende matrikkel, må propertien `matrikkel.useStub` settes til `false`
i [application-local.conf](./src/main/resources/application-local.conf)

I tillegg må følgende miljøvariabler være satt (f.eks. i Run Configurations i IntelliJ)

```
MATRIKKEL_USE_STUB=false    # Deaktiverer stub av matrikkel APIet lokalt
MATRIKKEL_BASE_URL          # Kan settes for å overstyre default miljø
MATRIKKEL_USERNAME
MATRIKKEL_PASSWORD
```

### Integrasjonstester

Prosjektet inneholder noen integrasjonstester som ligger under [src/integrationTest](src/integrationTest). Testene
bruker blant annet
testcontainers for å kjøre opp en database som bruke under testene.

Testene er definert med en egen task som kan kjøres slik:

```./gradlew integrationTest```

I tillegg blir task-en kjørt ved standard `./gradlew build`

#### Oppsett med Colima

Integrasjonstestene krever at man har Docker kjørende på maskinen, men skal i utgangspunktet fungere uten noe annet
oppsett, spesielt hvis man benytter Docker Desktop.

Men hvis man benytter Colima som container runtime er det nødvendig å sette opp noen ekstra miljøvariabler for å få
testene til å kjøre.  
Se testcontainers sin egen dokumentasjon [her](https://java.testcontainers.org/supported_docker_environment/)
