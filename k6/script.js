import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '30s', target: 20 },
        { duration: '1m30s', target: 10 },
        { duration: '20s', target: 0 },
    ],
};

export default function() {
    const payload = JSON.stringify({
        originalUrl: 'https://www.google.com',
    });
    const params = {
        headers: { 'Content-Type': 'application/json' },
    };
    const res = http.post('http://192.168.123.10:50000/api/short-urls', payload, params);
    check(res, { 'status was 201': (r) => r.status === 201 });
    sleep(1)
}