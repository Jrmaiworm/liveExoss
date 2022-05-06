import dayjs from 'dayjs';
import { IEstacao } from 'app/shared/model/estacao.model';

export interface ICaptura {
  id?: number;
  imagemContentType?: string | null;
  imagem?: string | null;
  data?: string | null;
  descricao?: string | null;
  status?: boolean | null;
  video?: string | null;
  estacao?: IEstacao | null;
}

export const defaultValue: Readonly<ICaptura> = {
  status: false,
};
