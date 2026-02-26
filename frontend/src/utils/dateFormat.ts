import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';

dayjs.extend(utc);

export function dateFormat(date: string, format: string = 'DD.MM.YYYY HH:mm'): string {
  const tzOffset = new Date().getTimezoneOffset();
  return dayjs(date)
    .utc(true)
    .utcOffset(tzOffset, true)
    .format(format);
}
