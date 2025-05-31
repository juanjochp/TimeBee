'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">time-bee-front documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>README
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                                <li class="link">
                                    <a href="properties.html" data-type="chapter-link">
                                        <span class="icon ion-ios-apps"></span>Properties
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-bs-toggle="collapse" ${ isNormalMode ?
                                'data-bs-target="#modules-links"' : 'data-bs-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/ApiModule.html" data-type="entity-link" >ApiModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link" >AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-AppModule-2c6e714fbb9a6d8b71beb39f6de5d542c9dd3417894ae56f5c3b35cde15b4a19f520fc9c6531edab4733f374f07f99935cc7713f4784a29594c6de711045c7bb"' : 'data-bs-target="#xs-components-links-module-AppModule-2c6e714fbb9a6d8b71beb39f6de5d542c9dd3417894ae56f5c3b35cde15b4a19f520fc9c6531edab4733f374f07f99935cc7713f4784a29594c6de711045c7bb"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-2c6e714fbb9a6d8b71beb39f6de5d542c9dd3417894ae56f5c3b35cde15b4a19f520fc9c6531edab4733f374f07f99935cc7713f4784a29594c6de711045c7bb"' :
                                            'id="xs-components-links-module-AppModule-2c6e714fbb9a6d8b71beb39f6de5d542c9dd3417894ae56f5c3b35cde15b4a19f520fc9c6531edab4733f374f07f99935cc7713f4784a29594c6de711045c7bb"' }>
                                            <li class="link">
                                                <a href="components/AppComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AppComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PageNotFoundComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >PageNotFoundComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRoutingModule.html" data-type="entity-link" >AppRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/AutenticacionModule.html" data-type="entity-link" >AutenticacionModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-AutenticacionModule-82f5d120c418c973869d351e64fffee07a224b99a34b4280130c06e133a143a4373fc0c4a9676a25315e5dfdd33fc8fdd12048b32873ef69343f64d45b2da6f2"' : 'data-bs-target="#xs-components-links-module-AutenticacionModule-82f5d120c418c973869d351e64fffee07a224b99a34b4280130c06e133a143a4373fc0c4a9676a25315e5dfdd33fc8fdd12048b32873ef69343f64d45b2da6f2"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AutenticacionModule-82f5d120c418c973869d351e64fffee07a224b99a34b4280130c06e133a143a4373fc0c4a9676a25315e5dfdd33fc8fdd12048b32873ef69343f64d45b2da6f2"' :
                                            'id="xs-components-links-module-AutenticacionModule-82f5d120c418c973869d351e64fffee07a224b99a34b4280130c06e133a143a4373fc0c4a9676a25315e5dfdd33fc8fdd12048b32873ef69343f64d45b2da6f2"' }>
                                            <li class="link">
                                                <a href="components/AltaEmpresaComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AltaEmpresaComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LoginComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >LoginComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AuthRoutingModule.html" data-type="entity-link" >AuthRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/EmpresaModule.html" data-type="entity-link" >EmpresaModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-EmpresaModule-ce73875b685a29ad4083b44a7ed322a43dd05e48d76d7c1d844ac8a065b24f37698885abdcb906400b24a2bfd1292c2219d823389a039e65ae10bd84b1a7d1b0"' : 'data-bs-target="#xs-components-links-module-EmpresaModule-ce73875b685a29ad4083b44a7ed322a43dd05e48d76d7c1d844ac8a065b24f37698885abdcb906400b24a2bfd1292c2219d823389a039e65ae10bd84b1a7d1b0"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-EmpresaModule-ce73875b685a29ad4083b44a7ed322a43dd05e48d76d7c1d844ac8a065b24f37698885abdcb906400b24a2bfd1292c2219d823389a039e65ae10bd84b1a7d1b0"' :
                                            'id="xs-components-links-module-EmpresaModule-ce73875b685a29ad4083b44a7ed322a43dd05e48d76d7c1d844ac8a065b24f37698885abdcb906400b24a2bfd1292c2219d823389a039e65ae10bd84b1a7d1b0"' }>
                                            <li class="link">
                                                <a href="components/EmpresaDashboardComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >EmpresaDashboardComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/EmpresaRoutingModule.html" data-type="entity-link" >EmpresaRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/TrabajadorModule.html" data-type="entity-link" >TrabajadorModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-TrabajadorModule-3d1762042f3f7cc191708d5ea44d141d72505b36f0e820db3f05df93674ec641f49b03395519e014cff012891b0f12d515ad70a3f73203f0860e23259caf62b5"' : 'data-bs-target="#xs-components-links-module-TrabajadorModule-3d1762042f3f7cc191708d5ea44d141d72505b36f0e820db3f05df93674ec641f49b03395519e014cff012891b0f12d515ad70a3f73203f0860e23259caf62b5"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-TrabajadorModule-3d1762042f3f7cc191708d5ea44d141d72505b36f0e820db3f05df93674ec641f49b03395519e014cff012891b0f12d515ad70a3f73203f0860e23259caf62b5"' :
                                            'id="xs-components-links-module-TrabajadorModule-3d1762042f3f7cc191708d5ea44d141d72505b36f0e820db3f05df93674ec641f49b03395519e014cff012891b0f12d515ad70a3f73203f0860e23259caf62b5"' }>
                                            <li class="link">
                                                <a href="components/TrabajadorDashboardComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TrabajadorDashboardComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/TrabajadorRoutingModule.html" data-type="entity-link" >TrabajadorRoutingModule</a>
                            </li>
                </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#components-links"' :
                            'data-bs-target="#xs-components-links"' }>
                            <span class="icon ion-md-cog"></span>
                            <span>Components</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="components-links"' : 'id="xs-components-links"' }>
                            <li class="link">
                                <a href="components/DashboardFichajesComponent.html" data-type="entity-link" >DashboardFichajesComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DashboardFichajesTrabajadorComponent.html" data-type="entity-link" >DashboardFichajesTrabajadorComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DashboardNominasTrabajadorComponent.html" data-type="entity-link" >DashboardNominasTrabajadorComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DashboardPermisosComponent.html" data-type="entity-link" >DashboardPermisosComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DashboardPermisosTrabajadorComponent.html" data-type="entity-link" >DashboardPermisosTrabajadorComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/DashboardTrabajadoresComponent.html" data-type="entity-link" >DashboardTrabajadoresComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/FormlyHorizontalWrapper.html" data-type="entity-link" >FormlyHorizontalWrapper</a>
                            </li>
                            <li class="link">
                                <a href="components/ModalComponent.html" data-type="entity-link" >ModalComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/NavbarEmpresaComponent.html" data-type="entity-link" >NavbarEmpresaComponent</a>
                            </li>
                            <li class="link">
                                <a href="components/NavbarTrabajadorComponent.html" data-type="entity-link" >NavbarTrabajadorComponent</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#classes-links"' :
                            'data-bs-target="#xs-classes-links"' }>
                            <span class="icon ion-ios-paper"></span>
                            <span>Classes</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="classes-links"' : 'id="xs-classes-links"' }>
                            <li class="link">
                                <a href="classes/BaseService.html" data-type="entity-link" >BaseService</a>
                            </li>
                            <li class="link">
                                <a href="classes/Configuration.html" data-type="entity-link" >Configuration</a>
                            </li>
                            <li class="link">
                                <a href="classes/CustomHttpParameterCodec.html" data-type="entity-link" >CustomHttpParameterCodec</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#injectables-links"' :
                                'data-bs-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/AuthControllerService.html" data-type="entity-link" >AuthControllerService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/EmpresasService.html" data-type="entity-link" >EmpresasService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/FichajesService.html" data-type="entity-link" >FichajesService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/FormasJurdicasService.html" data-type="entity-link" >FormasJurdicasService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/GenerosService.html" data-type="entity-link" >GenerosService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/NominasService.html" data-type="entity-link" >NominasService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/PermisosService.html" data-type="entity-link" >PermisosService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/TrabajadoresService.html" data-type="entity-link" >TrabajadoresService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UsuarioResourceService.html" data-type="entity-link" >UsuarioResourceService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#interceptors-links"' :
                            'data-bs-target="#xs-interceptors-links"' }>
                            <span class="icon ion-ios-swap"></span>
                            <span>Interceptors</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="interceptors-links"' : 'id="xs-interceptors-links"' }>
                            <li class="link">
                                <a href="interceptors/AuthInterceptor.html" data-type="entity-link" >AuthInterceptor</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#interfaces-links"' :
                            'data-bs-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/AuthControllerServiceInterface.html" data-type="entity-link" >AuthControllerServiceInterface</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/ConfigurationParameters.html" data-type="entity-link" >ConfigurationParameters</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/EmailRequest.html" data-type="entity-link" >EmailRequest</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/EmpresasServiceInterface.html" data-type="entity-link" >EmpresasServiceInterface</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FichajeEditRequestDto.html" data-type="entity-link" >FichajeEditRequestDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FichajeRequestDto.html" data-type="entity-link" >FichajeRequestDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FichajesDto.html" data-type="entity-link" >FichajesDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FichajesServiceInterface.html" data-type="entity-link" >FichajesServiceInterface</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FichajeView.html" data-type="entity-link" >FichajeView</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FichajeView-1.html" data-type="entity-link" >FichajeView</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FormaJuridicaDto.html" data-type="entity-link" >FormaJuridicaDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/FormasJurdicasServiceInterface.html" data-type="entity-link" >FormasJurdicasServiceInterface</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/GeneroDto.html" data-type="entity-link" >GeneroDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/GenerosServiceInterface.html" data-type="entity-link" >GenerosServiceInterface</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IdRequestDto.html" data-type="entity-link" >IdRequestDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/LoginRequest.html" data-type="entity-link" >LoginRequest</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/LoginResponse.html" data-type="entity-link" >LoginResponse</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/NominaMetadataDto.html" data-type="entity-link" >NominaMetadataDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/NominasServiceInterface.html" data-type="entity-link" >NominasServiceInterface</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Param.html" data-type="entity-link" >Param</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/PermisosHumanizeDto.html" data-type="entity-link" >PermisosHumanizeDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/PermisosServiceInterface.html" data-type="entity-link" >PermisosServiceInterface</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/TipoPermisoDto.html" data-type="entity-link" >TipoPermisoDto</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/TrabajadoresServiceInterface.html" data-type="entity-link" >TrabajadoresServiceInterface</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/UsuarioResourceServiceInterface.html" data-type="entity-link" >UsuarioResourceServiceInterface</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#miscellaneous-links"'
                            : 'data-bs-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/functions.html" data-type="entity-link">Functions</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/typealiases.html" data-type="entity-link">Type aliases</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <a data-type="chapter-link" href="routes.html"><span class="icon ion-ios-git-branch"></span>Routes</a>
                        </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank" rel="noopener noreferrer">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});