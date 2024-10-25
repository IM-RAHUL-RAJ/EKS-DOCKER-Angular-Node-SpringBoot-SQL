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
        const mockReports = [{ id: 1, activity: 'Test Activity' }];
        ClientActivityReportService.prototype.getClientActivityReports.and.returnValue(Promise.resolve(mockReports));

        request(app)
            .get('/clientActivityReport/12345')
            .expect(200)
            .expect((res) => {
                expect(res.body).toEqual(mockReports);
            })
            .end(done);
    });

    it('should return 204 if no reports are found', (done) => {
        ClientActivityReportService.prototype.getClientActivityReports.and.returnValue(Promise.resolve([]));

        request(app)
            .get('/clientActivityReport/12345')
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
            .get('/clientActivityReport/12345')
            .expect(500)
            .end(done);
    });
});
