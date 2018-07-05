import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SingInComponent} from './sing-in/sing-in.component';
import {SignUpComponent} from './sign-up/sign-up.component';

const routes: Routes = [
  {path: '', redirectTo: '/sign-in', pathMatch: 'full'},
  {path: 'sign-in', component: SingInComponent},
  {path: 'sign-up', component: SignUpComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
