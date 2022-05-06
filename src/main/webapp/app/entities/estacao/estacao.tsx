import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEstacao } from 'app/shared/model/estacao.model';
import { getEntities } from './estacao.reducer';

export const Estacao = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const estacaoList = useAppSelector(state => state.estacao.entities);
  const loading = useAppSelector(state => state.estacao.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="estacao-heading" data-cy="EstacaoHeading">
        Estacaos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/estacao/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Estacao
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {estacaoList && estacaoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Associado</th>
                <th>Email</th>
                <th>Telefone</th>
                <th>Cidade</th>
                <th>Estado</th>
                <th>Lente</th>
                <th>Camera</th>
                <th>Fov</th>
                <th>Kml</th>
                <th>Lat</th>
                <th>Lng</th>
                <th>Site</th>
                <th>Ativa</th>
                <th>Pareamento</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {estacaoList.map((estacao, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/estacao/${estacao.id}`} color="link" size="sm">
                      {estacao.id}
                    </Button>
                  </td>
                  <td>{estacao.nome}</td>
                  <td>{estacao.associado}</td>
                  <td>{estacao.email}</td>
                  <td>{estacao.telefone}</td>
                  <td>{estacao.cidade}</td>
                  <td>{estacao.estado}</td>
                  <td>{estacao.lente}</td>
                  <td>{estacao.camera}</td>
                  <td>{estacao.fov}</td>
                  <td>{estacao.kml}</td>
                  <td>{estacao.lat}</td>
                  <td>{estacao.lng}</td>
                  <td>{estacao.site}</td>
                  <td>{estacao.ativa ? 'true' : 'false'}</td>
                  <td>{estacao.pareamento}</td>
                  <td>{estacao.user ? estacao.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/estacao/${estacao.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/estacao/${estacao.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/estacao/${estacao.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Estacaos found</div>
        )}
      </div>
    </div>
  );
};

export default Estacao;
