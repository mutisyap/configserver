export interface IApplication {
  id?: number;
  name?: string;
  description?: string;
}

export class Application implements IApplication {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
