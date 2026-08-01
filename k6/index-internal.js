import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    vus: 1000,
    duration: '1s',
};

export default function () {
    const response = http.get(
        'http://nginx/ecommerce-api/api/v1/products/1'
    );

    check(response, {
        'HTTP 200': (r) => r.status === 200,
    });

    sleep(1);
}

