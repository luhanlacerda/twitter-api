import axios from 'axios';

const URL_BASE = 'http://localhost:8080';

axios.defaults.headers.common['Content-Type'] = 'application/json';

const buildMessageError = data =>
  Object.keys(data)
    .map(key => `${key}: ${data[key]}`)
    .join('\n');

const to = promise => {
  return promise
    .then(function(data) {
      return [null, data];
    })
    .catch(function(err) {
      console.log(err.response);
      err['fullMsgError'] = buildMessageError(err.response.data);
      return [err, undefined];
    });
};

export default {
  get: path => to(axios.get(URL_BASE + path)),
  post: (path, body) => to(axios.post(URL_BASE + path, body)),
  put: (path, body) => to(axios.put(URL_BASE + path, body)),
  delete: path => to(axios.delete(URL_BASE + path))
};
