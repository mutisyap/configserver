export interface IRudishaConfig {
  id?: number;
  key?: string;
  value?: string;
  digest?: string;
  lastUpdatedOn?: number;
  profileName?: string;
  profileId?: number;
}

export class RudishaConfig implements IRudishaConfig {
  constructor(
    public id?: number,
    public key?: string,
    public value?: string,
    public digest?: string,
    public lastUpdatedOn?: number,
    public profileName?: string,
    public profileId?: number
  ) {}
}
