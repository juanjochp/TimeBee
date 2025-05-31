import {Component, OnInit} from '@angular/core';
import {AuthControllerService} from '../../../openapi';
import {Router} from '@angular/router';
import {FormGroup} from '@angular/forms';
import {FormlyFieldConfig} from '@ngx-formly/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: false,
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService:AuthControllerService, private router:Router) {
  }

  form = new FormGroup({});
  model: any = {};
  fields: FormlyFieldConfig[] = [
    {
      key: 'email',
      type: 'input',
      props: {
        type: 'email',
        label: 'Correo electrónico',
        placeholder: 'Introduce tu correo electrónico',
        required: true,
        hideRequiredMarker: true
      },
    },
    {
      key: 'password',
      type: 'input',
      props: {
        type: 'password',
        label: 'Contraseña',
        placeholder: 'Introduce tu contraseña',
        required: true,
        hideRequiredMarker: true
      },
    }
  ];

  ngOnInit() {
  }

  onSubmit() {

    this.authService.login({'email':this.model.email,'password':this.model.password}).subscribe({
      next: (response)=> {
        // console.log(response.token);
        localStorage.setItem('token',<string>response.token);

        // decodificamos el payload (segunda parte del JWT)
        const payloadJson = atob(response.token!.split('.')[1]);
        const payload = JSON.parse(payloadJson) as { role: string };

        if (payload.role === 'ROLE_EMPRESA') {
          this.router.navigate(['/empresa']);
        } else {
          this.router.navigate(['/trabajador']);
        }

      }
    })
  }

  altaEmpresa() {
    this.router.navigate(['/alta']);
  }
}
