import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeanceStatsComponent } from './seance-stats.component';

describe('SeanceStatsComponent', () => {
  let component: SeanceStatsComponent;
  let fixture: ComponentFixture<SeanceStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeanceStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeanceStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
