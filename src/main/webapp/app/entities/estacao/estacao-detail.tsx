import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './estacao.reducer';

export const EstacaoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const estacaoEntity = useAppSelector(state => state.estacao.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="estacaoDetailsHeading">Estacao</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{estacaoEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{estacaoEntity.nome}</dd>
          <dt>
            <span id="associado">Associado</span>
          </dt>
          <dd>{estacaoEntity.associado}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{estacaoEntity.email}</dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{estacaoEntity.telefone}</dd>
          <dt>
            <span id="cidade">Cidade</span>
          </dt>
          <dd>{estacaoEntity.cidade}</dd>
          <dt>
            <span id="estado">Estado</span>
          </dt>
          <dd>{estacaoEntity.estado}</dd>
          <dt>
            <span id="lente">Lente</span>
          </dt>
          <dd>{estacaoEntity.lente}</dd>
          <dt>
            <span id="camera">Camera</span>
          </dt>
          <dd>{estacaoEntity.camera}</dd>
          <dt>
            <span id="fov">Fov</span>
          </dt>
          <dd>{estacaoEntity.fov}</dd>
          <dt>
            <span id="kml">Kml</span>
          </dt>
          <dd>{estacaoEntity.kml}</dd>
          <dt>
            <span id="lat">Lat</span>
          </dt>
          <dd>{estacaoEntity.lat}</dd>
          <dt>
            <span id="lng">Lng</span>
          </dt>
          <dd>{estacaoEntity.lng}</dd>
          <dt>
            <span id="site">Site</span>
          </dt>
          <dd>{estacaoEntity.site}</dd>
          <dt>
            <span id="ativa">Ativa</span>
          </dt>
          <dd>{estacaoEntity.ativa ? 'true' : 'false'}</dd>
          <dt>
            <span id="pareamento">Pareamento</span>
          </dt>
          <dd>{estacaoEntity.pareamento}</dd>
          <dt>User</dt>
          <dd>{estacaoEntity.user ? estacaoEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/estacao" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/estacao/${estacaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EstacaoDetail;
