import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRudishaConfig } from 'app/shared/model/rudisha-config.model';

type EntityResponseType = HttpResponse<IRudishaConfig>;
type EntityArrayResponseType = HttpResponse<IRudishaConfig[]>;

@Injectable({ providedIn: 'root' })
export class RudishaConfigService {
  public resourceUrl = SERVER_API_URL + 'api/rudisha-configs';

  constructor(protected http: HttpClient) {}

  create(rudishaConfig: IRudishaConfig): Observable<EntityResponseType> {
    return this.http.post<IRudishaConfig>(this.resourceUrl, rudishaConfig, { observe: 'response' });
  }

  update(rudishaConfig: IRudishaConfig): Observable<EntityResponseType> {
    return this.http.put<IRudishaConfig>(this.resourceUrl, rudishaConfig, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRudishaConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRudishaConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
