import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RudishaConfigService } from 'app/entities/rudisha-config/rudisha-config.service';
import { IRudishaConfig, RudishaConfig } from 'app/shared/model/rudisha-config.model';

describe('Service Tests', () => {
  describe('RudishaConfig Service', () => {
    let injector: TestBed;
    let service: RudishaConfigService;
    let httpMock: HttpTestingController;
    let elemDefault: IRudishaConfig;
    let expectedResult: IRudishaConfig | IRudishaConfig[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RudishaConfigService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new RudishaConfig(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RudishaConfig', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RudishaConfig()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RudishaConfig', () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            value: 'BBBBBB',
            digest: 'BBBBBB',
            lastUpdatedOn: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RudishaConfig', () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            value: 'BBBBBB',
            digest: 'BBBBBB',
            lastUpdatedOn: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RudishaConfig', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
