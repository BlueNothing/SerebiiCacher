import { TestBed } from '@angular/core/testing';

import { CollisionServiceService } from './collision-service.service';

describe('CollisionServiceService', () => {
  let service: CollisionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollisionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
