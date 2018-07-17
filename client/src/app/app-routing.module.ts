import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {MoviesComponent} from './components/movies/movies.component';
import {SeancesComponent} from './components/seances/seances.component';
import {BuyTicketsComponent} from './components/buy-tickets/buy-tickets.component';
import {UsersComponent} from './components/users/users.component';

const routes: Routes = [
  {path: '', redirectTo: '/movies', pathMatch: 'full'},
  {path: 'movies', component: MoviesComponent},
  {path: 'seances', component: SeancesComponent},
  {path: 'seances/buy/:id', component: BuyTicketsComponent},
  {path: 'users', component: UsersComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
