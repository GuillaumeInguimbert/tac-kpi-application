import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './rec-notifs-stats.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RecNotifsStatsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const recNotifsStatsEntity = useAppSelector(state => state.recNotifsStats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recNotifsStatsDetailsHeading">
          <Translate contentKey="tacKpiApplicationApp.recNotifsStats.detail.title">RecNotifsStats</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recNotifsStatsEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="tacKpiApplicationApp.recNotifsStats.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {recNotifsStatsEntity.date ? <TextFormat value={recNotifsStatsEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="partner">
              <Translate contentKey="tacKpiApplicationApp.recNotifsStats.partner">Partner</Translate>
            </span>
          </dt>
          <dd>{recNotifsStatsEntity.partner}</dd>
          <dt>
            <span id="useCase">
              <Translate contentKey="tacKpiApplicationApp.recNotifsStats.useCase">Use Case</Translate>
            </span>
          </dt>
          <dd>{recNotifsStatsEntity.useCase}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="tacKpiApplicationApp.recNotifsStats.status">Status</Translate>
            </span>
          </dt>
          <dd>{recNotifsStatsEntity.status}</dd>
          <dt>
            <span id="fallbackReason">
              <Translate contentKey="tacKpiApplicationApp.recNotifsStats.fallbackReason">Fallback Reason</Translate>
            </span>
          </dt>
          <dd>{recNotifsStatsEntity.fallbackReason}</dd>
          <dt>
            <span id="nbNotifications">
              <Translate contentKey="tacKpiApplicationApp.recNotifsStats.nbNotifications">Nb Notifications</Translate>
            </span>
          </dt>
          <dd>{recNotifsStatsEntity.nbNotifications}</dd>
          <dt>
            <span id="nbDeviceDelivered">
              <Translate contentKey="tacKpiApplicationApp.recNotifsStats.nbDeviceDelivered">Nb Device Delivered</Translate>
            </span>
          </dt>
          <dd>{recNotifsStatsEntity.nbDeviceDelivered}</dd>
        </dl>
        <Button tag={Link} to="/rec-notifs-stats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rec-notifs-stats/${recNotifsStatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecNotifsStatsDetail;
