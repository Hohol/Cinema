import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSeanceComponent } from './create-seance.component';

describe('CreateSeanceComponent', () => {
  let component: CreateSeanceComponent;
  let fixture: ComponentFixture<CreateSeanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSeanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSeanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
