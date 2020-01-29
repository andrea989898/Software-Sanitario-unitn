# Healthcare 
Il sito "Healthcare" si propone di offrire un efficace sistema di gestione sanitaria utilizzabile da un Servizio Sanitario Nazionale. 
La finalità è quella di poter gestire le principali funzioni sanitarie di ogni individuo delle provincie, in particolare dal punto di vista
del paziente, del dottore generale, di uno specialista ed eventualmente il lato paziente anche per un dottore. Inoltre 
la supervisione del sistema è effettuabile da parte del servizio sanitario con speciali funzioni di visualizzazione complessiva.

# Analisi iniziale del software da realizzare
Nella fase iniziale della realizzazione sono state effettuate:
- Analisi dei requisiti tramite Use-Case Diagram che è possibile trovare nel percorso Analisi requisiti/Use case IPW.pdf
- Analisi del processo di business che è possibile trovare nel percorso Analisi requisiti/BPMN IPW.pdf
- Analisi dei possibili utenti che è possibile trovare nel percorso Analisi requisiti/Personas

## Funzionalità
Di seguito le funzionalità suddivise:

> Paziente

- Visualizzare le proprie informazioni personali
- Scegliere e/o cambiare un dottore
- Modificare la password del proprio profilo
- Cambiare foto profilo
- Visualizzare gli esami prescritti
- Visualizzare i farmaci prescritti
- Visualizzare e segnare come letti gli esami con report
- Visulizzare e scaricare i propri referti

> Dottore

- Visualizzare la scheda personale dei propri pazienti
- Prescrivere esami ai propri pazienti
- Prescrivere visite ai propri pazienti
- Prescrivere farmaci ai propri pazienti

> Medici Specialisti

- Visualizzare le visite eseguite
- Visualizzare le visite da eseguire
- Scrivere un referto relativo ad una visita assegnata
- Visualizzare gli esami eseguiti
- Visualizzare gli esami da eseguire
- Scrivere un referto relativo ad un esame assegnato

> Servizio Sanitario

- Assegnare ad una visita data, ora e esecutore
- Visualizzare le visite eseguite
- Visualizzare le visite da eseguire
- Scrivere un referto relativo ad una visita assegnata

## Costruito con

- [Java](https://www.java.com) - General purpose computer-programming language
- [Maven](https://maven.apache.org/) - Dependency Management
- [Git](https://git-scm.com) - Distributed version control system
- [Tomcat](https://tomcat.apache.org) - Web Server

## Java Dependencies

- [Java Servlet](https://www.oracle.com/technetwork/java/index-jsp-135475.html) - I servlet Java possono rispondere a molti tipi di richieste, implementano più comunemente contenitori Web per l'hosting di applicazioni Web su server Web e pertanto si qualificano come API Web servlet lato server
- [JSP](https://www.oracle.com/technetwork/java/index-jsp-138231.html) -  avaServer Pages, di solito indicato con la sigla JSP (letto anche talvolta come Java Scripting Preprocessor), è una tecnologia di programmazione Web in Java per lo sviluppo della logica di presentazione (tipicamente secondo il pattern MVC) di applicazioni Web, fornendo contenuti dinamici in formato HTML o XML. Si basa su un insieme di speciali tag, all'interno di una pagina HTML, con cui possono essere invocate funzioni predefinite sotto forma di codice Java (JSTL) e/o funzioni JavaScript.
- [JBCrypt](https://github.com/jeremyh/jBCrypt) - jBCrypt è un'implementazione Java ™ del codice di hashing della password Blowfish di OpenBSD
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/) - Il driver JDBC PostgreSQL (abbreviato in PgJDBC) consente ai programmi Java di connettersi a un database PostgreSQL utilizzando un codice Java standard indipendente dal database
- [REST API](https://restfulapi.net/) - Descrivono una serie di linee guida e di approcci che definiscono lo stile con cui i dati vengono trasmessi
- [FAST JSON](https://github.com/alibaba/fastjson) - Fastjson è una libreria Java che può essere utilizzata per convertire oggetti Java nella loro rappresentazione JSON. Può anche essere usato per convertire una stringa JSON in un oggetto Java equivalente
- [Google ZXing QR Code](https://www.callicoder.com/generate-qr-code-in-java-using-zxing/) - libreria per generare codici QR per la nostra applicazione
- [Apache PDFBox](https://pdfbox.apache.org/) - La libreria Apache PDFBox è uno strumento Java open source per lavorare con documenti PDF
- [Apache XSSF](https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFWorkbook.html) - La libreria Apache XSSF è uno strumento Java open source per lavorare con documenti XLS
- [Apache commons.io](https://commons.apache.org/proper/commons-io/javadocs/api-2.5/org/apache/commons/io/FilenameUtils.html) - La libreria Apache common.io è uno strumento Java open source per manipolare i file

## Librerie

- [cookieconsent](https://cookieconsent.osano.com/) - Cookie Consent è un plug-in JavaScript leggero per avvisare gli utenti dell'uso dei cookie sul tuo sito Web, progettato per aiutarti a rispettare rapidamente la normativa sui cookie dell'UE
- [jquery](https://jquery.com/) - Rende molto più semplici le operazioni di spostamento e manipolazione dei documenti HTML, la gestione degli eventi, l'animazione e Ajax con un'API di facile utilizzo che funziona su una moltitudine di browser
- [bootstrap](https://getbootstrap.com) Bootstrap è una raccolta di strumenti liberi per la creazione di siti e applicazioni per il Web.
- [JavaScript](https://developer.oracle.com/javascript/) - In informatica JavaScript è un linguaggio di scripting orientato agli oggetti e agli eventi, comunemente utilizzato nella programmazione Web lato client (recentemente esteso anche al lato server) per la creazione, in siti web e applicazioni web, di effetti dinamici interattivi tramite funzioni di script invocate da eventi innescati a loro volta in vari modi dall'utente sulla pagina web in uso.

## Versioning

Abbiamo usato [Git](https://git-scm.com) per il versioning.


## Sviluppatori
- Francesco Di Flumeri
- Andrea Bovo
- Francesco Pozzobon

