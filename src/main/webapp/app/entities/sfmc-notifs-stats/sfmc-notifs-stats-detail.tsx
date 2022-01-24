import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './sfmc-notifs-stats.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SfmcNotifsStatsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sfmcNotifsStatsEntity = useAppSelector(state => state.sfmcNotifsStats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sfmcNotifsStatsDetailsHeading">
          <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.detail.title">SfmcNotifsStats</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sfmcNotifsStatsEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {sfmcNotifsStatsEntity.date ? (
              <TextFormat value={sfmcNotifsStatsEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eventType">
              <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.eventType">Event Type</Translate>
            </span>
          </dt>
          <dd>{sfmcNotifsStatsEntity.eventType}</dd>
          <dt>
            <span id="langue">
              <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.langue">Langue</Translate>
            </span>
          </dt>
          <dd>{sfmcNotifsStatsEntity.langue}</dd>
          <dt>
            <span id="nbMessages">
              <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.nbMessages">Nb Messages</Translate>
            </span>
          </dt>
          <dd>{sfmcNotifsStatsEntity.nbMessages}</dd>
          <dt>
            <span id="eventLabel">
              <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.eventLabel">Event Label</Translate>
            </span>
          </dt>
          <dd>{sfmcNotifsStatsEntity.eventLabel}</dd>
          <dt>
            <span id="channel">
              <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.channel">Channel</Translate>
            </span>
          </dt>
          <dd>{sfmcNotifsStatsEntity.channel}</dd>
        </dl>
        <Button tag={Link} to="/sfmc-notifs-stats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sfmc-notifs-stats/${sfmcNotifsStatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SfmcNotifsStatsDetail;
