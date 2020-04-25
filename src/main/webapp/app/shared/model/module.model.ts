export interface IModule {
  id?: number;
  name?: string;
  description?: string;
  applicationName?: string;
  applicationId?: number;
}

export class Module implements IModule {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public applicationName?: string,
    public applicationId?: number
  ) {}
}
