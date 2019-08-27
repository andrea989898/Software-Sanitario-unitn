CREATE TABLE Pazienti(
        Nome CHAR(40) NOT NULL,
        Cognome CHAR(40) NOT NULL,
        Nascita DATE NOT NULL,
        LuogoNascita CHAR(40) NOT NULL,
        CodiceFiscale CHAR(16) PRIMARY KEY NOT NULL,
        Sesso CHAR(2) NOT NULL,
        FotoPaziente BINARY NOT NULL,
        MedicoBase CHAR(16),
        Email CHAR(80) NOT NULL,
        FOREIGN KEY(MedicoBase) REFERENCES MediciBase(CodiceFiscale)
);


CREATE TABLE Medici(
        Nome CHAR(40) NOT NULL,
        Cognome CHAR(40) NOT NULL,
        Nascita DATE NOT NULL,
        LuogoNascita CHAR(40) NOT NULL,
        CodiceFiscale CHAR(16) PRIMARY KEY NOT NULL,
        Email CHAR(80) NOT NULL
)


CREATE TABLE MediciBase(
        CodiceFiscale CHAR(16) PRIMARY KEY NOT NULL, 
        FOREIGN KEY(CodiceFiscale) REFERENCES Medici(CodiceFiscale)
);

CREATE TABLE MediciSpecialisti(
        Specializzazione CHAR(70),
        CodiceFiscale CHAR(16) PRIMARY KEY NOT NULL, 
        FOREIGN KEY(CodiceFiscale) REFERENCES Medici(CodiceFiscale)
);


CREATE TABLE Visite(
        IDVisita INT PRIMARY KEY NOT NULL,
        IDPaziente CHAR(16) NOT NULL,
        IDMedico CHAR(16)NOT NULL,
        Orario TIME,
        Luogo CHAR(20),
        IsDone BOOLEAN NOT NULL,
        IsSpecial BOOLEAN NOT NULL,
        DataVisita DATE,
        Argomento CHAR(100),
        FOREIGN KEY(IDMedico) REFERENCES Medici(CodiceFiscale),
        FOREIGN KEY(IDPaziente) REFERENCES Pazienti(CodiceFiscale)
);


CREATE TABLE Ticket(
        Codice INT PRIMARY KEY NOT NULL,
        IDVisita INT,
        FOREIGN KEY (IDVisita) REFERENCES Visite(IDVisita)
);

CREATE TABLE Esami(
        Codice INT PRIMARY KEY NOT NULL,
        IDPrescrizione INT,
        IDRicetta INT,
        IDVisita INT,
        Esito char(100),
        IsDone BOOLEAN NOT NULL,
        FOREIGN KEY (IDVisita) REFERENCES Visite(IDVisita),
        FOREIGN KEY (IDPrescrizione) REFERENCES Prescrizioni(Codice),
        FOREIGN KEY (IDRicetta) REFERENCES Ricette(Codice)
)

CREATE TABLE Prescrizioni(
        Codice INT PRIMARY KEY NOT NULL,
        IDMedicina INT,
        FOREIGN KEY (IDMedicina) REFERENCES Medicine(Codice)

)

CREATE TABLE Ricette(
        Codice INT PRIMARY KEY NOT NULL,
        IDMedicina INT,
        FOREIGN KEY (IDMedicina) REFERENCES Medicine(Codice)
)

CREATE TABLE Medicine(
        Codice INT PRIMARY KEY NOT NULL,
        IsForPrescrizione BOOLEAN NOT NULL,
        Nome char(100)
)