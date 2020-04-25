export interface IProfile {
  id?: number;
  name?: string;
  description?: string;
  isDefault?: boolean;
  moduleName?: string;
  moduleId?: number;
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public isDefault?: boolean,
    public moduleName?: string,
    public moduleId?: number
  ) {
    this.isDefault = this.isDefault || false;
  }
}
