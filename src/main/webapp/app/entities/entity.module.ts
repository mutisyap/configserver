import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'application',
        loadChildren: () => import('./application/application.module').then(m => m.ConfigserverApplicationModule)
      },
      {
        path: 'module',
        loadChildren: () => import('./module/module.module').then(m => m.ConfigserverModuleModule)
      },
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.ConfigserverProfileModule)
      },
      {
        path: 'rudisha-config',
        loadChildren: () => import('./rudisha-config/rudisha-config.module').then(m => m.ConfigserverRudishaConfigModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ConfigserverEntityModule {}
