import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConfigserverTestModule } from '../../../test.module';
import { RudishaConfigDetailComponent } from 'app/entities/rudisha-config/rudisha-config-detail.component';
import { RudishaConfig } from 'app/shared/model/rudisha-config.model';

describe('Component Tests', () => {
  describe('RudishaConfig Management Detail Component', () => {
    let comp: RudishaConfigDetailComponent;
    let fixture: ComponentFixture<RudishaConfigDetailComponent>;
    const route = ({ data: of({ rudishaConfig: new RudishaConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConfigserverTestModule],
        declarations: [RudishaConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RudishaConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RudishaConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rudishaConfig on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rudishaConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
