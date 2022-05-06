import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEstacao } from 'app/shared/model/estacao.model';
import { getEntity, updateEntity, createEntity, reset } from './estacao.reducer';

export const EstacaoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const estacaoEntity = useAppSelector(state => state.estacao.entity);
  const loading = useAppSelector(state => state.estacao.loading);
  const updating = useAppSelector(state => state.estacao.updating);
  const updateSuccess = useAppSelector(state => state.estacao.updateSuccess);
  const handleClose = () => {
    props.history.push('/estacao');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...estacaoEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...estacaoEntity,
          user: estacaoEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="liveExossApp.estacao.home.createOrEditLabel" data-cy="EstacaoCreateUpdateHeading">
            Create or edit a Estacao
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="estacao-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nome" id="estacao-nome" name="nome" data-cy="nome" type="text" />
              <ValidatedField label="Associado" id="estacao-associado" name="associado" data-cy="associado" type="text" />
              <ValidatedField label="Email" id="estacao-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Telefone" id="estacao-telefone" name="telefone" data-cy="telefone" type="text" />
              <ValidatedField label="Cidade" id="estacao-cidade" name="cidade" data-cy="cidade" type="text" />
              <ValidatedField label="Estado" id="estacao-estado" name="estado" data-cy="estado" type="text" />
              <ValidatedField label="Lente" id="estacao-lente" name="lente" data-cy="lente" type="text" />
              <ValidatedField label="Camera" id="estacao-camera" name="camera" data-cy="camera" type="text" />
              <ValidatedField label="Fov" id="estacao-fov" name="fov" data-cy="fov" type="text" />
              <ValidatedField label="Kml" id="estacao-kml" name="kml" data-cy="kml" type="text" />
              <ValidatedField label="Lat" id="estacao-lat" name="lat" data-cy="lat" type="text" />
              <ValidatedField label="Lng" id="estacao-lng" name="lng" data-cy="lng" type="text" />
              <ValidatedField label="Site" id="estacao-site" name="site" data-cy="site" type="text" />
              <ValidatedField label="Ativa" id="estacao-ativa" name="ativa" data-cy="ativa" check type="checkbox" />
              <ValidatedField label="Pareamento" id="estacao-pareamento" name="pareamento" data-cy="pareamento" type="text" />
              <ValidatedField id="estacao-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/estacao" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EstacaoUpdate;
