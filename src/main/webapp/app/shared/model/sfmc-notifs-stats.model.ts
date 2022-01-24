import dayjs from 'dayjs';

export interface ISfmcNotifsStats {
  id?: number;
  date?: string;
  eventType?: string | null;
  langue?: string | null;
  nbMessages?: number;
  eventLabel?: string | null;
  channel?: string | null;
}

export const defaultValue: Readonly<ISfmcNotifsStats> = {};
