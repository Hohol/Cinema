import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {MoviesComponent} from './components/movie/movies/movies.component';
import {SeancesComponent} from './components/seance/seances/seances.component';
import {BuyTicketsComponent} from './components/seance/buy-tickets/buy-tickets.component';
import {UsersComponent} from './components/user/users/users.component';
import {UserStatsComponent} from './components/user/user-stats/user-stats.component';
import {SeanceStatsComponent} from './components/seance/seance-stats/seance-stats.component';
import {CreateMovieComponent} from './components/movie/create-movie/create-movie.component';
import {EditMovieComponent} from './components/movie/create-movie/edit-movie.component';

const routes: Routes = [
  {path: '', redirectTo: '/movies', pathMatch: 'full'},
  {path: 'movies', component: MoviesComponent},
  {path: 'movies/create', component: CreateMovieComponent},
  {path: 'movies/edit/:id', component: EditMovieComponent},
  {path: 'seances', component: SeancesComponent},
  {path: 'seances/buy/:id', component: BuyTicketsComponent},
  {path: 'seances/:id', component: SeanceStatsComponent},
  {path: 'users', component: UsersComponent},
  {path: 'user/:id', component: UserStatsComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
