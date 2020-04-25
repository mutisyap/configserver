import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRudishaConfig } from 'app/shared/model/rudisha-config.model';
import { RudishaConfigService } from './rudisha-config.service';

@Component({
  templateUrl: './rudisha-config-delete-dialog.component.html'
})
export class RudishaConfigDeleteDialogComponent {
  rudishaConfig?: IRudishaConfig;

  constructor(
    protected rudishaConfigService: RudishaConfigService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rudishaConfigService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rudishaConfigListModification');
      this.activeModal.close();
    });
  }
}
