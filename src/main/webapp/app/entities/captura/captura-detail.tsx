import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './captura.reducer';

export const CapturaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const capturaEntity = useAppSelector(state => state.captura.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="capturaDetailsHeading">Captura</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{capturaEntity.id}</dd>
          <dt>
            <span id="imagem">Imagem</span>
          </dt>
          <dd>
            {capturaEntity.imagem ? (
              <div>
                {capturaEntity.imagemContentType ? (
                  <a onClick={openFile(capturaEntity.imagemContentType, capturaEntity.imagem)}>
                    <img src={`data:${capturaEntity.imagemContentType};base64,${capturaEntity.imagem}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {capturaEntity.imagemContentType}, {byteSize(capturaEntity.imagem)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="data">Data</span>
          </dt>
          <dd>{capturaEntity.data ? <TextFormat value={capturaEntity.data} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="descricao">Descricao</span>
          </dt>
          <dd>{capturaEntity.descricao}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{capturaEntity.status ? 'true' : 'false'}</dd>
          <dt>
            <span id="video">Video</span>
          </dt>
          <dd>{capturaEntity.video}</dd>
          <dt>Estacao</dt>
          <dd>{capturaEntity.estacao ? capturaEntity.estacao.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/captura" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/captura/${capturaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CapturaDetail;
