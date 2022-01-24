import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RecNotifsStats from './rec-notifs-stats';
import SfmcNotifsStats from './sfmc-notifs-stats';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}rec-notifs-stats`} component={RecNotifsStats} />
      <ErrorBoundaryRoute path={`${match.url}sfmc-notifs-stats`} component={SfmcNotifsStats} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
