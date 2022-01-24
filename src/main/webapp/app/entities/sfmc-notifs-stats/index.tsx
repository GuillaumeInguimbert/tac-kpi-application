import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SfmcNotifsStats from './sfmc-notifs-stats';
import SfmcNotifsStatsDetail from './sfmc-notifs-stats-detail';
import SfmcNotifsStatsUpdate from './sfmc-notifs-stats-update';
import SfmcNotifsStatsDeleteDialog from './sfmc-notifs-stats-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SfmcNotifsStatsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SfmcNotifsStatsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SfmcNotifsStatsDetail} />
      <ErrorBoundaryRoute path={match.url} component={SfmcNotifsStats} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SfmcNotifsStatsDeleteDialog} />
  </>
);

export default Routes;
