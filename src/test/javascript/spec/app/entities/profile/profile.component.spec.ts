import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConfigserverTestModule } from '../../../test.module';
import { ProfileComponent } from 'app/entities/profile/profile.component';
import { ProfileService } from 'app/entities/profile/profile.service';
import { Profile } from 'app/shared/model/profile.model';

describe('Component Tests', () => {
  describe('Profile Management Component', () => {
    let comp: ProfileComponent;
    let fixture: ComponentFixture<ProfileComponent>;
    let service: ProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConfigserverTestModule],
        declarations: [ProfileComponent]
      })
        .overrideTemplate(ProfileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Profile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.profiles && comp.profiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
