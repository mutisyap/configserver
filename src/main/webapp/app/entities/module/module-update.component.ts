import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModule, Module } from 'app/shared/model/module.model';
import { ModuleService } from './module.service';
import { IApplication } from 'app/shared/model/application.model';
import { ApplicationService } from 'app/entities/application/application.service';

@Component({
  selector: 'jhi-module-update',
  templateUrl: './module-update.component.html'
})
export class ModuleUpdateComponent implements OnInit {
  isSaving = false;
  applications: IApplication[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    applicationId: []
  });

  constructor(
    protected moduleService: ModuleService,
    protected applicationService: ApplicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ module }) => {
      this.updateForm(module);

      this.applicationService.query().subscribe((res: HttpResponse<IApplication[]>) => (this.applications = res.body || []));
    });
  }

  updateForm(module: IModule): void {
    this.editForm.patchValue({
      id: module.id,
      name: module.name,
      description: module.description,
      applicationId: module.applicationId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const module = this.createFromForm();
    if (module.id !== undefined) {
      this.subscribeToSaveResponse(this.moduleService.update(module));
    } else {
      this.subscribeToSaveResponse(this.moduleService.create(module));
    }
  }

  private createFromForm(): IModule {
    return {
      ...new Module(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      applicationId: this.editForm.get(['applicationId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModule>>): void {
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

  trackById(index: number, item: IApplication): any {
    return item.id;
  }
}
