import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {LoginComponent} from './components/auth/login/login.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from '@angular/forms';
import {MoviesComponent} from './components/movie/movies/movies.component';
import {HttpClientModule} from '@angular/common/http';
import {SeancesComponent} from './components/seance/seances/seances.component';
import {MatDatepickerModule, MatInputModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatMomentDateModule} from '@angular/material-moment-adapter';
import {BuyTicketsComponent} from './components/seance/buy-tickets/buy-tickets.component';
import {UsersComponent} from './components/user/users/users.component';
import {UserStatsComponent} from './components/user/user-stats/user-stats.component';
import {SeanceStatsComponent} from './components/seance/seance-stats/seance-stats.component';
import {IsAdminPipe} from './is-admin.pipe';
import {CreateMovieComponent} from './components/movie/create-movie/create-movie.component';
import {EditMovieComponent} from './components/movie/create-movie/edit-movie.component';

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
    IsAdminPipe,
    CreateMovieComponent,
    EditMovieComponent,
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
