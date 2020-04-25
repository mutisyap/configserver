import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRudishaConfig, RudishaConfig } from 'app/shared/model/rudisha-config.model';
import { RudishaConfigService } from './rudisha-config.service';
import { RudishaConfigComponent } from './rudisha-config.component';
import { RudishaConfigDetailComponent } from './rudisha-config-detail.component';
import { RudishaConfigUpdateComponent } from './rudisha-config-update.component';

@Injectable({ providedIn: 'root' })
export class RudishaConfigResolve implements Resolve<IRudishaConfig> {
  constructor(private service: RudishaConfigService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRudishaConfig> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rudishaConfig: HttpResponse<RudishaConfig>) => {
          if (rudishaConfig.body) {
            return of(rudishaConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RudishaConfig());
  }
}

export const rudishaConfigRoute: Routes = [
  {
    path: '',
    component: RudishaConfigComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RudishaConfigs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RudishaConfigDetailComponent,
    resolve: {
      rudishaConfig: RudishaConfigResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RudishaConfigs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RudishaConfigUpdateComponent,
    resolve: {
      rudishaConfig: RudishaConfigResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RudishaConfigs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RudishaConfigUpdateComponent,
    resolve: {
      rudishaConfig: RudishaConfigResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RudishaConfigs'
    },
    canActivate: [UserRouteAccessService]
  }
];
