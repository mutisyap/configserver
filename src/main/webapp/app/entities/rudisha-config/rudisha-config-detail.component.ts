import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRudishaConfig } from 'app/shared/model/rudisha-config.model';

@Component({
  selector: 'jhi-rudisha-config-detail',
  templateUrl: './rudisha-config-detail.component.html'
})
export class RudishaConfigDetailComponent implements OnInit {
  rudishaConfig: IRudishaConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rudishaConfig }) => (this.rudishaConfig = rudishaConfig));
  }

  previousState(): void {
    window.history.back();
  }
}
