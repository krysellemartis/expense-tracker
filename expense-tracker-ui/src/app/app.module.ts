import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NbLayoutModule, NbThemeModule, NbUserModule } from '@nebular/theme';
import { HttpClientModule } from '@angular/common/http';
import { NbPasswordAuthStrategy, NbAuthModule, NbAuthJWTToken } from '@nebular/auth';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HeaderComponent } from './header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    NbThemeModule.forRoot(),
    HttpClientModule,
    NbAuthModule.forRoot({
         strategies: [
           NbPasswordAuthStrategy.setup({
             name: 'email',
             baseEndpoint: 'http://localhost:8080/expense/tracker',
              login: {
                endpoint: '/login',
                method: 'post'
              },
              register: {
                endpoint: '/register',
                method: 'post'
              },
              token: {
               class: NbAuthJWTToken,
             }
           }),
         ],
         forms: {},
       }),
    AppRoutingModule,
    NbLayoutModule,
    NbUserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
