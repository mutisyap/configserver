import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ConfigserverTestModule } from '../../../test.module';
import { RudishaConfigUpdateComponent } from 'app/entities/rudisha-config/rudisha-config-update.component';
import { RudishaConfigService } from 'app/entities/rudisha-config/rudisha-config.service';
import { RudishaConfig } from 'app/shared/model/rudisha-config.model';

describe('Component Tests', () => {
  describe('RudishaConfig Management Update Component', () => {
    let comp: RudishaConfigUpdateComponent;
    let fixture: ComponentFixture<RudishaConfigUpdateComponent>;
    let service: RudishaConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConfigserverTestModule],
        declarations: [RudishaConfigUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RudishaConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RudishaConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RudishaConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RudishaConfig(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RudishaConfig();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
