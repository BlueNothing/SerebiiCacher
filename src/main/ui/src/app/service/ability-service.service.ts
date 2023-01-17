import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AbilityServiceService {

  private abilityUrl: string = "";

  constructor(private http: HttpClient) {
  this.abilityUrl = 'http://localhost:8080/abilities';
  }
}
