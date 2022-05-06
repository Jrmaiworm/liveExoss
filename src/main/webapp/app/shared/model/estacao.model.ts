import { IUser } from 'app/shared/model/user.model';
import { ICaptura } from 'app/shared/model/captura.model';

export interface IEstacao {
  id?: number;
  nome?: string | null;
  associado?: string | null;
  email?: string | null;
  telefone?: string | null;
  cidade?: string | null;
  estado?: string | null;
  lente?: string | null;
  camera?: string | null;
  fov?: number | null;
  kml?: string | null;
  lat?: number | null;
  lng?: number | null;
  site?: string | null;
  ativa?: boolean | null;
  pareamento?: string | null;
  user?: IUser | null;
  capturas?: ICaptura[] | null;
}

export const defaultValue: Readonly<IEstacao> = {
  ativa: false,
};
