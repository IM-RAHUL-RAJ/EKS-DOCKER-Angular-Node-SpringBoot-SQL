DROP TABLE ClientActivityReport;

CREATE TABLE ClientActivityReport (
    id INTEGER PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    summary VARCHAR2(255)
);

-- Insert records
INSERT INTO ClientActivityReport (id, title, summary) VALUES 
(1, 'Monthly Performance Overview for C001', 'A detailed summary of the client''s portfolio performance over the past month, including key metrics such as total returns, top-performing assets, and areas for improvement.');

INSERT INTO ClientActivityReport (id, title, summary) VALUES 
(2, 'Quarterly Investment Insights for C001', 'An in-depth analysis of the client''s investment activities for the quarter, highlighting significant market trends, portfolio adjustments, and strategic recommendations for the next quarter.');

INSERT INTO ClientActivityReport (id, title, summary) VALUES 
(3, 'Annual Financial Health Check for C001', 'A comprehensive review of the client''s financial health over the past year, covering portfolio performance, risk assessment, and personalized advice for achieving long-term financial goals.');

INSERT INTO ClientActivityReport (id, title, summary) VALUES 
(4, 'Customized Market Analysis Report for C001', 'A tailored report providing insights into market trends and forecasts relevant to the client''s investment strategy, including sector performance, economic indicators, and potential opportunities.');

INSERT INTO ClientActivityReport (id, title, summary) VALUES 
(5, 'Client-Specific Tax Optimization Report for C001', 'A report focused on tax-efficient investment strategies, detailing potential tax savings, year-end tax planning tips, and recommendations for optimizing the client''s tax situation.');

INSERT INTO ClientActivityReport (id, title, summary) VALUES 
(6, 'Sustainable Investment Report for C001', 'An analysis of the client''s investments in sustainable and socially responsible assets, including performance metrics, impact assessment, and suggestions for enhancing the sustainability of the portfolio.');

INSERT INTO ClientActivityReport (id, title, summary) VALUES 
(7, 'Risk Management Report for C001', 'A detailed evaluation of the client''s portfolio risk, including risk exposure analysis, stress testing results, and recommendations for mitigating potential risks.');

COMMIT;
