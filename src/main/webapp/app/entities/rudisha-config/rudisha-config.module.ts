import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConfigserverSharedModule } from 'app/shared/shared.module';
import { RudishaConfigComponent } from './rudisha-config.component';
import { RudishaConfigDetailComponent } from './rudisha-config-detail.component';
import { RudishaConfigUpdateComponent } from './rudisha-config-update.component';
import { RudishaConfigDeleteDialogComponent } from './rudisha-config-delete-dialog.component';
import { rudishaConfigRoute } from './rudisha-config.route';

@NgModule({
  imports: [ConfigserverSharedModule, RouterModule.forChild(rudishaConfigRoute)],
  declarations: [RudishaConfigComponent, RudishaConfigDetailComponent, RudishaConfigUpdateComponent, RudishaConfigDeleteDialogComponent],
  entryComponents: [RudishaConfigDeleteDialogComponent]
})
export class ConfigserverRudishaConfigModule {}
