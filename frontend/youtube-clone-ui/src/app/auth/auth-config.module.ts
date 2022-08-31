import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';


@NgModule({
  imports: [AuthModule.forRoot({
    config: {
      authority: 'https://dev-ermevev.eu.auth0.com',
      redirectUrl: 'http://localhost:4200/callback',
      clientId: '6EnXQVrTvd2e5HfByJQLr1GZF1r2vH0K',
      scope: 'openid profile offline_access email',
      responseType: 'code',
      silentRenew: true,
      useRefreshToken: true,
      secureRoutes: ['http://localhost:8080/'],
      customParamsAuthRequest: {
        audience: 'http://localhost:8080/'
      }
    }
  })],
  exports: [AuthModule],
})
export class AuthConfigModule {}
