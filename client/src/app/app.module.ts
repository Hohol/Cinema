import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RegisterComponent} from './components/register/register.component';
import {LoginComponent} from './components/login/login.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from '@angular/forms';
import {MoviesComponent} from './components/movies/movies.component';
import {HttpClientModule} from '@angular/common/http';
import {SeancesComponent} from './components/seances/seances.component';
import {MatDatepickerModule, MatInputModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatMomentDateModule} from '@angular/material-moment-adapter';
import {BuyTicketsComponent} from './components/buy-tickets/buy-tickets.component';
import {UsersComponent} from './components/users/users.component';
import { UserStatsComponent } from './components/user-stats/user-stats.component';
import { SeanceStatsComponent } from './components/seance-stats/seance-stats.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    LoginComponent,
    RegisterComponent,
    MoviesComponent,
    MoviesComponent,
    SeancesComponent,
    BuyTicketsComponent,
    UsersComponent,
    UserStatsComponent,
    SeanceStatsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MatDatepickerModule,
    MatInputModule,
    MatMomentDateModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
