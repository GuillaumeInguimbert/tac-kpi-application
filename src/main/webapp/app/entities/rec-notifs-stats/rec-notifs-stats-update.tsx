import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './rec-notifs-stats.reducer';
import { IRecNotifsStats } from 'app/shared/model/rec-notifs-stats.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RecNotifsStatsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const recNotifsStatsEntity = useAppSelector(state => state.recNotifsStats.entity);
  const loading = useAppSelector(state => state.recNotifsStats.loading);
  const updating = useAppSelector(state => state.recNotifsStats.updating);
  const updateSuccess = useAppSelector(state => state.recNotifsStats.updateSuccess);
  const handleClose = () => {
    props.history.push('/rec-notifs-stats');
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
      ...recNotifsStatsEntity,
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
          ...recNotifsStatsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tacKpiApplicationApp.recNotifsStats.home.createOrEditLabel" data-cy="RecNotifsStatsCreateUpdateHeading">
            <Translate contentKey="tacKpiApplicationApp.recNotifsStats.home.createOrEditLabel">Create or edit a RecNotifsStats</Translate>
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
                  id="rec-notifs-stats-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('tacKpiApplicationApp.recNotifsStats.date')}
                id="rec-notifs-stats-date"
                name="date"
                data-cy="date"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.recNotifsStats.partner')}
                id="rec-notifs-stats-partner"
                name="partner"
                data-cy="partner"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.recNotifsStats.useCase')}
                id="rec-notifs-stats-useCase"
                name="useCase"
                data-cy="useCase"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.recNotifsStats.status')}
                id="rec-notifs-stats-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.recNotifsStats.fallbackReason')}
                id="rec-notifs-stats-fallbackReason"
                name="fallbackReason"
                data-cy="fallbackReason"
                type="text"
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.recNotifsStats.nbNotifications')}
                id="rec-notifs-stats-nbNotifications"
                name="nbNotifications"
                data-cy="nbNotifications"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('tacKpiApplicationApp.recNotifsStats.nbDeviceDelivered')}
                id="rec-notifs-stats-nbDeviceDelivered"
                name="nbDeviceDelivered"
                data-cy="nbDeviceDelivered"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rec-notifs-stats" replace color="info">
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

export default RecNotifsStatsUpdate;
