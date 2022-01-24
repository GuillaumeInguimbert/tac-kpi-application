import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './sfmc-notifs-stats.reducer';
import { ISfmcNotifsStats } from 'app/shared/model/sfmc-notifs-stats.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SfmcNotifsStats = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const sfmcNotifsStatsList = useAppSelector(state => state.sfmcNotifsStats.entities);
  const loading = useAppSelector(state => state.sfmcNotifsStats.loading);
  const totalItems = useAppSelector(state => state.sfmcNotifsStats.totalItems);
  const links = useAppSelector(state => state.sfmcNotifsStats.links);
  const entity = useAppSelector(state => state.sfmcNotifsStats.entity);
  const updateSuccess = useAppSelector(state => state.sfmcNotifsStats.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="sfmc-notifs-stats-heading" data-cy="SfmcNotifsStatsHeading">
        <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.home.title">Sfmc Notifs Stats</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.home.createLabel">Create new Sfmc Notifs Stats</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={sfmcNotifsStatsList ? sfmcNotifsStatsList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {sfmcNotifsStatsList && sfmcNotifsStatsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('date')}>
                    <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('eventType')}>
                    <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.eventType">Event Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('langue')}>
                    <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.langue">Langue</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nbMessages')}>
                    <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.nbMessages">Nb Messages</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('eventLabel')}>
                    <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.eventLabel">Event Label</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('channel')}>
                    <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.channel">Channel</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {sfmcNotifsStatsList.map((sfmcNotifsStats, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${sfmcNotifsStats.id}`} color="link" size="sm">
                        {sfmcNotifsStats.id}
                      </Button>
                    </td>
                    <td>
                      {sfmcNotifsStats.date ? <TextFormat type="date" value={sfmcNotifsStats.date} format={APP_LOCAL_DATE_FORMAT} /> : null}
                    </td>
                    <td>{sfmcNotifsStats.eventType}</td>
                    <td>{sfmcNotifsStats.langue}</td>
                    <td>{sfmcNotifsStats.nbMessages}</td>
                    <td>{sfmcNotifsStats.eventLabel}</td>
                    <td>{sfmcNotifsStats.channel}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${sfmcNotifsStats.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${sfmcNotifsStats.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${sfmcNotifsStats.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="tacKpiApplicationApp.sfmcNotifsStats.home.notFound">No Sfmc Notifs Stats found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default SfmcNotifsStats;
