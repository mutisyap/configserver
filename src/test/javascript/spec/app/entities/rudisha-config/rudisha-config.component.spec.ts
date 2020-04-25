import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConfigserverTestModule } from '../../../test.module';
import { RudishaConfigComponent } from 'app/entities/rudisha-config/rudisha-config.component';
import { RudishaConfigService } from 'app/entities/rudisha-config/rudisha-config.service';
import { RudishaConfig } from 'app/shared/model/rudisha-config.model';

describe('Component Tests', () => {
  describe('RudishaConfig Management Component', () => {
    let comp: RudishaConfigComponent;
    let fixture: ComponentFixture<RudishaConfigComponent>;
    let service: RudishaConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConfigserverTestModule],
        declarations: [RudishaConfigComponent]
      })
        .overrideTemplate(RudishaConfigComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RudishaConfigComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RudishaConfigService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RudishaConfig(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.rudishaConfigs && comp.rudishaConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
