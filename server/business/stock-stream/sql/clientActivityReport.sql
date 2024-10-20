CREATE TABLE ClientActivityReport (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    summary VARCHAR(255)
);

INSERT INTO ClientActivityReport (title, summary) VALUES 
('Monthly Report', 'Summary of client activities for the month.'),
('Quarterly Report', 'Detailed analysis of client activities for the quarter.'),
('Annual Report', 'Comprehensive overview of client activities for the year.');


Commit;
