import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RecNotifsStats from './rec-notifs-stats';
import RecNotifsStatsDetail from './rec-notifs-stats-detail';
import RecNotifsStatsUpdate from './rec-notifs-stats-update';
import RecNotifsStatsDeleteDialog from './rec-notifs-stats-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RecNotifsStatsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RecNotifsStatsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RecNotifsStatsDetail} />
      <ErrorBoundaryRoute path={match.url} component={RecNotifsStats} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RecNotifsStatsDeleteDialog} />
  </>
);

export default Routes;
