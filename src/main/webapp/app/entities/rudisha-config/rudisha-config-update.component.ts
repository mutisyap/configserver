import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRudishaConfig, RudishaConfig } from 'app/shared/model/rudisha-config.model';
import { RudishaConfigService } from './rudisha-config.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile/profile.service';

@Component({
  selector: 'jhi-rudisha-config-update',
  templateUrl: './rudisha-config-update.component.html'
})
export class RudishaConfigUpdateComponent implements OnInit {
  isSaving = false;
  profiles: IProfile[] = [];

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    value: [null, [Validators.required, Validators.maxLength(7500)]],
    digest: [],
    lastUpdatedOn: [],
    profileId: []
  });

  constructor(
    protected rudishaConfigService: RudishaConfigService,
    protected profileService: ProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rudishaConfig }) => {
      this.updateForm(rudishaConfig);

      this.profileService.query().subscribe((res: HttpResponse<IProfile[]>) => (this.profiles = res.body || []));
    });
  }

  updateForm(rudishaConfig: IRudishaConfig): void {
    this.editForm.patchValue({
      id: rudishaConfig.id,
      key: rudishaConfig.key,
      value: rudishaConfig.value,
      digest: rudishaConfig.digest,
      lastUpdatedOn: rudishaConfig.lastUpdatedOn,
      profileId: rudishaConfig.profileId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rudishaConfig = this.createFromForm();
    if (rudishaConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.rudishaConfigService.update(rudishaConfig));
    } else {
      this.subscribeToSaveResponse(this.rudishaConfigService.create(rudishaConfig));
    }
  }

  private createFromForm(): IRudishaConfig {
    return {
      ...new RudishaConfig(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      value: this.editForm.get(['value'])!.value,
      digest: this.editForm.get(['digest'])!.value,
      lastUpdatedOn: this.editForm.get(['lastUpdatedOn'])!.value,
      profileId: this.editForm.get(['profileId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRudishaConfig>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IProfile): any {
    return item.id;
  }
}
