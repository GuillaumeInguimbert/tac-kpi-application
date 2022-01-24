import dayjs from 'dayjs';

export interface IRecNotifsStats {
  id?: number;
  date?: string;
  partner?: string;
  useCase?: string;
  status?: string;
  fallbackReason?: string | null;
  nbNotifications?: number;
  nbDeviceDelivered?: number;
}

export const defaultValue: Readonly<IRecNotifsStats> = {};
