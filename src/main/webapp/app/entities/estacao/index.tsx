import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Estacao from './estacao';
import EstacaoDetail from './estacao-detail';
import EstacaoUpdate from './estacao-update';
import EstacaoDeleteDialog from './estacao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EstacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EstacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EstacaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Estacao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EstacaoDeleteDialog} />
  </>
);

export default Routes;
