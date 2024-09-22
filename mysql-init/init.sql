-- User Types Table
CREATE TABLE UserTypes (
    UserTypeId INT PRIMARY KEY AUTO_INCREMENT,
    UserTypeDesc VARCHAR(100)
);


-- User Table
CREATE TABLE User (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50),
	lastName VARCHAR(50),
    Email VARCHAR(100),
	Salt VARCHAR(50),
    UserTypeId INT,
	FOREIGN KEY(UserTypeId) REFERENCES UserTypes(UserTypeId)
);


-- Organization Table
CREATE TABLE Organization (
    OrganizationID INT PRIMARY KEY AUTO_INCREMENT,
	UserID INT,
    OrganizationName VARCHAR(100),
    FOREIGN KEY(UserID) REFERENCES User(UserID),
	INDEX idx_Organization_UserID (UserID) 

);


-- Repository Table
CREATE TABLE Repository (
    RepositoryID INT PRIMARY KEY AUTO_INCREMENT,
    OrganizationID INT,
    RepositoryName VARCHAR(100),
    FOREIGN KEY (OrganizationID) REFERENCES Organization(OrganizationID),
	INDEX idx_Repository_OrganizationID (OrganizationID)
);

-- Branch Table
CREATE TABLE Branch (
    BranchID INT PRIMARY KEY AUTO_INCREMENT,
    RepositoryID INT,
    BranchName VARCHAR(100),
    FOREIGN KEY (RepositoryID) REFERENCES Repository(RepositoryID),
	INDEX idx_Branch_RepositoryID (RepositoryID)
);

-- Commit Table
CREATE TABLE Commit (
    CommitID INT PRIMARY KEY AUTO_INCREMENT,
    BranchID INT,
    CommitMessage TEXT,
    CommitDate DATETIME,
    FOREIGN KEY (BranchID) REFERENCES Branch(BranchID),
	INDEX idx_Commit_BranchID (BranchID)
);

CREATE TABLE ScanType (
    ScanTypeID INT PRIMARY KEY AUTO_INCREMENT,
    ScanTypeValue VARCHAR(50) NOT NULL,
	ProcessingTimeSeconds INT NOT NULL
);

-- Scan Table
CREATE TABLE Scan (
    ScanID INT PRIMARY KEY AUTO_INCREMENT,
    CommitID INT,
    ScanTypeID INT,
    Status ENUM('PENDING', 'RUNNING', 'COMPLETED', 'FAILED') NOT NULL,
    InitiatedByUserID INT NULL,
    Issues INT,
	CreatedAt DATETIME,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	IsValid BOOLEAN DEFAULT FALSE,
 
    -- Foreign Keys
     FOREIGN KEY (CommitID) REFERENCES Commit(CommitID),
     FOREIGN KEY (InitiatedByUserID) REFERENCES User(UserID),
     FOREIGN KEY (ScanTypeID) REFERENCES ScanType(ScanTypeID),

    -- Indexes
    INDEX idx_Scan_CommitID (CommitID), 
    INDEX idx_Scan_InitiatedByUserID (InitiatedByUserID),
    INDEX idx_status (Status)


);

-- Insert data into UserTypes
INSERT INTO UserTypes (UserTypeDesc) VALUES ('Regular'), ('Gold');

INSERT INTO User (FirstName, LastName, Email, Salt, UserTypeId) VALUES
('Dunald', 'Trump', 'dunald@whitehouse.gov', 'somesalt1', 1),
('Kamala', 'Harris', 'Kamala@whitehouse.gov', 'somesalt2', 2),
('Miriam', 'Nussbacher', 'miriamnussbacher@gmail.com', 'somesalt3', 2);


INSERT INTO Organization (UserID, OrganizationName) VALUES
(1, 'Alpha Corp'),
(2, 'Beta LLC');

INSERT INTO Repository (OrganizationID, RepositoryName) VALUES
(1, 'AlphaRepo1'),
(1, 'AlphaRepo2'),
(2, 'BetaRepo1');

INSERT INTO Branch (RepositoryID, BranchName) VALUES
(1, 'main'),
(1, 'develop'),
(2, 'main'),
(3, 'main');

-- Insert data into Commit
INSERT INTO `Commit` (BranchID, CommitMessage, CommitDate) VALUES
(1, 'Initial commit on main', NOW()),
(2, 'Initial commit on develop', NOW()),
(2, 'Feature added to develop', NOW()),
(3, 'Initial commit on main', NOW()),
(4, 'Initial commit on main', NOW());

-- insert data into ScanType

INSERT INTO ScanType (ScanTypeValue, ProcessingTimeSeconds) VALUES ('RENOVATE', 30);
INSERT INTO ScanType (ScanTypeValue, ProcessingTimeSeconds) VALUES ('SCA', 60);
INSERT INTO ScanType (ScanTypeValue, ProcessingTimeSeconds) VALUES ('SAST', 90);








/*
While MySQL stores ENUM values as integers, queries that involve these fields can sometimes be harder to optimize than traditional indexed fields in a lookup table. Large or complex queries might perform better with a foreign key and a lookup table structure.

*/