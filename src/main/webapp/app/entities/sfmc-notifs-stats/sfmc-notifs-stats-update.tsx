import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './sfmc-notifs-stats.reducer';
import { ISfmcNotifsStats } from 'app/shared/model/sfmc-notifs-stats.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SfmcNotifsStatsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const sfmcNotifsStatsEntity = useAppSelector(state => state.sfmcNotifsStats.entity);
  const loading = useAppSelector(state => state.sfmcNotifsStats.loading);
  const updating = useAppSelector(state => state.sfmcNotifsStats.updating);
  const updateSuccess = useAppSelector(state => state.sfmcNotifsStats.updateSuccess);
  const handleClose = () => {
    props.history.push('/sfmc-notifs-stats');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sfmcNotifsStatsEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...sfmcNotifsStatsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tacKpiApplicationApp.sfmcNotifsStats.home.createOrEditLabel" data-cy="SfmcNotifsStatsCreateUpdateHeading">
            <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.home.createOrEditLabel">Create or edit a SfmcNotifsStats</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="sfmc-notifs-stats-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('tacKpiApplicationApp.sfmcNotifsStats.date')}
                id="sfmc-notifs-stats-date"
                name="date"
                data-cy="date"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.sfmcNotifsStats.eventType')}
                id="sfmc-notifs-stats-eventType"
                name="eventType"
                data-cy="eventType"
                type="text"
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.sfmcNotifsStats.langue')}
                id="sfmc-notifs-stats-langue"
                name="langue"
                data-cy="langue"
                type="text"
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.sfmcNotifsStats.nbMessages')}
                id="sfmc-notifs-stats-nbMessages"
                name="nbMessages"
                data-cy="nbMessages"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.sfmcNotifsStats.eventLabel')}
                id="sfmc-notifs-stats-eventLabel"
                name="eventLabel"
                data-cy="eventLabel"
                type="text"
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.sfmcNotifsStats.channel')}
                id="sfmc-notifs-stats-channel"
                name="channel"
                data-cy="channel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sfmc-notifs-stats" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SfmcNotifsStatsUpdate;
