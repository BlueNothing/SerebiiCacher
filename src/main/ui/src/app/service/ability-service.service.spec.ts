import { TestBed } from '@angular/core/testing';

import { AbilityServiceService } from './ability-service.service';

describe('AbilityServiceService', () => {
  let service: AbilityServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbilityServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
