import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Captura from './captura';
import CapturaDetail from './captura-detail';
import CapturaUpdate from './captura-update';
import CapturaDeleteDialog from './captura-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CapturaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CapturaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CapturaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Captura} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CapturaDeleteDialog} />
  </>
);

export default Routes;
