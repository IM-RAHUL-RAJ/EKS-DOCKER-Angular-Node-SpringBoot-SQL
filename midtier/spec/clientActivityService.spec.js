const request = require('supertest');
const express = require('express');
const router = require('../src/controllers/clientActivityReportController'); // Adjust the path as necessary
const ClientActivityReportService = require('../src/services/clientActivityReportService');

const app = express();
app.use('/clientActivityReport', router);

describe('Client Activity Report API', () => {
    let server;

    beforeAll((done) => {
        server = app.listen(done);
    });

    afterAll((done) => {
        server.close(done);
    });

    beforeEach(() => {
        spyOn(ClientActivityReportService.prototype, 'getClientActivityReports').and.callThrough();
    });

    it('should return 200 and the reports for a valid client ID', (done) => {
        const mockReports = [
            {
                title: "Monthly Performance Overview for C001",
                summary: "A detailed summary of the client's portfolio performance over the past month, including key metrics such as total returns, top-performing assets, and areas for improvement.",
                clientId: "C001"
            },
            {
                title: "Quarterly Investment Insights for C001",
                summary: "An in-depth analysis of the client's investment activities for the quarter, highlighting significant market trends, portfolio adjustments, and strategic recommendations for the next quarter.",
                clientId: "C001"
            },
            {
                title: "Annual Financial Health Check for C001",
                summary: "A comprehensive review of the client's financial health over the past year, covering portfolio performance, risk assessment, and personalized advice for achieving long-term financial goals.",
                clientId: "C001"
            }
        ];
        ClientActivityReportService.prototype.getClientActivityReports.and.returnValue(Promise.resolve(mockReports));

        request(app)
            .get('/clientActivityReport/C001')
            .expect(200)
            .expect((res) => {
                expect(res.body).toEqual(mockReports);
            })
            .end(done);
    });

    it('should return 204 if no reports are found', (done) => {
        ClientActivityReportService.prototype.getClientActivityReports.and.returnValue(Promise.resolve([]));

        request(app)
            .get('/clientActivityReport/C001')
            .expect(204)
            .end(done);
    });

    it('should return 400 for an invalid client ID', (done) => {
        const error = { response: { status: 400 } };
        ClientActivityReportService.prototype.getClientActivityReports.and.returnValue(Promise.reject(error));

        request(app)
            .get('/clientActivityReport/invalid-id')
            .expect(400)
            .end(done);
    });

    it('should return 500 for a server error', (done) => {
        const error = new Error('Server error');
        ClientActivityReportService.prototype.getClientActivityReports.and.returnValue(Promise.reject(error));

        request(app)
            .get('/clientActivityReport/C001')
            .expect(500)
            .end(done);
    });
});
