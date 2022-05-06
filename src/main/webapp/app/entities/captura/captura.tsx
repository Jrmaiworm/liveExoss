import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICaptura } from 'app/shared/model/captura.model';
import { getEntities } from './captura.reducer';

export const Captura = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const capturaList = useAppSelector(state => state.captura.entities);
  const loading = useAppSelector(state => state.captura.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="captura-heading" data-cy="CapturaHeading">
        Capturas
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/captura/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Captura
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {capturaList && capturaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Imagem</th>
                <th>Data</th>
                <th>Descricao</th>
                <th>Status</th>
                <th>Video</th>
                <th>Estacao</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {capturaList.map((captura, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/captura/${captura.id}`} color="link" size="sm">
                      {captura.id}
                    </Button>
                  </td>
                  <td>
                    {captura.imagem ? (
                      <div>
                        {captura.imagemContentType ? (
                          <a onClick={openFile(captura.imagemContentType, captura.imagem)}>
                            <img src={`data:${captura.imagemContentType};base64,${captura.imagem}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {captura.imagemContentType}, {byteSize(captura.imagem)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{captura.data ? <TextFormat type="date" value={captura.data} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{captura.descricao}</td>
                  <td>{captura.status ? 'true' : 'false'}</td>
                  <td>{captura.video}</td>
                  <td>{captura.estacao ? <Link to={`/estacao/${captura.estacao.id}`}>{captura.estacao.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/captura/${captura.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/captura/${captura.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/captura/${captura.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Capturas found</div>
        )}
      </div>
    </div>
  );
};

export default Captura;
