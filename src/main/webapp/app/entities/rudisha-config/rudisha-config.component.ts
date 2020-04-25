import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRudishaConfig } from 'app/shared/model/rudisha-config.model';
import { RudishaConfigService } from './rudisha-config.service';
import { RudishaConfigDeleteDialogComponent } from './rudisha-config-delete-dialog.component';

@Component({
  selector: 'jhi-rudisha-config',
  templateUrl: './rudisha-config.component.html'
})
export class RudishaConfigComponent implements OnInit, OnDestroy {
  rudishaConfigs?: IRudishaConfig[];
  eventSubscriber?: Subscription;

  constructor(
    protected rudishaConfigService: RudishaConfigService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.rudishaConfigService.query().subscribe((res: HttpResponse<IRudishaConfig[]>) => (this.rudishaConfigs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRudishaConfigs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRudishaConfig): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRudishaConfigs(): void {
    this.eventSubscriber = this.eventManager.subscribe('rudishaConfigListModification', () => this.loadAll());
  }

  delete(rudishaConfig: IRudishaConfig): void {
    const modalRef = this.modalService.open(RudishaConfigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rudishaConfig = rudishaConfig;
  }
}
